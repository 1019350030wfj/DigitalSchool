package com.onesoft.digitaledu.view.fragment.infomanager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.view.activity.infomanager.attendancelog.AttendanceLogActivity;
import com.onesoft.digitaledu.view.fragment.BaseFragment;
import com.onesoft.digitaledu.widget.calendar.JaydenViewPager;
import com.onesoft.digitaledu.widget.calendar.LunarCalendar;
import com.onesoft.digitaledu.widget.calendar.custom.YearView;
import com.onesoft.digitaledu.widget.calendar.custom.YearViewClickListeners;

import java.util.Calendar;

/**
 * 考勤查询
 * Created by Jayden on 2017/01/04.
 */

public class YearFragment extends BaseFragment<BasePresenter> implements YearViewClickListeners {

    private JaydenViewPager mJaydenViewPager;
    private YearView yearView1;
    private YearView yearView2;
    private YearView yearView3;
    private YearView mYearMiddle;
    private YearView mYearLeft;
    private YearView mYearRight;

    private int mMiddleYear;
    private LunarCalendar lc;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_year;
    }

    @Override
    protected void initPresenter() {
    }

    @Override
    public void initView(View view) {
        mJaydenViewPager = (JaydenViewPager) view.findViewById(R.id.jayden_viewpager);
        yearView1 = (YearView) view.findViewById(R.id.year_view1);
        yearView2 = (YearView) view.findViewById(R.id.year_view2);
        yearView3 = (YearView) view.findViewById(R.id.year_view3);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        lc = new LunarCalendar();
        Calendar cal = Calendar.getInstance();
        mMiddleYear = cal.get(Calendar.YEAR);
        //更新Activity的标题
        updateTitle();

        mYearLeft = yearView1;
        mYearMiddle = yearView2;
        mYearRight = yearView3;

        //init
        yearView1.registerYearViewClickListener(this);
        yearView2.registerYearViewClickListener(this);
        yearView3.registerYearViewClickListener(this);

        mJaydenViewPager.setYearChangeListener(new JaydenViewPager.OnYearChangeListener() {
            @Override
            public void onYearChange(ViewGroup left, ViewGroup middle, ViewGroup right) {
                if (middle == yearView1) {
                    mMiddleYear = yearView1.getDisplayYear();
                    mYearMiddle = yearView1;
                } else if (middle == yearView2) {
                    mMiddleYear = yearView2.getDisplayYear();
                    mYearMiddle = yearView2;
                } else if (middle == yearView3) {
                    mMiddleYear = yearView3.getDisplayYear();
                    mYearMiddle = yearView3;
                }

                //更新Activity的标题
                updateTitle();

                if (left == yearView1) {
                    yearView1.updateYearCalendar(mMiddleYear - 1);
                    mYearLeft = yearView1;
                } else if (left == yearView2) {
                    yearView2.updateYearCalendar(mMiddleYear - 1);
                    mYearLeft = yearView2;
                } else if (left == yearView3) {
                    yearView3.updateYearCalendar(mMiddleYear - 1);
                    mYearLeft = yearView3;
                }

                if (right == yearView1) {
                    yearView1.updateYearCalendar(mMiddleYear + 1);
                    mYearRight = yearView1;
                } else if (right == yearView2) {
                    yearView2.updateYearCalendar(mMiddleYear + 1);
                    mYearRight = yearView2;
                } else if (right == yearView3) {
                    yearView3.updateYearCalendar(mMiddleYear + 1);
                    mYearRight = yearView3;
                }
            }
        });

        mJaydenViewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mJaydenViewPager.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        yearView2.updateYearCalendar(mMiddleYear);
                        mJaydenViewPager.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                yearView1.updateYearCalendar(mMiddleYear - 1);
                                yearView3.updateYearCalendar(mMiddleYear + 1);
                            }
                        }, 1350);
                    }
                }, 300);

                mJaydenViewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        mPageStateLayout.onSucceed();
    }

    private void updateTitle() {
        ((AttendanceLogActivity) getActivity()).updateTitle(mMiddleYear + "年",
                lc.cyclical(mMiddleYear) + "(" + lc.animalsYear(mMiddleYear) + ")" + "年");
    }

    @Override
    public void dateClicked(int year, int month, int day) {
        ((AttendanceLogActivity) getActivity()).switchToMonth(year, month, day);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            updateTitle();
        }
    }

    public void refreshToday() {
        Calendar cal = Calendar.getInstance();
        mMiddleYear = cal.get(Calendar.YEAR);
        //更新Activity的标题
        updateTitle();
        mYearLeft.updateYearCalendar(mMiddleYear - 1);
        mYearMiddle.updateYearCalendar(mMiddleYear);
        mYearRight.updateYearCalendar(mMiddleYear + 1);
    }

}
