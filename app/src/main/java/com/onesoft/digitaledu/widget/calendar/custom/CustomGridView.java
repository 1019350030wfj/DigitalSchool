package com.onesoft.digitaledu.widget.calendar.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import com.onesoft.digitaledu.utils.Utils;
import com.yancy.gallerypick.utils.AppUtils;

/**
 * Created by ismail.khan2 on 5/26/2016.
 */
public class CustomGridView extends GridView {

    private int mHeight;
    boolean expanded = false;

    public CustomGridView(Context context) {
        super(context);
        init(context);
    }

    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomGridView(Context context, AttributeSet attrs,
                          int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mHeight = (Utils.getDisplayWidth(context) - (int) AppUtils.dipToPx(context, 30)) / 2;
    }

    public boolean isExpanded() {
        return expanded;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isExpanded()) {
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(mHeight,MeasureSpec.EXACTLY));

        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
