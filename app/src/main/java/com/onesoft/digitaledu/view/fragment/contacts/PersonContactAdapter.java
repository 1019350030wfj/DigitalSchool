package com.onesoft.digitaledu.view.fragment.contacts;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.PersonContact;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;
import com.onesoft.netlibrary.utils.ImageHandler;

import static com.onesoft.digitaledu.R.id.avatar;

/**
 * Created by Jayden on 2016/11/30.
 */

public class PersonContactAdapter extends BaseAbsListAdapter<PersonContact> {

    public PersonContactAdapter(Context context) {
        super(context);
    }
    private String mSearchContent;

    public void setSearchContent(String searchContent) {
        mSearchContent = searchContent;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_person_contact, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.select = (ImageView) convertView.findViewById(R.id.img_select);
            viewHolder.avatar = (ImageView) convertView.findViewById(avatar);
            viewHolder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.container);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PersonContact itemData = getItem(position);
        viewHolder.name.setText(itemData.name);
        viewHolder.title.setText(itemData.phoneNumber);
        ImageHandler.getAvater((Activity) mContext,viewHolder.avatar,itemData.photo);

        return convertView;
    }

    private static class ViewHolder {
        public TextView name;
        public TextView title;
        public ImageView avatar;
        public ImageView select;
        public RelativeLayout relativeLayout;
    }
}
