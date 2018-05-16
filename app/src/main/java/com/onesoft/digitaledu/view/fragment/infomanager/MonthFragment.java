package com.onesoft.digitaledu.view.fragment.infomanager;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.Agenda;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.presenter.infomanager.attendancelog.MonthPresenter;
import com.onesoft.digitaledu.utils.TimeUtil;
import com.onesoft.digitaledu.view.activity.infomanager.attendancelog.AttendanceLogActivity;
import com.onesoft.digitaledu.view.activity.infomanager.attendancelog.AttendanceLogDetailActivity;
import com.onesoft.digitaledu.view.fragment.BaseFragment;
import com.onesoft.digitaledu.widget.calendar.CalendarConstant;
import com.onesoft.digitaledu.widget.calendar.LunarCalendar;
import com.onesoft.digitaledu.widget.calendar.ScrollableLayout;
import com.onesoft.digitaledu.widget.calendar.SpecialCalendar;
import com.onesoft.digitaledu.widget.calendar.adapter.AgendaAdapter;
import com.onesoft.digitaledu.widget.calendar.adapter.CalendarAdapter;
import com.onesoft.digitaledu.widget.calendar.presenter.IMonthView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 考勤查询
 * Created by Jayden on 2017/01/04.
 */

public class MonthFragment extends BaseFragment<MonthPresenter> implements GestureDetector.OnGestureListener, IMonthView {

    private GestureDetector gestureDetector = null;
    private CalendarAdapter calV = null;
    private GridView gridView = null;
    private ScrollableLayout mScrollLayout;
    private RelativeLayout mTopLayout;
    private ListView mListView;
    private AgendaAdapter mAdapter;
    private TextView mTxtNoAgenda;

    private static int jumpMonth = 0;      //每次滑动，增加或减去一个月,默认为0（即显示当前月）
    private static int jumpYear = 0;       //滑动跨越一年，则增加或者减去一年,默认为0(即当前年)
    private int year_c = 0;
    private int month_c = 0;
    private int day_c = 0;
    private String currentDate = "";
    private SpecialCalendar sc = null;
    private LunarCalendar lc = null;

