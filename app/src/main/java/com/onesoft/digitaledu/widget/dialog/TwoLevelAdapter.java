package com.onesoft.digitaledu.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.TwoLevelMenuData;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;
import com.onesoft.digitaledu.widget.ExpandableLayout;

/**
 * 二级菜单
 * Created by Jayden on 2016/11/24.
 */

public class TwoLevelAdapter extends BaseAbsListAdapter<TwoLevelMenuData> {

    public TwoLevelAdapter(Context context) {
        super(context);
    }

    private boolean isExpand;

    public void setShow(boolean show) {
        if (isExpand != show) {
            isExpand = show;
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_dialog_twolevel, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.select = (CheckBox) convertView.findViewById(R.id.img_select);
            viewHolder.mListView = (ListView) convertView.findViewById(R.id.item_listview);
            viewHolder.mExpandableLayout = (ExpandableLayout) convertView.findViewById(R.id.expandLayout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TwoLevelMenuData item = getItem(position);
        viewHolder.name.setText(item.name);
        viewHolder.select.setChecked(viewHolder.mExpandableLayout.isOpened());
        if (item.mSubMenuList != null && item.mSubMenuList.size() > 0) {
            SubMenuItemAdapter subMenuItemAdapter = new SubMenuItemAdapter(mContext);
            subMenuItemAdapter.setDatas(item.mSubMenuList);
            viewHolder.mListView.setAdapter(subMenuItemAdapter);
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        CheckBox select;
        ListView mListView;
        ExpandableLayout mExpandableLayout;

    }
}
