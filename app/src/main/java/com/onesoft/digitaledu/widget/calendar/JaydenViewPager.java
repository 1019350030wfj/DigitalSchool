package com.onesoft.digitaledu.widget.calendar;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jayden on 2016/12/27.
 */

public class JaydenViewPager extends ViewGroup {

    private ViewDragHelper dragger;

    private ViewGroup mLLLeft;
    private ViewGroup mLLMiddle;
    private ViewGroup mLLRight;
    private int mWidth;


    public JaydenViewPager(Context context) {
        this(context, null);
    }

    public JaydenViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JaydenViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);//设为false说明，自己想画或有view要画
        dragger = ViewDragHelper.create(this, 1f, new DragCallBack());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mLLLeft.measure(widthMeasureSpec, heightMeasureSpec);
        mLLMiddle.measure(widthMeasureSpec, heightMeasureSpec);
        mLLRight.measure(widthMeasureSpec, heightMeasureSpec);
        int height = mLLMiddle.getMeasuredHeight();
        setMeasuredDimension(mWidth, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mLLLeft.layout(-mWidth, 0, 0, mLLLeft.getMeasuredHeight());
        mLLMiddle.layout(0, 0, mWidth, mLLMiddle.getMeasuredHeight());
        mLLRight.layout(mWidth, 0, 2 * mWidth, mLLRight.getMeasuredHeight());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLLLeft = (ViewGroup) getChildAt(0);
        mLLMiddle = (ViewGroup) getChildAt(1);
        mLLRight = (ViewGroup) getChildAt(2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
    }

    @Override
    public void computeScroll() {
        if (dragger.continueSettling(true)) {
            invalidate();
        }
    }

    private float mLastX = 0.0f;
    private float mLastY = 0.0f;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mLastX = ev.getX();
                mLastY = ev.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float deltX = Math.abs(ev.getX() - mLastX);
                float deltY = Math.abs(ev.getY() - mLastY);
                if (deltX - deltY > 0) {
                    if (deltX > 120) {//横向滑动，拦截
                        return true;
                    }
                }
            }
        }
        return dragger.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragger.processTouchEvent(event);
        return true;
    }

    private static final int VEL_THRESHOLD = 3000;

    private class DragCallBack extends ViewDragHelper.Callback {

        @Override
        public void onViewDragStateChanged(int state) {
            switch (state) {
                case ViewDragHelper.STATE_IDLE:
                    ViewGroup old = mLLMiddle;
                    // swap their position to make childMiddle still in middle
                    if (mLLMiddle.getLeft() == -mWidth) {
                        ViewGroup temp = mLLLeft;
                        mLLLeft = mLLMiddle;
                        mLLMiddle = mLLRight;
                        mLLRight = temp;
                        mLLRight.offsetLeftAndRight(3 * mWidth);
                    } else if (mLLMiddle.getLeft() == mWidth) {
                        ViewGroup temp = mLLRight;
                        mLLRight = mLLMiddle;
                        mLLMiddle = mLLLeft;
                        mLLLeft = temp;
                        mLLLeft.offsetLeftAndRight(-3 * mWidth);
                    }

                    if (mLLMiddle != old) {
                        mYearChangeListener.onYearChange(mLLLeft, mLLMiddle, mLLRight);
                        // swap listener to current middle
                    }
                    break;
            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == mLLMiddle) { // childMiddle is scrolling
                // offset left and right children
                mLLLeft.offsetLeftAndRight(dx);
                mLLRight.offsetLeftAndRight(dx);
                ViewCompat.postInvalidateOnAnimation(JaydenViewPager.this);
            }
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mLLMiddle;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return 1;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }


        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int DISTANCE_THRESHOLD = mWidth / 3;
            int finalLeft = 0;
            if (xvel < -VEL_THRESHOLD || releasedChild.getLeft() < -DISTANCE_THRESHOLD) {
                finalLeft = -mWidth;
            }
            if (xvel > VEL_THRESHOLD || releasedChild.getLeft() > DISTANCE_THRESHOLD) {
                finalLeft = mWidth;
            }

            if (dragger.settleCapturedViewAt(finalLeft, 0)) {
                ViewCompat.postInvalidateOnAnimation(JaydenViewPager.this);
            }
        }
    }

    private OnYearChangeListener mYearChangeListener;

    public void setYearChangeListener(OnYearChangeListener yearChangeListener) {
        mYearChangeListener = yearChangeListener;
    }

    public interface OnYearChangeListener {
        void onYearChange(ViewGroup left, ViewGroup middle, ViewGroup right);
    }
}
