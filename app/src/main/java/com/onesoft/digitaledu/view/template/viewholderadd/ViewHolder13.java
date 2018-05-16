package com.onesoft.digitaledu.view.template.viewholderadd;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.model.UserTreeEvent;
import com.onesoft.digitaledu.widget.treerecyclerview.activity.TreeActivity;
import com.onesoft.netlibrary.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 文字+"必选"+右边加号，弹出弹框 用户树
 * Created by Jayden on 2016/12/21.
 */

public class ViewHolder13 extends BaseViewHolder {

    protected TextView mTxtName;
    protected TextView mTxtContent;
    protected ImageView img_select;

    private TopBtn mTopBtn;
    private int pos;

    public ViewHolder13(Context context) {
        mContext = context;
        EventBus.getDefault().register(this);
    }

    /**
     * 使用事件总线监听回调
     *
     * @param event
     */
    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBaseEvent(final UserTreeEvent event) {
        updateTemplateBean(mTopBtn.mTemplate10Been.get(pos), event.name, event.id);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public View createView(ViewGroup parent) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_viewholder_13, parent, false);
        mTxtName = (TextView) view.findViewById(R.id.title);
        mTxtContent = (TextView) view.findViewById(R.id.title_content);
        img_select = (ImageView) view.findViewById(R.id.img_select);
        view.getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() {
            @Override
            public void onWindowAttached() {
                LogUtil.e("onWindowAttached ");
            }

            @Override
            public void onWindowDetached() {
                LogUtil.e("onWindowDetached");
                EventBus.getDefault().unregister(this);
            }
        });
        return view;
    }

    @Override
    public void bindView(final TopBtn mTopBtn, final int position) {
        this.mTopBtn = mTopBtn;
        pos = position;
        mTxtName.setText(mTopBtn.mTemplate10Been.get(position).name);
        setData(mTopBtn, position);
        img_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TreeActivity.startUserTreeActivity(mContext, true);
            }
        });
    }

    protected void setData(TopBtn mTopBtn, int position) {
        mTxtContent.setText(TextUtils.isEmpty(mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key).showValue)
                ? mContext.getString(R.string.need_select)
                : mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key).showValue);
    }

    protected void updateTemplateBean(Template10Bean template10Bean, String builder, String idBuilder) {
        template10Bean.showValue = builder;
        template10Bean.content = idBuilder;
        mINotifyChange.onNotifyChange();
    }

}
