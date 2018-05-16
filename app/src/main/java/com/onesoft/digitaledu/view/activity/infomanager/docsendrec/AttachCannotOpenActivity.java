package com.onesoft.digitaledu.view.activity.infomanager.docsendrec;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.Attach;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.netlibrary.utils.FileUtils;
import com.onesoft.netlibrary.utils.PathUtil;

import static com.onesoft.digitaledu.view.activity.infomanager.docsendrec.DocDetailGridAdapter.REQUEST_CODE_DIR;

/**
 * 公文附件不能打开
 * Created by Jayden on 2017/01/03
 */
public class AttachCannotOpenActivity extends ToolBarActivity<BasePresenter> {

    private Attach mBoxBean;

    public static void startAttach(Context context, Attach boxBean) {
        Intent intent = new Intent(context, AttachCannotOpenActivity.class);
        intent.putExtra("boxBean", boxBean);
        context.startActivity(intent);
    }

    private void getDataFromForward() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mBoxBean = (Attach) bundle.getSerializable("boxBean");
        }
    }

    private TextView mTxtTitle;
    private TextView mTxtSize;
    private Button mBtnSave;

    @Override
    protected void initPresenter() {
        getDataFromForward();
    }

    @Override
    public void initData() {
        setTitle(mBoxBean.attach_name);
        mTxtSize.setText(mBoxBean.size);
        mTxtTitle.setText(mBoxBean.attach_name);
        mPageStateLayout.onSucceed();
    }

    @Override
    public void initView() {
        mTxtTitle = (TextView) findViewById(R.id.txt_file_name);
        mTxtSize = (TextView) findViewById(R.id.txt_file_size);
        mBtnSave = (Button) findViewById(R.id.btn_save);
    }

    @Override
    public void initListener() {
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialFilePicker()
                        .withActivity(AttachCannotOpenActivity.this)
                        .withRequestCode(REQUEST_CODE_DIR)
                        .withShowConfirm(true)
                        .withHiddenFiles(true)
                        .start();
            }
        });
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_attach_cannot_open, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == DocDetailGridAdapter.REQUEST_CODE_DIR && data != null) {
            String dir = data.getStringExtra(FilePickerActivity.RESULT_DIR_PATH);
            if (FileUtils.copyFile(mBoxBean.new_name, PathUtil.getInstance().getDownloadDir(),dir) > 0){
                Toast.makeText(this, getString(R.string.save_success), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
