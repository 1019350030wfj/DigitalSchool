package com.onesoft.digitaledu.view.activity.publicfoundation.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.TopBtn;

import java.util.List;

import static com.onesoft.digitaledu.view.utils.TopBtnHelper.TOP_BTN_BATCH_DELETE;
import static com.onesoft.digitaledu.view.utils.TopBtnHelper.TOP_BTN_DELETE;
import static com.onesoft.digitaledu.view.utils.TopBtnHelper.TOP_BTN_DOWNLOAD;
import static com.onesoft.digitaledu.view.utils.TopBtnHelper.TOP_BTN_EDIT;
import static com.onesoft.digitaledu.view.utils.TopBtnHelper.TOP_BTN_EXPORT;
import static com.onesoft.digitaledu.view.utils.TopBtnHelper.TOP_BTN_IMPORT;
import static com.onesoft.digitaledu.view.utils.TopBtnHelper.TOP_BTN_LOOK;
import static com.onesoft.digitaledu.view.utils.TopBtnHelper.TOP_BTN_NINE;
import static com.onesoft.digitaledu.view.utils.TopBtnHelper.TOP_BTN_UPLOAD;

/**
 * 菜单caozuo
 * Created by Jayden on 2016/11/17.
 */

public class MenuOperatorAdapter extends RecyclerView.Adapter<MenuOperatorAdapter.ViewHolder> {

    private Context mContext;
    private List<TopBtn> mTitles;

    public MenuOperatorAdapter(Context context) {
        this.mContext = context;
    }

    public void setTitles(List<TopBtn> titles) {
        mTitles = titles;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        switch (Integer.parseInt(mTitles.get(position).btn_type)){//通过btn_type来判断
                case TOP_BTN_EDIT: {
                    holder.mImageView.setBackgroundResource(R.drawable.bg_btn_edity);
                    break;
                }
                case TOP_BTN_DELETE: {
                    holder.mImageView.setBackgroundResource(R.drawable.bg_btn_del2);
                    break;
                }
                case TOP_BTN_IMPORT: {
                    holder.mImageView.setBackgroundResource(R.drawable.bg_btn_datarecovery);
                    break;
                }
                case TOP_BTN_EXPORT: {
                    holder.mImageView.setBackgroundResource(R.drawable.bg_btn_restore);
                    break;
                }
                case TOP_BTN_UPLOAD: {
                    holder.mImageView.setBackgroundResource(R.drawable.bg_btn_restore);
                    break;
                }
                case TOP_BTN_BATCH_DELETE: {
                    holder.mImageView.setBackgroundResource(R.drawable.bg_btn_del2);
                    break;
                }
                case TOP_BTN_LOOK: {
                    holder.mImageView.setBackgroundResource(R.drawable.bg_btn_view);
                    break;
                }
                case TOP_BTN_NINE: {
                    holder.mImageView.setBackgroundResource(R.drawable.bg_btn_datarecovery);
                    break;
                }
                case TOP_BTN_DOWNLOAD: {
                    holder.mImageView.setBackgroundResource(R.drawable.bg_btn_download);
                    break;
                }
            }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickItemListener != null) {
                    mOnClickItemListener.onClickItem(mTitles.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTitles == null ? 0 : mTitles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    private OnClickItemListener mOnClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        mOnClickItemListener = onClickItemListener;
    }

    public interface OnClickItemListener {
        void onClickItem(TopBtn topBtn);
    }
}
