package com.onesoft.digitaledu.view.fragment.contacts;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.widget.treerecyclerview.interfaces.ItemDataClickListener;
import com.onesoft.digitaledu.widget.treerecyclerview.model.TreeItem;
import com.onesoft.digitaledu.widget.treerecyclerview.viewholder.BaseViewHolder;

public class TreeContactGroup1Holder extends BaseViewHolder {

    public TextView text;
    public ImageView expand;
    public ImageView addMember;
    public ImageView message;
    public ImageView delete;
    public RelativeLayout relativeLayout;

    public TreeContactGroup1Holder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.text);
        expand = (ImageView) itemView.findViewById(R.id.expand);
        addMember = (ImageView) itemView.findViewById(R.id.img_add_member);
        message = (ImageView) itemView.findViewById(R.id.img_msg);
        delete = (ImageView) itemView.findViewById(R.id.img_del);
        relativeLayout = (RelativeLayout) itemView.findViewById(R.id.container);
    }

    public void bindView(final TreeItem itemData, final int position,
                         final ItemDataClickListener imageClickListener, final ContactGroupAdapter.ItemOperatorListener itemOperatorListener) {
        text.setText(itemData.name);
        if (itemData.isExpand) {
            expand.setRotation(90);
        } else {
            expand.setRotation(0);
        }
        relativeLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (imageClickListener != null) {
                    if (itemData.isExpand) {
                        imageClickListener.onHideChildren(itemData);
                        itemData.isExpand = false;
                        rotationExpandIcon(90, 0);
                    } else {
                        imageClickListener.onExpandChildren(itemData);
                        itemData.isExpand = true;
                        rotationExpandIcon(0, 90);
                    }
                }

            }
        });

        addMember.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemOperatorListener != null){
                    itemOperatorListener.onAddUsers(itemData);
                }
            }
        });
        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemOperatorListener != null){
                    itemOperatorListener.onDelete(itemData);
                }
            }
        });
        message.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemOperatorListener != null){
                    itemOperatorListener.onSendMessage(itemData);
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void rotationExpandIcon(float from, float to) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
            valueAnimator.setDuration(150);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    expand.setRotation((Float) valueAnimator.getAnimatedValue());
                }
            });
            valueAnimator.start();
        }
    }
}
