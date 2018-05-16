package com.onesoft.digitaledu.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.TwoLevelSubMenu;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;

/**
 * Created by Jayden on 2016/11/24.
 */

public class SubMenuItemAdapter extends BaseAbsListAdapter<TwoLevelSubMenu> {

    public SubMenuItemAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_dialog_sub_menu, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.mCheckBox = (CheckBox) convertView.findViewById(R.id.img_sub_menu_check);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TwoLevelSubMenu item = getItem(position);
        viewHolder.mCheckBox.setChecked(item.isSelect);
        viewHolder.name.setText(item.name);
        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        CheckBox mCheckBox;
    }
}