    private float location;             // 最终决定的收缩比例值
    private float currentLoction = 1f;  // 记录当天的收缩比例值
    private float selectLoction = 1f;   // 记录选择那一天的收缩比例值

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_month;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MonthPresenter(getActivity(), this);
    }

    @Override
    public void initView(View view) {
        gridView = (GridView) view.findViewById(R.id.gridview);
        mTxtNoAgenda = (TextView) view.findViewById(R.id.txt_no_agenda);
        mListView = (ListView) view.findViewById(R.id.main_lv);
        mTopLayout = (RelativeLayout) view.findViewById(R.id.rl_head);
        mScrollLayout = (ScrollableLayout) view.findViewById(R.id.scrollableLayout);
        view.findViewById(R.id.btn_add).setVisibility(View.INVISIBLE);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AttendanceLogDetailActivity.startDetail(getActivity(), mAdapter.getItem(position));
            }
        });
        EventBus.getDefault().register(this);
    }

    private TranslateAnimation mAnimationLeft;
    private TranslateAnimation mAnimationRight;
    private TranslateAnimation mAnimationCurrent;

    private void initAnima() {
        mAnimationLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        mAnimationRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        mAnimationLeft.setDuration(500);
        mAnimationRight.setDuration(500);
        mAnimationCurrent = mAnimationLeft;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        currentDate = sdf.format(date);  //当期日期
        sc = new SpecialCalendar();
        lc = new LunarCalendar();

        mScrollLayout.setOnScrollListener(new ScrollableLayout.OnScrollListener() {
            @Override
            public void onScroll(int currentY, int maxY) {
                mTopLayout.setTranslationY(currentY * location);
            }
        });

        mScrollLayout.getHelper().setCurrentContainer(mListView);
        gestureDetector = new GestureDetector(this);
        refreshData(Integer.parseInt(currentDate.split("-")[0]), Integer.parseInt(currentDate.split("-")[1]),
                Integer.parseInt(currentDate.split("-")[2]));
    }

    public void refreshData(int year, int month, int day) {
        year_c = year;
        month_c = month;
        day_c = day;
        initAnima();
        CalendarConstant.zYear = String.valueOf(year);
        CalendarConstant.zMonth = String.valueOf(month);
        CalendarConstant.zDay = String.valueOf(day);
        jumpMonth = 0;
        jumpYear = 0;
        mAdapter = new AgendaAdapter(getActivity());
        mListView.setAdapter(mAdapter);

        // TODO 计算当天的位置和收缩比例
        SpecialCalendar calendar = new SpecialCalendar();
        boolean isLeapYear = calendar.isLeapYear(year);
        int days = calendar.getDaysOfMonth(isLeapYear, month);
        int dayOfWeek = calendar.getWeekdayOfMonth(year, month);
        int todayPosition = day;
        if (dayOfWeek != 7) {
            days = days + dayOfWeek;
            todayPosition += dayOfWeek - 1;
        } else {
            todayPosition -= 1;
        }
        /**
         * 如果 少于或者等于35天显示五行 多余35天显示六行
         * 五行: 收缩比例是：0.25，0.5，0.75，1
         * 六行: 收缩比例是：0.2，0.4，0.6，0.8，1
         */

        if (days <= 35) {
            CalendarConstant.scale = 0.25f;
            currentLoction = (4 - todayPosition / 7) * CalendarConstant.scale;
        } else {
            CalendarConstant.scale = 0.2f;
            currentLoction = (5 - todayPosition / 7) * CalendarConstant.scale;
        }
        selectLoction = currentLoction;
        location = currentLoction;
        getDataByYM();
        getDataByYMD();
        mPageStateLayout.onSucceed();
    }

    private void getDataByYM() {//用年和月去获取数据
        mPresenter.getYearMonth(CalendarConstant.zYear, CalendarConstant.zMonth);
    }

    private void getDataByYMD() {//用过年、月、日去获取数据
        mPresenter.getYearMonthDay(CalendarConstant.zYear, CalendarConstant.zMonth, CalendarConstant.zDay);
    }

    //添加gridview
    private void addGridView() {
        // TODO 如果滑动到其他月默认定位到第一行，划回本月定位到当天那行
        location = currentLoction;
        // TODO 选择的月份 定位到选择的那天
        if (((jumpMonth + month_c) + "").equals(CalendarConstant.zMonth)) {
            location = selectLoction;
        }

        gridView.setOnTouchListener(new View.OnTouchListener() {
            //将gridview中的触摸事件回传给gestureDetector
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //gridView中的每一个item的点击事件
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                //点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
                int startPosition = calV.getStartPositon();
                int endPosition = calV.getEndPosition();
                String scheduleDay, scheduleYear, scheduleMonth;
                location = (float) ((5 - position / 7) * 0.2);
                scheduleDay = calV.getDateByClickItem(position).split("\\.")[0];  //这一天的阳历
                scheduleYear = calV.getShowYear();
                scheduleMonth = calV.getShowMonth();
                CalendarConstant.zYear = scheduleYear;
                CalendarConstant.zMonth = scheduleMonth;
                CalendarConstant.zDay = scheduleDay;
                if (startPosition <= position + 7 && position <= endPosition - 7) {//点击的是这一个月的日期
                    if (calV.hasAgenda(scheduleDay)) {//有日程，才去加载
                        getDataByYMD();
                    } else {
                        onGetDayDataError("无考勤");
                    }

                    if (CalendarConstant.scale == 0.2f) {
                        location = (5 - position / 7) * CalendarConstant.scale;
                    } else {
                        location = (4 - position / 7) * CalendarConstant.scale;
                    }
                    selectLoction = location;
                    calV.setAnimation(null);
                    addTextToTopTextView();
                    calV.notifyDataSetChanged();
                } else if (position < startPosition) {//点击的是前一个月的日期
                    jumpMonth--;     //上一个月
                    mAnimationCurrent = mAnimationRight;
                    upDateView();
                } else if (position > endPosition - 7) {//点击的是后一个月的日期
                    jumpMonth++;     //下一个月
                    mAnimationCurrent = mAnimationLeft;
                    upDateView();
                }
            }

        });
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() > 120) {
            //像左滑动
            jumpMonth++;     //下一个月
            mAnimationCurrent = mAnimationLeft;
            upDateView();
            return true;
        } else if (e1.getX() - e2.getX() < -120) {
            //向右滑动
            jumpMonth--;     //上一个月
            mAnimationCurrent = mAnimationRight;
            upDateView();
            return true;
        }
        return false;
    }

    private void getCurrentYm() {
        int stepYear = year_c + jumpYear;
        int stepMonth = month_c + jumpMonth;
        if (stepMonth > 0) {//往下一个月滑动
            if (stepMonth % 12 == 0) {
                stepYear = year_c + stepMonth / 12 - 1;
                stepMonth = 12;
            } else {
                stepYear = year_c + stepMonth / 12;
                stepMonth = stepMonth % 12;
            }
        } else {//往上一个月滑动
            stepYear = year_c - 1 + stepMonth / 12;
            stepMonth = stepMonth % 12 + 12;
            if (stepMonth % 12 == 0) {
            }
        }

        CalendarConstant.zYear = String.valueOf(stepYear);
        CalendarConstant.zMonth = String.valueOf(stepMonth);

        // TODO 计算当天的位置和收缩比例
        SpecialCalendar calendar = new SpecialCalendar();
        boolean isLeapYear = calendar.isLeapYear(stepYear);
        int days = calendar.getDaysOfMonth(isLeapYear, stepMonth);
        if (days < Integer.parseInt(CalendarConstant.zDay)) {
            CalendarConstant.zDay = String.valueOf(days);
        }
        int dayOfWeek = calendar.getWeekdayOfMonth(stepYear, stepMonth);
        int todayPosition = Integer.parseInt(CalendarConstant.zDay);
        if (dayOfWeek != 7) {
            days = days + dayOfWeek;
            todayPosition += dayOfWeek - 1;
        } else {
            todayPosition -= 1;
        }

        /**
         * 如果 少于或者等于35天显示五行 多余35天显示六行
         * 五行: 收缩比例是：0.25，0.5，0.75，1
         * 六行: 收缩比例是：0.2，0.4，0.6，0.8，1
         */

        if (days <= 35) {
            CalendarConstant.scale = 0.25f;
            currentLoction = (4 - todayPosition / 7) * CalendarConstant.scale;
        } else {
            CalendarConstant.scale = 0.2f;
            currentLoction = (5 - todayPosition / 7) * CalendarConstant.scale;
        }

        selectLoction = currentLoction;
    }

    private void upDateView() {
        getCurrentYm();
        getDataByYM();
        getDataByYMD();
    }

    @Override
    public void onGetYearMonthDataSuccess(final List<Agenda> agendas) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                calV = new CalendarAdapter(getActivity(), getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
                calV.setAnimation(mAnimationCurrent);
                calV.setAgendas(agendas);
                addGridView();
                gridView.setAdapter(calV);
                addTextToTopTextView();
            }
        });
    }

    @Override
    public void onGetYearMonthDataError(String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                calV = new CalendarAdapter(getActivity(), getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
                calV.setAnimation(mAnimationCurrent);
                calV.setAgendas(null);
                addGridView();
                gridView.setAdapter(calV);
                addTextToTopTextView();
            }
        });

    }

    @Override
    public void onGetDayDataSuccess(final List<Agenda> agendas) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTxtNoAgenda.setVisibility(View.GONE);
                mListView.setVisibility(View.VISIBLE);
                mAdapter.setDatas(agendas);
                mScrollLayout.requestScrollableLayoutDisallowInterceptTouchEvent(false);
            }
        });
    }

    @Override
    public void onGetDayDataError(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTxtNoAgenda.setVisibility(View.VISIBLE);
                mTxtNoAgenda.setText(msg);
                mListView.setVisibility(View.GONE);
                mScrollLayout.requestScrollableLayoutDisallowInterceptTouchEvent(true);
            }
        });

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            addTextToTopTextView();
        }
    }

    @Override
    public void onDeleteSuccess() {//删除成功，更新列表
        getDataByYMD();
    }

    //添加头部的年份 闰哪月等信息
    public void addTextToTopTextView() {
        if (calV != null) {
            StringBuffer textDate = new StringBuffer();
            textDate.append(calV.getShowYear()).append("年").append(
                    calV.getShowMonth()).append("月").append(CalendarConstant.zDay).append("日").append("\t").append("第")
                    .append(TimeUtil.getWeekOfYear(calV.getShowYear(),
                            calV.getShowMonth(), CalendarConstant.zDay)).append("周");

            StringBuffer titleDown = new StringBuffer();
            titleDown.append(calV.getCyclical()).append("(").append(calV.getAnimalsYear()).append(")年").append("\t");
            titleDown.append(lc.getLunarDate(Integer.parseInt(CalendarConstant.zYear),
                    Integer.parseInt(CalendarConstant.zMonth), Integer.parseInt(CalendarConstant.zDay)));
            ((AttendanceLogActivity) getActivity()).updateTitleBg(calV.getShowMonth());
            ((AttendanceLogActivity) getActivity()).updateTitle(textDate.toString(), titleDown.toString());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 使用事件总线监听回调
     *
     * @param event
     */
    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBaseEvent(final BaseEvent event) {
        switch (event.type) {
            case BaseEvent.UPDATE_ATTENDANCE: {//添加成功
                getDataByYM();
                getDataByYMD();//刷新列表
                break;
            }
            case BaseEvent.UPDATE_ATTENDANCE_LIST: {//编辑成功
                getDataByYMD();//刷新列表
                break;
            }
        }
    }
}
