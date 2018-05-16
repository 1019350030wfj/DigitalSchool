package com.onesoft.digitaledu.view.activity.publicfoundation.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.ThirdToMainBean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.view.OnDeleteListener;
import com.onesoft.digitaledu.view.utils.TemplateHelper;
import com.onesoft.digitaledu.widget.LayoutListView;

import java.util.List;

/**
 * 三级菜单到动态列表页
 * Created by Jayden on 2016/11/22.
 */

public class ThirdToMainAdapter extends BaseAdapter {

    private TemplateHelper mTemplateHelper;

    public ThirdToMainAdapter(Context context) {
        this.mContext = context;
        mISDeleteMode = false;
        mTemplateHelper = new TemplateHelper();
    }

    protected Context mContext;
    protected List<ThirdToMainBean> mDatas;

    protected boolean mISDeleteMode;

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

    public void setDatas(List<ThirdToMainBean> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param list
     */
    public void addData(final List<ThirdToMainBean> list) {
        if (mDatas == null) {
            mDatas = list;
        } else {
            mDatas.addAll(list);
        }
        notifyDataSetChanged();
    }

    public List<ThirdToMainBean> getDatas() {
        return mDatas;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public ThirdToMainBean getItem(int position) {
        return mDatas == null ? null : mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private String mSearchContent;

    public void setSearchContent(String searchContent) {
        mSearchContent = searchContent;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_third_to_main_detail, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.img_del = convertView.findViewById(R.id.img_del);
            viewHolder.mListView = (LayoutListView) convertView.findViewById(R.id.listview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ThirdToMainBean menuDetail = getItem(position);
        ThirdToMainItemAdapter itemAdapter = new ThirdToMainItemAdapter(mContext);
        itemAdapter.setSearchContent(mSearchContent);
        itemAdapter.setThirdToMainBean(menuDetail);
        itemAdapter.setDatas(menuDetail.mShownList);
        itemAdapter.setOnClickItemListener(new MenuOperatorAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(TopBtn topBtn) {
                mTemplateHelper.handleToTemplate(mContext,menuDetail,topBtn);
            }
        });
        viewHolder.mListView.setAdapter(itemAdapter);

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
        viewHolder.img_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mISDeleteMode){//删除模式
                    menuDetail.isDelete = !menuDetail.isDelete;
                    if (mOnDeleteListener != null) {
                        mOnDeleteListener.onDelete();
                    }
                    notifyDataSetChanged();
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        View img_del;
        LayoutListView mListView;
    }


    private OnDeleteListener mOnDeleteListener;

    public void setOnDeleteListener(OnDeleteListener listener) {
        this.mOnDeleteListener = listener;
    }
}
