package com.onesoft.digitaledu.widget.treerecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.widget.treerecyclerview.interfaces.ItemDataClickListener;
import com.onesoft.digitaledu.widget.treerecyclerview.interfaces.OnScrollToListener;
import com.onesoft.digitaledu.widget.treerecyclerview.model.TreeItem;
import com.onesoft.digitaledu.widget.treerecyclerview.viewholder.BaseViewHolder;
import com.onesoft.digitaledu.widget.treerecyclerview.viewholder.TreeItem1Holder;
import com.onesoft.digitaledu.widget.treerecyclerview.viewholder.TreeItem2Holder;
import com.onesoft.digitaledu.widget.treerecyclerview.viewholder.TreeItem3Holder;
import com.onesoft.digitaledu.widget.treerecyclerview.viewholder.TreeViewDetailHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jayden on 2016/11/8.
 */

public class TreeRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<TreeItem> mDataSet;
    private OnScrollToListener onScrollToListener;

    public void setOnScrollToListener(OnScrollToListener onScrollToListener) {
        this.onScrollToListener = onScrollToListener;
    }

    public TreeRecyclerAdapter(Context context) {
        mContext = context;
        mDataSet = new ArrayList<>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TreeItem.ITEM_TYPE_PARENT1: {
                view = LayoutInflater.from(mContext).inflate(
                        R.layout.item_tree_recycler_1, parent, false);
                return new TreeItem1Holder(view);
            }
            case TreeItem.ITEM_TYPE_PARENT2: {
                view = LayoutInflater.from(mContext).inflate(
                        R.layout.item_tree_recycler_2, parent, false);
                return new TreeItem2Holder(view);
            }
            case TreeItem.ITEM_TYPE_PARENT3: {
                view = LayoutInflater.from(mContext).inflate(
                        R.layout.item_tree_recycler_3, parent, false);
                return new TreeItem3Holder(view);
            }
            case TreeItem.ITEM_TYPE_CHILD: {
                view = LayoutInflater.from(mContext).inflate(
                        R.layout.item_tree_recycler_detail, parent, false);
                return new TreeViewDetailHolder(view);
            }
            default:
                view = LayoutInflater.from(mContext).inflate(
                        R.layout.item_tree_recycler_detail, parent, false);
                return new TreeViewDetailHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TreeItem.ITEM_TYPE_PARENT1: {
                TreeItem1Holder imageViewHolder = (TreeItem1Holder) holder;
                imageViewHolder.bindView(mDataSet.get(position), position,
                        imageClickListener);
                break;
            }
            case TreeItem.ITEM_TYPE_PARENT2: {
                TreeItem2Holder imageViewHolder = (TreeItem2Holder) holder;
                imageViewHolder.bindView(mDataSet.get(position), position,
                        imageClickListener);
                break;
            }
            case TreeItem.ITEM_TYPE_PARENT3: {
                TreeItem3Holder imageViewHolder = (TreeItem3Holder) holder;
                imageViewHolder.bindView(mDataSet.get(position), position,
                        imageClickListener);
                break;
            }
            case TreeItem.ITEM_TYPE_CHILD: {
                TreeViewDetailHolder textViewHolder = (TreeViewDetailHolder) holder;
                textViewHolder.bindView(mDataSet.get(position), position);
                break;
            }
            default:
                break;
        }
    }

    private ItemDataClickListener imageClickListener = new ItemDataClickListener() {
        @Override
        public void onSelectChange(TreeItem itemData) {
            List<TreeItem> children = itemData.mChildren;
            if (children == null || children.size() == 0) {
                return;
            }
            for (TreeItem item : children) {
                item.isSelect = itemData.isSelect;
            }
            notifyDataSetChanged();
        }

        @Override
        public void onExpandChildren(TreeItem itemData) {
            int position = getCurrentPosition(itemData.uuid);
            List<TreeItem> children = itemData.mChildren;
            if (children == null) {
                return;
            }
            addAll(children, position + 1);// 插入到点击点的下方
            if (onScrollToListener != null) {
                onScrollToListener.scrollTo(position + 1);
            }
        }

        @Override
        public void onHideChildren(TreeItem itemData) {
            int position = getCurrentPosition(itemData.uuid);
            mRemoveCount = 0;
            getRemoveCount(itemData);
            removeAll(position + 1, mRemoveCount);
            if (onScrollToListener != null) {
                onScrollToListener.scrollTo(position);
            }
        }
    };

    private int mRemoveCount = 0;

    private void getRemoveCount(TreeItem treeItem) {
        if (treeItem == null || !treeItem.isExpand) {
            mRemoveCount += 0;
            return;
        }
        if (treeItem.mChildren == null || treeItem.mChildren.size() == 0) {
            mRemoveCount += 0;
            return;
        }
        mRemoveCount += treeItem.mChildren.size();
        for (TreeItem item : treeItem.mChildren) {
            getRemoveCount(item);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    /**
     * 从position开始删除，删除
     *
     * @param position
     * @param itemCount 删除的数目
     */
    protected void removeAll(int position, int itemCount) {
        for (int i = 0; i < itemCount; i++) {
            mDataSet.get(position).isExpand = false;
            mDataSet.remove(position);
        }
        notifyItemRangeRemoved(position, itemCount);
    }

    protected int getCurrentPosition(String uuid) {
        for (int i = 0; i < mDataSet.size(); i++) {
            if (uuid.equalsIgnoreCase(mDataSet.get(i).uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSet.get(position).type;
    }

    public void add(TreeItem text, int position) {
        mDataSet.add(position, text);
        notifyItemInserted(position);
    }

    public void addAll(List<TreeItem> list, int position) {
        mDataSet.addAll(position, list);
        notifyItemRangeInserted(position, list.size());
    }

    public void delete(int pos) {
        if (pos >= 0 && pos < mDataSet.size()) {
            if (mDataSet.get(pos).type == TreeItem.ITEM_TYPE_PARENT1
                    && mDataSet.get(pos).isExpand) {// 父组件并且子节点已经展开
                for (int i = 0; i < mDataSet.get(pos).mChildren.size() + 1; i++) {
                    mDataSet.remove(pos);
                }
                notifyItemRangeRemoved(pos, mDataSet.get(pos).mChildren
                        .size() + 1);
            } else {// 孩子节点，或没有展开的父节点
                mDataSet.remove(pos);
                notifyItemRemoved(pos);
            }
        }
    }


    private boolean mIsAllSelect;

    public void setAllSelect(boolean select) {
        this.mIsAllSelect = select;
        changeSelectState(mDataSet);
        notifyDataSetChanged();
    }

    private void changeSelectState(List<TreeItem> items) {
        if (items == null || items.size() == 0) {
            return;
        }
        for (TreeItem item : items) {
            changeSelectState(item.mChildren);
            item.isSelect = mIsAllSelect;
        }
    }
}
