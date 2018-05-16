package com.onesoft.digitaledu.widget.treerecyclerview.viewholder;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.widget.treerecyclerview.model.TreeItem;
import com.onesoft.netlibrary.utils.ImageHandler;

public class TreeViewDetailHolder extends BaseViewHolder {

    public TextView name;
    public TextView title;
    public ImageView avatar;
    public ImageView select;
    public RelativeLayout relativeLayout;

    public TreeViewDetailHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name);
        title = (TextView) itemView.findViewById(R.id.title);
        select = (ImageView) itemView.findViewById(R.id.img_select);
        avatar = (ImageView) itemView.findViewById(R.id.avatar);
        relativeLayout = (RelativeLayout) itemView.findViewById(R.id.container);
    }

    public void bindView(final TreeItem itemData, int position) {
        name.setText(itemData.name);
        title.setText(itemData.number);
        select.setSelected(itemData.isSelect);
        ImageHandler.getAvater((Activity) relativeLayout.getContext(), avatar, itemData.imgUrl);
        relativeLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        select.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                itemData.isSelect = !itemData.isSelect;
                select.setSelected(itemData.isSelect);
            }
        });
    }
}
