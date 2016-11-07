package com.onesoft.digitaledu.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.onesoft.digitaledu.R;

/**
 * Created by Jayden on 2016/10/28.
 */

public class CircleIndicator  extends LinearLayout {

    private final static int DEFAULT_INDICATOR_WIDTH = 5;
    private ViewPager mViewpager;
    private int mIndicatorMargin = -1;
    private int mIndicatorWidth = -1;
    private int mIndicatorHeight = -1;
    private int mAnimatorResId = R.animator.scale_with_alpha;
    private int mIndicatorBackgroundResId = R.drawable.shape_dot_white;
    private int mIndicatorUnselectedBackgroundResId = R.drawable.shape_dot_white;

    private int mLastPosition = -1;

    public CircleIndicator(Context context) {
        super(context);
        init(context, null);
    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
        handleTypedArray(context, attrs);
        checkIndicatorConfig(context);
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.CircleIndicator);
        mIndicatorWidth = typedArray.getDimensionPixelSize(
                R.styleable.CircleIndicator_ci_width, -1);
        mIndicatorHeight = typedArray.getDimensionPixelSize(
                R.styleable.CircleIndicator_ci_height, -1);
        mIndicatorMargin = typedArray.getDimensionPixelSize(
                R.styleable.CircleIndicator_ci_margin, -1);

        mAnimatorResId = typedArray.getResourceId(
                R.styleable.CircleIndicator_ci_animator,
                R.animator.scale_with_alpha);
        mIndicatorBackgroundResId = typedArray.getResourceId(
                R.styleable.CircleIndicator_ci_drawable,
                R.drawable.shape_dot_white);
        mIndicatorUnselectedBackgroundResId = typedArray.getResourceId(
                R.styleable.CircleIndicator_ci_drawable_unselected,
                mIndicatorBackgroundResId);
        typedArray.recycle();
    }

    /**
     * Create and configure Indicator in Java code.
     */
    public void configureIndicator(int indicatorWidth, int indicatorHeight,
                                   int indicatorMargin) {
        configureIndicator(indicatorWidth, indicatorHeight, indicatorMargin,
                R.animator.scale_with_alpha, 0, R.drawable.shape_dot_white,
                R.drawable.shape_dot_white);
    }

    public void configureIndicator(int indicatorWidth, int indicatorHeight,
                                   int indicatorMargin, int animatorId, int animatorReverseId,
                                   int indicatorBackgroundId, int indicatorUnselectedBackgroundId) {

        mIndicatorWidth = indicatorWidth;
        mIndicatorHeight = indicatorHeight;
        mIndicatorMargin = indicatorMargin;

        mAnimatorResId = animatorId;
        mIndicatorBackgroundResId = indicatorBackgroundId;
        mIndicatorUnselectedBackgroundResId = indicatorUnselectedBackgroundId;

        checkIndicatorConfig(getContext());
    }

    private void checkIndicatorConfig(Context context) {
        mIndicatorWidth = (mIndicatorWidth < 0) ? dip2px(DEFAULT_INDICATOR_WIDTH)
                : mIndicatorWidth;
        mIndicatorHeight = (mIndicatorHeight < 0) ? dip2px(DEFAULT_INDICATOR_WIDTH)
                : mIndicatorHeight;
        mIndicatorMargin = (mIndicatorMargin < 0) ? dip2px(DEFAULT_INDICATOR_WIDTH)
                : mIndicatorMargin;

        mAnimatorResId = (mAnimatorResId == 0) ? R.animator.scale_with_alpha
                : mAnimatorResId;
        mIndicatorBackgroundResId = (mIndicatorBackgroundResId == 0) ? R.drawable.shape_dot_white
                : mIndicatorBackgroundResId;
        mIndicatorUnselectedBackgroundResId = (mIndicatorUnselectedBackgroundResId == 0) ? mIndicatorBackgroundResId
                : mIndicatorUnselectedBackgroundResId;
    }


    public void setCount(int count, int defaultItem) {
        // 添加小园点
        removeAllViews();
        if (count <= 0) {
            return;
        }
        int currentItem = defaultItem;
        for (int i = 0; i < count; i++) {
            if (currentItem == i) {
                addIndicator(mIndicatorBackgroundResId);
            } else {
                addIndicator(mIndicatorUnselectedBackgroundResId);
            }
        }
    }

    public void chooseItem(int position) {
        if (mLastPosition >= 0) {
            View currentIndicator = getChildAt(mLastPosition);
            currentIndicator
                    .setBackgroundResource(mIndicatorUnselectedBackgroundResId);
        }

        View selectedIndicator = getChildAt(position);
        selectedIndicator.setBackgroundResource(mIndicatorBackgroundResId);
        mLastPosition = position;
    }


    public void setViewPager(ViewPager viewPager) {
        mViewpager = viewPager;
        if (mViewpager != null && mViewpager.getAdapter() != null) {
            createIndicators();
            mViewpager.setOnPageChangeListener(mInternalPageChangeListener);
            mViewpager.getAdapter().registerDataSetObserver(
                    mInternalDataSetObserver);
            mInternalPageChangeListener.onPageSelected(mViewpager
                    .getCurrentItem());
        }
    }

    private final ViewPager.OnPageChangeListener mInternalPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {

            if (mViewpager.getAdapter() == null
                    || mViewpager.getAdapter().getCount() <= 0) {
                return;
            }

            if (mLastPosition >= 0) {
                View currentIndicator = getChildAt(mLastPosition);
                currentIndicator
                        .setBackgroundResource(mIndicatorUnselectedBackgroundResId);
            }

            View selectedIndicator = getChildAt(position);
            selectedIndicator.setBackgroundResource(mIndicatorBackgroundResId);
            mLastPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private DataSetObserver mInternalDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();

            int newCount = mViewpager.getAdapter().getCount();
            int currentCount = getChildCount();

            if (newCount == currentCount) { // No change
                return;
            } else if (mLastPosition < newCount) {
                mLastPosition = mViewpager.getCurrentItem();
            } else {
                mLastPosition = -1;
            }

            createIndicators();
        }
    };

    public void addOnPageChangeListener(
            ViewPager.OnPageChangeListener onPageChangeListener) {
        if (mViewpager == null) {
            throw new NullPointerException(
                    "can not find Viewpager , setViewPager first");
        }
        mViewpager.addOnPageChangeListener(onPageChangeListener);
    }

    private void createIndicators() {
        removeAllViews();
        int count = mViewpager.getAdapter().getCount();
        if (count <= 0) {
            return;
        }
        int currentItem = mViewpager.getCurrentItem();

        for (int i = 0; i < count; i++) {
            if (currentItem == i) {
                addIndicator(mIndicatorBackgroundResId);
            } else {
                addIndicator(mIndicatorUnselectedBackgroundResId);
            }
        }
    }

    private void addIndicator(int backgroundDrawableId) {
        View Indicator = new View(getContext());
        Indicator.setBackgroundResource(backgroundDrawableId);
        addView(Indicator, mIndicatorWidth, mIndicatorHeight);
        LayoutParams lp = (LayoutParams) Indicator.getLayoutParams();
        lp.leftMargin = mIndicatorMargin;
        lp.rightMargin = mIndicatorMargin;
        Indicator.setLayoutParams(lp);
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
