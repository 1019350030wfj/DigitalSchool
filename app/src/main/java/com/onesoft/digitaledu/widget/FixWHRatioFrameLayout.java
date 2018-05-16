package com.onesoft.digitaledu.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.onesoft.digitaledu.R;

/**
 * Created by Jayden on 2016/12/30.
 */

public class FixWHRatioFrameLayout extends FrameLayout {

    private float wh_ratio = 1.45f;//默认宽高比

    public FixWHRatioFrameLayout(Context context) {
        super(context);
        init(context, null);
    }

    public FixWHRatioFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FixWHRatioFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FixWidthHeightRatioView);
        wh_ratio = a.getFloat(R.styleable.FixWidthHeightRatioView_whratio, wh_ratio);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (widthSize / wh_ratio + 0.5f);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
