package com.onesoft.digitaledu.view.activity.infomanager.docsendrec;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.Attach;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.model.BoxBean;
import com.onesoft.digitaledu.model.BoxDetail;
import com.onesoft.digitaledu.presenter.infomanager.docsendrec.DeleteDocPresenter;
import com.onesoft.digitaledu.presenter.infomanager.docsendrec.DocDetailPresenter;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.infomanager.docsendrec.IDelDocView;
import com.onesoft.digitaledu.view.iview.infomanager.docsendrec.IDocDetailView;
import com.onesoft.digitaledu.widget.dialog.RecipientDialog;
import com.onesoft.netlibrary.utils.FileUtils;
import com.onesoft.netlibrary.utils.PathUtil;

import org.greenrobot.eventbus.EventBus;

import static com.onesoft.digitaledu.model.BaseEvent.UPDATE_INBOX_DOCUMENT;
import static com.onesoft.digitaledu.view.activity.infomanager.docsendrec.DocDetailGridAdapter.REQUEST_CODE_DIR;

/**
 * 公文收发  收件箱详情
 * Created by Jayden on 2016/12/29.
 */
public class DocRecDetailActivity extends ToolBarActivity<DocDetailPresenter> implements IDelDocView, IDocDetailView {

    private BoxBean mBoxBean;

    public static void startDetail(Context context, BoxBean boxBean) {
        Intent intent = new Intent(context, DocRecDetailActivity.class);
        intent.putExtra("boxBean", boxBean);
        context.startActivity(intent);
    }

    private void getDataFromForward() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mBoxBean = (BoxBean) bundle.getSerializable("boxBean");
        }
    }

    private Button mBtnReply;
    private ImageView mImageType;
    private TextView mTxtTitle;
    private TextView mTxtSendTime;
    private TextView mTxtSendPerson;
    private TextView mTxtRecipient;
    private TextView mEditContent;
    private GridView mGridView;
    private DocDetailGridAdapter mGridAdapter;

    private DeleteDocPresenter mDeleteDocPresenter;

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new DocDetailPresenter(this, this);
        mDeleteDocPresenter = new DeleteDocPresenter(this, this);
    }

    @Override
    public void initData() {
        setTitle(getResources().getString(R.string.message_detail));
        mGridAdapter = new DocDetailGridAdapter(this);
        mGridAdapter.setClickListener(new DocDetailGridAdapter.OnFileClickListener() {
            @Override
            public void onSaveFile(Attach attach) {
                mAttach = attach;
                new MaterialFilePicker()
                        .withActivity(DocRecDetailActivity.this)
                        .withRequestCode(REQUEST_CODE_DIR)
                        .withShowConfirm(true)
                        .withHiddenFiles(true)
                        .start();
            }

            @Override
            public void onForward(int pos) {
                ReplyDocActivity.startForward(DocRecDetailActivity.this, mBoxDetail, pos, ReplyDocActivity.TYPE_FORWARD);
            }
        });
        mGridView.setAdapter(mGridAdapter);
        mPresenter.getMessageDetail(mBoxBean.id);
    }

    @Override
    public void initView() {
        mGridView = (GridView) findViewById(R.id.grid_files);
        mImageType = (ImageView) findViewById(R.id.img_type);
        mTxtTitle = (TextView) findViewById(R.id.txt_title);
        mTxtRecipient = (TextView) findViewById(R.id.txt_recipient);
        mTxtSendPerson = (TextView) findViewById(R.id.txt_send_person);
        mTxtSendTime = (TextView) findViewById(R.id.txt_send_time);
        mEditContent = (TextView) findViewById(R.id.edit_title);
        mBtnReply = (Button) findViewById(R.id.btn_reply);
    }

    @Override
    public void initListener() {
        mImageType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipientDialog dialog = new RecipientDialog(DocRecDetailActivity.this);
                dialog.show();
                dialog.setData(mBoxBean.imgUrl, SPHelper.getUserName(DocRecDetailActivity.this),
                        SPHelper.getUserId(DocRecDetailActivity.this));
            }
        });
        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeleteDocPresenter.delInBoxMsg(mBoxBean.id);
            }
        });
        mBtnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//收件公文箱，回复
                ReplyDocActivity.startReply(DocRecDetailActivity.this, mBoxDetail, ReplyDocActivity.TYPE_REPLAY);
            }
        });
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_doc_rec_detail, null);
    }

    private BoxDetail mBoxDetail;

    @Override
    public void onSuccess(BoxDetail detail) {
        mBoxDetail = detail;
        mEditContent.setText(Html.fromHtml(detail.content));
        mTxtTitle.setText(detail.title);
        if ("0".equals(detail.typeid)) {//0是通知
            Drawable drawable = getResources().getDrawable(R.drawable.icon_mess_notice);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTxtTitle.setCompoundDrawables(null, null, drawable, null);
        }
        mTxtSendTime.setText(detail.time);
        mTxtSendPerson.setText(detail.name);//发件人
        mTxtRecipient.setText(SPHelper.getUserName(this));//收件人
        mGridAdapter.setDatas(detail.attach);
        if ("0".equals(detail.fromuser)) {//系统消息，不能回复
            mBtnReply.setVisibility(View.GONE);
        }
        mPageStateLayout.onSucceed();
    }

    @Override
    public void onDelSuccess() { //删除消息成功
        EventBus.getDefault().post(new BaseEvent(UPDATE_INBOX_DOCUMENT, ""));//刷新收件箱列表
        finish();
    }

    private Attach mAttach;//附件

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == DocDetailGridAdapter.REQUEST_CODE_DIR && data != null) {
            String dir = data.getStringExtra(FilePickerActivity.RESULT_DIR_PATH);
            if (FileUtils.copyFile(mAttach.new_name, PathUtil.getInstance().getDownloadDir(), dir) > 0) {
                Toast.makeText(this, getString(R.string.save_success), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
