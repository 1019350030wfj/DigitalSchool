package com.onesoft.digitaledu.view.activity.publicfoundation.menu;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.KeyValueBean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.utils.ViewUtil;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;

import java.util.List;

/**
 * 动态列表项item中的每一个item，就是item中的item
 * Created by Jayden on 2016/11/22.
 */

public class ThirdToMainItemExpandAdapter extends BaseAbsListAdapter<KeyValueBean> {

    public static final int ITEM_TYPE_STRING = 0;
    public static final int ITEM_TYPE_ARRAY = 1;
    public static final int ITEM_TYPE_EXPAND = 2;

    public ThirdToMainItemExpandAdapter(Context context) {
        super(context);
    }

    private String mSearchContent;

    public void setSearchContent(String searchContent) {
        mSearchContent = searchContent;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        switch (getItemViewType(position)) {
            case ITEM_TYPE_STRING: {
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_item_third_to_main_string_expand, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.key = (TextView) convertView.findViewById(R.id.item_item_key);
                    viewHolder.value = (TextView) convertView.findViewById(R.id.item_item_value);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                final KeyValueBean bean = getItem(position);
                viewHolder.key.setText(bean.key);
                if (TextUtils.isEmpty(mSearchContent)) {//为空说明不是搜索页面
                    viewHolder.value.setText(bean.value.toString());
                } else {
                    ViewUtil.setPartOfTextViewColor(viewHolder.value, bean.value.toString(), mSearchContent);
                }
                break;
            }
            case ITEM_TYPE_ARRAY: {
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_item_third_to_main_array_expand, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.txtRecycler = (TextView) convertView.findViewById(R.id.txt_recycler);
                    viewHolder.mOperator = (RecyclerView) convertView.findViewById(R.id.rv_operator);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                final KeyValueBean bean = getItem(position);
                viewHolder.txtRecycler.setText(bean.key);
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                viewHolder.mOperator.setLayoutManager(layoutManager);
                MenuOperatorAdapter menuOperatorAdapter = new MenuOperatorAdapter(mContext);
                menuOperatorAdapter.setOnClickItemListener(new MenuOperatorAdapter.OnClickItemListener() {
                    @Override
                    public void onClickItem(TopBtn topBtn) {//跳转到相应模版界面
                        if (mOnClickItemListener != null) {
                            mOnClickItemListener.onClickItem(topBtn);
                        }
                    }
                });
                menuOperatorAdapter.setTitles((List<TopBtn>) bean.value);
                viewHolder.mOperator.setAdapter(menuOperatorAdapter);
                break;
            }
            case ITEM_TYPE_EXPAND: {
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.expand_item_collapse, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.txtShrink = convertView.findViewById(R.id.ll_expand_header);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.txtShrink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { //点击收缩，回调
                        if (mOnClickItemListener != null) {
                            mOnClickItemListener.onCollapse();
                        }
                    }
                });
                break;
            }
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {//最后一个是展开和收缩
            return ITEM_TYPE_EXPAND;
        }
        KeyValueBean bean = getItem(position);
        if (bean.value instanceof String) {
            return ITEM_TYPE_STRING;
        } else if (bean.value instanceof List) {
            return ITEM_TYPE_ARRAY;
        }
        return ITEM_TYPE_STRING;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size() + 1;//加1是收缩项
    }

    private OnThirdItemExpandClickListener mOnClickItemListener;

    public void setOnClickItemListener(OnThirdItemExpandClickListener onClickItemListener) {
        mOnClickItemListener = onClickItemListener;
    }

    public interface OnThirdItemExpandClickListener{
        void onClickItem(TopBtn topBtn);

        void onCollapse();
    }

    private class ViewHolder {
        TextView key;
        TextView txtRecycler;
        TextView value;
        RecyclerView mOperator;

        View txtShrink;
    }
}
