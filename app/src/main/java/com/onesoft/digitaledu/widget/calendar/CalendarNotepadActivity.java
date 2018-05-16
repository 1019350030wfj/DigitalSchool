package com.onesoft.digitaledu.widget.calendar;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.widget.calendar.view.MonthFragment;
import com.onesoft.digitaledu.widget.calendar.view.YearFragment;

/**
 * 记事日历
 * Created by Jayden on 2016/11/9.
 */
public class CalendarNotepadActivity extends ToolBarActivity<BasePresenter> {

    private View mRlTitle;
    private TextView mTxtYear;
    private TextView mTxtTitleUp;
    private TextView mTxtTitleDown;
    private TextView mTvToday;
    private MonthFragment mMonthFragment;
    private YearFragment mYearFragment;

    private boolean isSelectYear;//弹框选择年

    @Override
    protected void initPresenter() {
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_calendar_notepad, null);
    }

    @Override
    public void initView() {
        mTxtYear = (TextView) findViewById(R.id.txt_year);
        mTxtTitleUp = (TextView) findViewById(R.id.txt_title_up);
        mTxtTitleDown = (TextView) findViewById(R.id.txt_title_down);
        mTvToday = (TextView) findViewById(R.id.txt_today);
        mRlTitle = findViewById(R.id.rl_title);
    }

    @Override
    public void initListener() {
        mTvToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelectYear) {
                    mYearFragment.refreshToday();
                } else {
                    mMonthFragment.initData(null);
                }
            }
        });
        mTxtYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出PopupWindow
                LinearLayout container = (LinearLayout) LayoutInflater.from(CalendarNotepadActivity.this).
                        inflate(R.layout.layout_calendar_popup, null);
                final TextView txtYear = (TextView) container.findViewById(R.id.txt_year);
                final TextView txtMonth = (TextView) container.findViewById(R.id.txt_month);
                if (isSelectYear) {
                    txtYear.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_top_left_right));
                    txtMonth.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_bottom_left_right_black));
                } else {
                    txtMonth.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_bottom_left_right));
                    txtYear.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_top_left_right_black));
                }
                final PopupWindow popupWindow = new PopupWindow(container,
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                // 控制popupwindow点击屏幕其他地方消失
                popupWindow.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.bg_calendar_popup));// 设置背景图片，不能在布局中设置，要通过代码来设置
                popupWindow.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功，如上
                popupWindow.showAsDropDown(mTxtYear, 10, 0);
                txtYear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isSelectYear = true;
                        txtYear.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_top_left_right));
                        txtMonth.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_bottom_left_right_black));
                        mTxtYear.setText(getResources().getString(R.string.year));
                        showYearFragment();
                        popupWindow.dismiss();
                    }
                });
                txtMonth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isSelectYear = false;
                        txtMonth.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_bottom_left_right));
                        txtYear.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_top_left_right_black));
                        mTxtYear.setText(getResources().getString(R.string.month));
                        showMonthFragment();
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public void initData() {
        setTitle(getString(R.string.calendar_notepad));
        showMonthFragment();
        mPageStateLayout.onSucceed();
    }

    private void showMonthFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit);
        if (mYearFragment != null) {
            ft.hide(mYearFragment);
        }
        if (mMonthFragment == null) {
            mMonthFragment = new MonthFragment();
            ft.add(R.id.container, mMonthFragment, "mMonthFragment");
        } else {
            ft.show(mMonthFragment);
        }
        ft.commit();
    }

    private void showYearFragment() {
        FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit);
        if (mMonthFragment != null) {
            ft.hide(mMonthFragment);
        }
        if (mYearFragment == null) {
            mYearFragment = new YearFragment();
            ft.add(R.id.container, mYearFragment, "mYearFragment");
        } else {
            ft.show(mYearFragment);
        }
        ft.commit();
    }

    public void updateTitle(String title, String titleDown) {
        mTxtTitleUp.setText(title);
        mTxtTitleDown.setText(titleDown);
    }

    public void switchToMonth(int year, int month, int day) {
        isSelectYear = false;
        mTxtYear.setText(getResources().getString(R.string.month));
        showMonthFragment();
        updateTitleBg(String.valueOf(month+1));
        mMonthFragment.refreshData(year, month + 1, day);
    }

    public void updateTitleBg(String month) {
        switch (month){
            case "11":
            case "12":
            case "1":{//冬季
                mRlTitle.setBackgroundDrawable(getResources().getDrawable(R.drawable.winter));
                break;
            }
            case "2":
            case "3":
            case "4":{
                mRlTitle.setBackgroundDrawable(getResources().getDrawable(R.drawable.spring));
                break;
            }
            case "5":
            case "6":
            case "7":{
                mRlTitle.setBackgroundDrawable(getResources().getDrawable(R.drawable.summer));
                break;
            }
            case "8":
            case "9":
            case "10":{//秋季
                mRlTitle.setBackgroundDrawable(getResources().getDrawable(R.drawable.autumn));
            }
        }
    }
}
