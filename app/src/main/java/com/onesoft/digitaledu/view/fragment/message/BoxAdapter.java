package com.onesoft.digitaledu.view.fragment.message;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BoxBean;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;
import com.onesoft.netlibrary.utils.ImageHandler;

/**
 * Created by Jayden on 2016/11/5.
 */

public class BoxAdapter extends BaseAbsListAdapter<BoxBean> {

    private boolean mISDeleteMode;

    public BoxAdapter(Context context) {
        super(context);
        mISDeleteMode = false;
    }

    /**
     * 是否是删除模式
     *
     * @param ISDeleteMode
     */
    public void setISDeleteMode(boolean ISDeleteMode) {
        if (mISDeleteMode != ISDeleteMode) {
            mISDeleteMode = ISDeleteMode;
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_box, null);
            viewHolder = new ViewHolder();
            viewHolder.mView = convertView;
            viewHolder.mImgAvater = (ImageView) convertView.findViewById(R.id.avatar);
            viewHolder.mImgDelete = convertView.findViewById(R.id.img_del);
            viewHolder.mImgAttach = convertView.findViewById(R.id.img_attach);
            viewHolder.mTxtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.mTxtTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.mTxtContent = (TextView) convertView.findViewById(R.id.message);
            viewHolder.mTxtTime = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final BoxBean boxBean = getItem(position);
        if (mISDeleteMode) {//删除模式
            viewHolder.mImgAvater.setVisibility(View.INVISIBLE);
            viewHolder.mImgDelete.setVisibility(View.VISIBLE);
            if (boxBean.isDelete) {//被选中要删除
                viewHolder.mImgDelete.setBackgroundResource(R.drawable.icon_mess_sel2_pre);
                viewHolder.mView.setBackgroundColor(mContext.getResources().getColor(R.color.color_fafafa));
            } else {
                viewHolder.mImgDelete.setBackgroundResource(R.drawable.icon_mess_sel2_nor);
                viewHolder.mView.setBackgroundColor(mContext.getResources().getColor(android.R.color.white));
            }
        } else {
            viewHolder.mImgAvater.setVisibility(View.VISIBLE);
            viewHolder.mImgDelete.setVisibility(View.INVISIBLE);
        }

        if ("0".equals(boxBean.is_read)) {//未读
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_dot_yellow);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getMinimumHeight());
            viewHolder.mTxtName.setCompoundDrawables(drawable, null, null, null);
        } else {//已读,或者没有
            viewHolder.mTxtName.setCompoundDrawables(null, null, null, null);
        }

        //有无附件
        viewHolder.mImgAttach.setVisibility(TextUtils.isEmpty(boxBean.attach_ids) ? View.INVISIBLE : View.VISIBLE);
        ImageHandler.getAvater((Activity) mContext, viewHolder.mImgAvater, boxBean.imgUrl);
        viewHolder.mTxtName.setText(boxBean.name);
        viewHolder.mTxtTitle.setText(boxBean.title);
        viewHolder.mTxtContent.setText(boxBean.content);
        viewHolder.mTxtTime.setText(boxBean.time);

        viewHolder.mImgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mISDeleteMode) {//删除模式
                    mDatas.get(position).isDelete = !boxBean.isDelete;
                    if (mCompleteListener != null) {
                        mCompleteListener.onDelete();
                    }
                    notifyDataSetChanged();
                }
            }
        });
        return convertView;
    }


    static class ViewHolder {
        View mView;
        TextView mTxtName;
        TextView mTxtTitle;
        TextView mTxtContent;
        TextView mTxtTime;
        ImageView mImgAvater;
        View mImgDelete;
        View mImgAttach;
    }

    private OnDeletedListener mCompleteListener;

    public void setOnDeletedListener(OnDeletedListener listener) {
        this.mCompleteListener = listener;
    }

    public interface OnDeletedListener {
        void onDelete();
    }
}
