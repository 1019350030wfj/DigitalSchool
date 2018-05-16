package com.onesoft.digitaledu.view.fragment.contacts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.view.activity.infomanager.contacts.ContactGroupInfoActivity;
import com.onesoft.digitaledu.view.activity.message.SendMessageActivity;
import com.onesoft.digitaledu.widget.treerecyclerview.interfaces.ItemDataClickListener;
import com.onesoft.digitaledu.widget.treerecyclerview.interfaces.OnScrollToListener;
import com.onesoft.digitaledu.widget.treerecyclerview.interfaces.OnTreeDetailClickListener;
import com.onesoft.digitaledu.widget.treerecyclerview.model.TreeItem;
import com.onesoft.digitaledu.widget.treerecyclerview.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 群组联系人
 * Created by Jayden on 2017/01/03.
 */
public class ContactGroupAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<TreeItem> mDataSet;
    private OnScrollToListener onScrollToListener;

    public void setOnScrollToListener(OnScrollToListener onScrollToListener) {
        this.onScrollToListener = onScrollToListener;
    }

    public ContactGroupAdapter(Context context) {
        mContext = context;
        mDataSet = new ArrayList<>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TreeItem.ITEM_TYPE_PARENT1: {
                view = LayoutInflater.from(mContext).inflate(
                        R.layout.item_tree_contact_group_1, parent, false);
                return new TreeContactGroup1Holder(view);
            }
            case TreeItem.ITEM_TYPE_CHILD: {
                view = LayoutInflater.from(mContext).inflate(
                        R.layout.item_tree_recycler_detail_contact, parent, false);
                return new TreeViewDetailHolder(view);
            }
            default:
                view = LayoutInflater.from(mContext).inflate(
                        R.layout.item_tree_recycler_detail_contact, parent, false);
                return new TreeViewDetailHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TreeItem.ITEM_TYPE_PARENT1: {
                TreeContactGroup1Holder imageViewHolder = (TreeContactGroup1Holder) holder;
                imageViewHolder.bindView(mDataSet.get(position), position,
                        imageClickListener, new ItemOperatorListener() {
                            @Override
                            public void onSendMessage(TreeItem item) {
                                //跳转到发送消息
                                SendMessageActivity.startSendMessageActivity(mContext,
                                        SendMessageActivity.FROM_GROUP_CONTACT_MASS,item.name,item.id);
                            }

                            @Override
                            public void onAddUsers(TreeItem item) {
                                if (mOperatorListener != null){
                                    mOperatorListener.onAddUsers(item);
                                }
                            }

                            @Override
                            public void onDelete(TreeItem item) {
                                if (mOperatorListener != null){
                                    mOperatorListener.onDelete(item);
                                }
                            }
                        });
                break;
            }
            case TreeItem.ITEM_TYPE_CHILD: {
                TreeViewDetailHolder textViewHolder = (TreeViewDetailHolder) holder;
                textViewHolder.bindView(mDataSet.get(position), new OnTreeDetailClickListener() {

                    @Override
                    public void onClick(TreeItem itemData) {
                        ContactGroupInfoActivity.startInfoActivity(mContext,itemData);
                    }
                });
                break;
            }
            default:
                break;
        }
    }

    private ItemOperatorListener mOperatorListener;

    public void setOperatorListener(ItemOperatorListener operatorListener) {
        mOperatorListener = operatorListener;
    }

    public interface ItemOperatorListener{
        void onSendMessage(TreeItem item);
        void onAddUsers(TreeItem item);
        void onDelete(TreeItem item);
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

    public void setDatas(List<TreeItem> list){
        mDataSet.clear();
        mDataSet.addAll(list);
        notifyDataSetChanged();
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
