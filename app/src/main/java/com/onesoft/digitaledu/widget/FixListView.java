package com.onesoft.digitaledu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Jayden on 2016/10/29.
 */

public class FixListView extends ListView {

    public boolean hasScrollBar = true;

    /**
     * @param context
     */
    public FixListView(Context context) {
        this(context, null);
    }

    public FixListView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public FixListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = heightMeasureSpec;
        if (hasScrollBar) {
            expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                    MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);// 注意这里,这里的意思是直接测量出GridView的高度
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
