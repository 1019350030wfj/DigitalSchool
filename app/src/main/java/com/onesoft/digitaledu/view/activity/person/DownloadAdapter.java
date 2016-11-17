package com.onesoft.digitaledu.view.activity.person;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.DownloadBean;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;
import com.onesoft.netlibrary.utils.ImageHandler;

/**
 * Created by Jayden on 2016/11/5.
 */

public class DownloadAdapter extends BaseAbsListAdapter<DownloadBean> {

    private boolean mISDeleteMode;

    public DownloadAdapter(Context context) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_downloaded, null);
            viewHolder = new ViewHolder();
            viewHolder.mView = convertView;
            viewHolder.mImgAvater = (ImageView) convertView.findViewById(R.id.avatar);
            viewHolder.mImgDelete = convertView.findViewById(R.id.img_del);
            viewHolder.mTxtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.mTxtContent = (TextView) convertView.findViewById(R.id.message);
            viewHolder.mImgLook = (ImageView) convertView.findViewById(R.id.img_look);
            viewHolder.mViewDot = convertView.findViewById(R.id.view_dot);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DownloadBean boxBean = getItem(position);
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
        ImageHandler.getImage((Activity) mContext, viewHolder.mImgAvater, boxBean.imgUrl,R.drawable.icon_my_li);
        viewHolder.mTxtName.setText(boxBean.filename);
        viewHolder.mTxtContent.setText(boxBean.filesize);

        viewHolder.mImgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mISDeleteMode) {//删除模式
                    mDatas.get(position).isDelete = !boxBean.isDelete;
                    notifyDataSetChanged();
                }
            }
        });
        return convertView;
    }


    static class ViewHolder {
        View mView;
        TextView mTxtName;
        TextView mTxtContent;
        View mViewDot;
        ImageView mImgAvater;
        ImageView mImgLook;
        View mImgDelete;
    }
}
