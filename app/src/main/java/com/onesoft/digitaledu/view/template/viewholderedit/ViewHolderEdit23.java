package com.onesoft.digitaledu.view.template.viewholderedit;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.MenuParent;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.presenter.template.TemplateCommonPresenter;
import com.onesoft.digitaledu.view.template.viewholderadd.BaseViewHolder;
import com.onesoft.digitaledu.widget.dialog.SingleSelectAdapter;
import com.onesoft.digitaledu.widget.dialog.SingleSelectDialog;

import java.util.List;

/**
 * 文字+"必选"+右边箭头，弹出弹框 上级菜单
 * Created by Jayden on 2017/01/05.
 */
public class ViewHolderEdit23 extends BaseViewHolder {

    protected TextView mTxtName;
    protected TextView mTxtContent;
    protected ImageView img_select;

    protected List<MenuParent> mRoles;

    public ViewHolderEdit23(Context context) {
        mContext = context;
    }

    public View createView(ViewGroup parent) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_viewholder_3, parent, false);
        mTxtName = (TextView) view.findViewById(R.id.title);
        mTxtContent = (TextView) view.findViewById(R.id.title_content);
        img_select = (ImageView) view.findViewById(R.id.img_select);
        return view;
    }

    public void bindView(final TopBtn mTopBtn, final int position) {
        mTxtName.setText(mTopBtn.mTemplate10Been.get(position).name);
        setData(mTopBtn,position);
        img_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRoles == null) {//服务器获取数据
                    TemplateCommonPresenter commonPresenter = new TemplateCommonPresenter(mContext);
                    commonPresenter.getSelectParentMenu(new TemplateCommonPresenter.ISelectList<MenuParent>() {
                        @Override
                        public void onGetList(List<MenuParent> list) {
                            mRoles = list;
                            showSelectRoleDialog(mTopBtn, position);
                        }
                    });
                } else {
                    showSelectRoleDialog(mTopBtn, position);
                }
            }
        });
    }

    protected void setData(TopBtn mTopBtn, int position) {
        Template10Bean template10Bean = mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key);
        mTxtContent.setText(TextUtils.isEmpty(template10Bean.showEditValue) ? template10Bean.originValue.toString() : template10Bean.showEditValue);
        if (TextUtils.isEmpty(template10Bean.content)) {
            template10Bean.content = template10Bean.originValue.toString();
        }
    }

    private void showSelectRoleDialog(TopBtn mTopBtn, int position) {
        final Template10Bean template10Bean = mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key);
        SingleSelectDialog dialog = new SingleSelectDialog<MenuParent>(mContext) {
            @Override
            public SingleSelectAdapter getAdapter() {
                return new SingleSelectAdapter(mContext){
                    @Override
                    public void bindView(TextView textView,int pos) {
                        textView.setText(mRoles.get(pos).text);
                    }
                };
            }
        };
        dialog.show();
        dialog.setDatas(mRoles);
        dialog.setDialogListener(new SingleSelectDialog.IDialogClick<MenuParent>() {
            @Override
            public void onConfirm(MenuParent bean,int pos) {
                updateTemplateBean(template10Bean,bean);
                mINotifyChange.onNotifyChange();
            }
        });
    }

    protected void updateTemplateBean(Template10Bean template10Bean, MenuParent bean) {
        template10Bean.showEditValue = bean.text;
        template10Bean.content = bean.id;
    }

}
