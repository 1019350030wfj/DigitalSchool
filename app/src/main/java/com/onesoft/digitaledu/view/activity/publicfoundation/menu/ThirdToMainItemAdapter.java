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
import com.onesoft.digitaledu.model.ThirdToMainBean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.utils.ViewUtil;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;
import com.onesoft.digitaledu.widget.ExpandableLayout;
import com.onesoft.digitaledu.widget.LayoutListView;
import com.yancy.gallerypick.utils.AppUtils;

import java.util.List;

/**
 * 动态列表项item中的每一个item，就是item中的item
 * Created by Jayden on 2016/11/22.
 */

public class ThirdToMainItemAdapter extends BaseAbsListAdapter<KeyValueBean> {

    public static final int ITEM_TYPE_STRING = 0;
    public static final int ITEM_TYPE_ARRAY = 1;
    public static final int ITEM_TYPE_EXPAND = 2;
    public static final int SHOW_ITEM_COUNT = 6;

    public ThirdToMainItemAdapter(Context context) {
        super(context);
    }

    private String mSearchContent;

    public void setSearchContent(String searchContent) {
        mSearchContent = searchContent;
    }

    private List<KeyValueBean> mExpandValues;//点击展开的数据集合
    private ThirdToMainBean mThirdToMainBean;//动态列表的item，这个item包含datas数据

    public void setThirdToMainBean(ThirdToMainBean thirdToMainBean) {
        this.mThirdToMainBean = thirdToMainBean;
    }

    private int mContentHeight = 0;

    @Override
    public void setDatas(List<KeyValueBean> datas) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        if (datas.size() > SHOW_ITEM_COUNT) {//大于7的话，取出大于7的后面列表数据
            mExpandValues = datas.subList(SHOW_ITEM_COUNT, datas.size());
            mContentHeight = (mExpandValues.size()+1) * (int)AppUtils.dipToPx(mContext,32);
        }
        mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        switch (getItemViewType(position)) {
            case ITEM_TYPE_STRING: {
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_item_third_to_main_string, parent, false);
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
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_item_third_to_main_array, parent, false);
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
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_item_third_to_main_expand, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.mExpandableLayout = (ExpandableLayout) convertView.findViewById(R.id.expand_item);
                    viewHolder.mTxtExpandHeader = convertView.findViewById(R.id.ll_expand_header);
                    viewHolder.mListView = (LayoutListView) convertView.findViewById(R.id.listview);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                final ViewHolder finalViewHolder = viewHolder;
                viewHolder.mExpandableLayout.setContentHeight(mContentHeight);

                ThirdToMainItemExpandAdapter itemAdapter = new ThirdToMainItemExpandAdapter(mContext);
                itemAdapter.setSearchContent(mSearchContent);
                itemAdapter.setDatas(mExpandValues);//设置从第八条到最后一条数据
                itemAdapter.setOnClickItemListener(new ThirdToMainItemExpandAdapter.OnThirdItemExpandClickListener() {
                    @Override
                    public void onClickItem(TopBtn topBtn) {
                        if (mOnClickItemListener != null) {
                            mOnClickItemListener.onClickItem(topBtn);
                        }
                    }

                    @Override
                    public void onCollapse() {
                        finalViewHolder.mExpandableLayout.hide();
                    }
                });
                viewHolder.mListView.setAdapter(itemAdapter);

                viewHolder.mExpandableLayout.setExpandResultListener(new ExpandableLayout.IExpandResult() {
                    @Override
                    public void onResult(boolean isExpand) {
                        if (isExpand) {
                            finalViewHolder.mTxtExpandHeader.setVisibility(View.GONE);
                        } else {
                            finalViewHolder.mTxtExpandHeader.setVisibility(View.VISIBLE);
                        }
                        mThirdToMainBean.isExpand = isExpand;//记录当前是否是展开的标记
                    }
                });
                if (mThirdToMainBean.isExpand) {
                    viewHolder.mExpandableLayout.show();
                } else {
                    viewHolder.mExpandableLayout.hide();
                }
                break;
            }
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == SHOW_ITEM_COUNT) {//最后一个是展开和收缩
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
        return mDatas == null ? 0 : mDatas.size() > SHOW_ITEM_COUNT ? SHOW_ITEM_COUNT + 1 : mDatas.size();
    }

    private MenuOperatorAdapter.OnClickItemListener mOnClickItemListener;

    public void setOnClickItemListener(MenuOperatorAdapter.OnClickItemListener onClickItemListener) {
        mOnClickItemListener = onClickItemListener;
    }

    private class ViewHolder {
        TextView key;
        TextView txtRecycler;
        TextView value;
        RecyclerView mOperator;

        ExpandableLayout mExpandableLayout;
        View mTxtExpandHeader;
        LayoutListView mListView;
    }
}
