package com.onesoft.digitaledu.view.activity.publicfoundation.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.MenuTitle;
import com.yancy.gallerypick.utils.AppUtils;
import com.yancy.gallerypick.utils.ScreenUtils;

import java.util.List;

/**
 * 菜单标题栏
 * Created by Jayden on 2016/11/17.
 */

public class MenuTitleAdapter extends RecyclerView.Adapter<MenuTitleAdapter.ViewHolder> {

    //实现单选  方法二，变量保存当前选中的position
    private int mSelectedPos = -1;

    private Context mContext;
    private List<MenuTitle> mTitles;
    private int width = 0;

    public MenuTitleAdapter(Context context) {
        this.mContext = context;
        width = (ScreenUtils.getScreenWidth(context) - (int) AppUtils.dipToPx(context, 35.0f)) / 4;
    }

    public void setTitles(List<MenuTitle> titles) {
        mTitles = titles;
        for (int i = 0; i < titles.size(); i++) {
            if (titles.get(i).isSelected) {
                mSelectedPos = i;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        MenuTitle menuTitle = mTitles.get(position);
        if (position == 0) {
            holder.mImageView.setVisibility(View.INVISIBLE);
        } else {
            holder.mImageView.setVisibility(View.VISIBLE);
        }
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.itemView.setLayoutParams(layoutParams);
        holder.name.setText(menuTitle.title);
        if (mSelectedPos == position) {
            holder.divider.setBackgroundColor(mContext.getResources().getColor(R.color.color_tab_selected));
            holder.name.setTextColor(mContext.getResources().getColor(R.color.color_tab_selected));
            holder.mImageView.setImageResource(mTitles.get(position).isUp?R.drawable.icon_sort_on:R.drawable.icon_sort_under);
        } else {
            holder.divider.setBackgroundColor(mContext.getResources().getColor(android.R.color.white));
            holder.name.setTextColor(mContext.getResources().getColor(R.color.color_BBBBBB));
            holder.mImageView.setImageResource(R.drawable.icon_sort_gray);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedPos != position) {
                    mTitles.get(mSelectedPos).isSelected = false;
                    notifyItemChanged(mSelectedPos);
                    mSelectedPos = position;
                    mTitles.get(mSelectedPos).isSelected = true;
                    mTitles.get(mSelectedPos).isUp = !mTitles.get(mSelectedPos).isUp;//升序，降序切换
                    notifyItemChanged(mSelectedPos);
                } else {
                    mTitles.get(mSelectedPos).isUp = !mTitles.get(mSelectedPos).isUp;//升序，降序切换
                    notifyItemChanged(mSelectedPos);
                }
                if (mOnClickItemListener != null) {
                    mOnClickItemListener.onClickItem(mTitles.get(mSelectedPos));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTitles == null ? 0 : mTitles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView mImageView;
        View divider;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txt_menu_title);
            divider = itemView.findViewById(R.id.divider);
            mImageView = (ImageView) itemView.findViewById(R.id.img_menu_order);
        }
    }

    private OnClickItemListener mOnClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        mOnClickItemListener = onClickItemListener;
    }

    public interface OnClickItemListener {
        void onClickItem(MenuTitle menuTitle);
    }
}
