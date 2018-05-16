package com.onesoft.digitaledu.view.activity.publicfoundation.menu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.MenuDetail;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;
import com.onesoft.digitaledu.widget.DividerItemDecoration;
import com.onesoft.digitaledu.widget.ExpandableLayout;

/**
 * Created by Jayden on 2016/11/22.
 */

public class MenuDetailAdapter extends BaseAbsListAdapter<MenuDetail> {

    public MenuDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_menu_detail, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.img_del =convertView.findViewById(R.id.img_del);
            viewHolder.number = (TextView) convertView.findViewById(R.id.txt_number);
            viewHolder.menuName = (TextView) convertView.findViewById(R.id.txt_menu_name);
            viewHolder.menuParent = (TextView) convertView.findViewById(R.id.txt_menu_parent);
            viewHolder.menuImage = (TextView) convertView.findViewById(R.id.txt_menu_image);
            viewHolder.isShow = (TextView) convertView.findViewById(R.id.txt_is_show);
            viewHolder.txtExpand = (TextView) convertView.findViewById(R.id.txt_expand);
            viewHolder.txtShrink = (TextView) convertView.findViewById(R.id.txt_shrink);
            viewHolder.mTxtExpandHeader = convertView.findViewById(R.id.ll_expand_header);
            viewHolder.mOperator = (RecyclerView) convertView.findViewById(R.id.rv_operator);
            viewHolder.mExpandableLayout = (ExpandableLayout) convertView.findViewById(R.id.expand_item);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL_LIST);
            dividerItemDecoration.setDivider(mContext.getDrawable(R.drawable.shape_divider_bg_fff_10));
            viewHolder.mOperator.addItemDecoration(dividerItemDecoration);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final MenuDetail menuDetail = getItem(position);
        viewHolder.number.setText(menuDetail.number);
        viewHolder.menuName.setText(menuDetail.menuName);
        viewHolder.menuParent.setText(menuDetail.menuParent);
        viewHolder.menuImage.setText(menuDetail.menuImage);
        viewHolder.isShow.setText(menuDetail.isShow);

        if (mISDeleteMode) {//删除模式
            viewHolder.img_del.setVisibility(View.VISIBLE);
            if (menuDetail.isDelete) {//被选中要删除
                viewHolder.img_del.setBackgroundResource(R.drawable.icon_mess_sel2_pre);
            } else {
                viewHolder.img_del.setBackgroundResource(R.drawable.icon_mess_sel2_nor);
            }
        } else {
            viewHolder.img_del.setVisibility(View.INVISIBLE);
        }

        if (menuDetail.operatorSrc != null && menuDetail.operatorSrc.size() > 0) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            viewHolder.mOperator.setLayoutManager(layoutManager);
            MenuOperatorAdapter menuOperatorAdapter = new MenuOperatorAdapter(mContext);
//            menuOperatorAdapter.setTitles(menuDetail.operatorSrc);
            viewHolder.mOperator.setAdapter(menuOperatorAdapter);

            final ViewHolder finalViewHolder = viewHolder;
            viewHolder.mExpandableLayout.setExpandResultListener(new ExpandableLayout.IExpandResult() {
                @Override
                public void onResult(boolean isExpand) {
                    if (isExpand) {
                        finalViewHolder.mTxtExpandHeader.setVisibility(View.GONE);
//                        Drawable nav_up=mContext.getResources().getDrawable(R.drawable.icon_expand);
//                        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
//                        finalViewHolder.txtShrink.setCompoundDrawables(null, null, null, nav_up);
                    }
//                    else {
//                        finalViewHolder.txtExpand.setText(mContext.getString(R.string.open));
//
//                    }
                }
            });
            viewHolder.txtShrink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalViewHolder.mTxtExpandHeader.setVisibility(View.VISIBLE);
                    finalViewHolder.mExpandableLayout.hide();
                }
            });

            viewHolder.img_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mISDeleteMode){//删除模式
                        menuDetail.isDelete = !menuDetail.isDelete;
                        notifyDataSetChanged();
                    }
                }
            });
        }
        return convertView;
    }

    private class ViewHolder {
        TextView number;
        TextView menuParent;
        TextView menuName;
        TextView isShow;
        TextView menuImage;
        RecyclerView mOperator;

        TextView txtExpand;
        TextView txtShrink;
        View mTxtExpandHeader;
        View img_del;
        ExpandableLayout mExpandableLayout;
    }
}
