package com.onesoft.digitaledu.widget.calendar.adapter;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.Agenda;
import com.onesoft.digitaledu.widget.calendar.CalendarConstant;
import com.onesoft.digitaledu.widget.calendar.LunarCalendar;
import com.onesoft.digitaledu.widget.calendar.SpecialCalendar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 日历gridview中的每一个item显示的textview
 *
 * @author lmw
 */
public class CalendarAdapter extends BaseAdapter {
    private boolean isLeapyear = false;  //是否为闰年
    private int daysOfMonth = 0;      //某月的天数
    private int dayOfWeek = 0;        //具体某一天是星期几
    private int lastDaysOfMonth = 0;  //上一个月的总天数
    private Context context;
    private int items = 42;
    private String[] dayNumber = new String[42];  //一个gridview中的日期存入此数组中
    private SpecialCalendar sc = null;
    private LunarCalendar lc = null;

    private String currentYear = "";//当前设置的年份
    private String currentMonth = "";
    private String currentDay = "";

    private String mYear;
    private String mMonth;
    private String mDay;
    private int mJumpMonth;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
    private int currentFlag = -1;     //用于标记当天
    private int[] schDateTagFlag = null;  //存储当月所有的日程日期

    private String showYear = "";   //用于在头部显示的年份
    private String showMonth = "";  //用于在头部显示的月份
    private String animalsYear = "";
    private String leapMonth = "";   //闰哪一个月
    private String cyclical = "";   //天干地支

    //系统当前时间
    private String sysDate = "";
    private String sys_year = "";
    private String sys_month = "";
    private String sys_day = "";

    private List<Agenda> agendas;
    private TranslateAnimation mAnimation;

    public void setAgendas(List<Agenda> agendas) {
        this.agendas = agendas;
    }

    public CalendarAdapter() {
        Date date = new Date();
        sysDate = sdf.format(date);  //当期日期
        sys_year = sysDate.split("-")[0];
        sys_month = sysDate.split("-")[1];
        sys_day = sysDate.split("-")[2];

    }

    public CalendarAdapter(Context context, Resources rs, int jumpMonth, int jumpYear, int year_c, int month_c, int day_c) {
        this();
        this.context = context;
        sc = new SpecialCalendar();
        lc = new LunarCalendar();
        this.mJumpMonth = jumpMonth;

        int stepYear = year_c + jumpYear;
        int stepMonth = month_c + jumpMonth;
        if (stepMonth > 0) {
            //往下一个月滑动
            if (stepMonth % 12 == 0) {
                stepYear = year_c + stepMonth / 12 - 1;
                stepMonth = 12;
            } else {
                stepYear = year_c + stepMonth / 12;
                stepMonth = stepMonth % 12;
            }
        } else {
            //往上一个月滑动
            stepYear = year_c - 1 + stepMonth / 12;
            stepMonth = stepMonth % 12 + 12;
            if (stepMonth % 12 == 0) {

            }
        }

        currentYear = String.valueOf(stepYear);  //得到当前的年份
        currentMonth = String.valueOf(stepMonth);  //得到本月 （jumpMonth为滑动的次数，每滑动一次就增加一月或减一月）
        currentDay = String.valueOf(day_c);  //得到当前日期是哪天

        CalendarConstant.zYear = currentYear;
        CalendarConstant.zMonth = currentMonth;

        getCalendar(Integer.parseInt(currentYear), Integer.parseInt(currentMonth));

    }

    public void setAnimation(TranslateAnimation animation) {
        mAnimation = animation;
    }

    public CalendarAdapter(Context context, Resources rs, int year, int month, int day) {
        this();
        this.context = context;
        sc = new SpecialCalendar();
        lc = new LunarCalendar();
        currentYear = String.valueOf(year); //得到跳转到的年份
        currentMonth = String.valueOf(month);  //得到跳转到的月份
        currentDay = String.valueOf(day);  //得到跳转到的天

        getCalendar(Integer.parseInt(currentYear), Integer.parseInt(currentMonth));
    }

    @Override
    public int getCount() {
        return items;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.calendar_item, null);
        }
        View llDate = convertView.findViewById(R.id.ll_date);
        TextView textView = (TextView) convertView.findViewById(R.id.tvtext);
        TextView dateView = (TextView) convertView.findViewById(R.id.date_text);
        mDay = dayNumber[position].split("\\.")[0];
        String dateName = dayNumber[position].split("\\.")[1];
        textView.setText(mDay + "");//设置阳历日期
        dateView.setText(dateName);//设置阴历日期/节假日

        if (position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {//显示本月的日期
            // 当前月信息显示
            textView.setTextColor(Color.BLACK);// 当月字体设黑
            dateView.setTextColor(Color.BLACK);
            if (currentFlag == position) {//设置 当天的背景
                llDate.setBackgroundResource(R.drawable.calendar_today_bg);
                textView.setTextColor(Color.WHITE);
                dateView.setTextColor(Color.WHITE);
            } else if (CalendarConstant.zYear.equals(currentYear) && CalendarConstant.zMonth.equals(currentMonth)
                    && CalendarConstant.zDay.equals(mDay)) {// 设置 选中的背景
                llDate.setBackgroundResource(R.drawable.calendar_current_select_bg);
                textView.setTextColor(Color.BLACK);
                dateView.setTextColor(Color.BLACK);
            } else if (hasAgenda(mDay)) {//判断是否是有日程的日期
                llDate.setBackgroundResource(R.drawable.calendar_select_day_bg);
                textView.setTextColor(context.getResources().getColor(R.color.text1));
                dateView.setTextColor(context.getResources().getColor(R.color.text1));
            } else {//默认，背景白色，文字黑色
                llDate.setBackgroundResource(android.R.color.white);
            }
        } else {//不是本月的不显示
            textView.setTextColor(context.getResources().getColor(R.color.color_dbdbdb));
            dateView.setTextColor(context.getResources().getColor(R.color.color_dbdbdb));
        }
        if (mAnimation != null){
            convertView.startAnimation(mAnimation);
        }
        return convertView;
    }




    /**
     * 判断这一天是否有日程
     *
     * @param day
     * @return
     */
    public boolean hasAgenda(String day) {
        if (agendas == null || agendas.size() == 0){
            return false;
        } else {
            for (Agenda agenda : agendas){
                if (day.equals(agenda.day)){//如果天相等，说明有日程
                    return true;
                }
            }
        }
        return false;
    }

    //得到某年的某月的天数且这月的第一天是星期几

    public void getCalendar(int year, int month) {
        isLeapyear = sc.isLeapYear(year);              //是否为闰年
        daysOfMonth = sc.getDaysOfMonth(isLeapyear, month);  //某月的总天数
        dayOfWeek = sc.getWeekdayOfMonth(year, month);      //某月第一天为星期几
        lastDaysOfMonth = sc.getDaysOfMonth(isLeapyear, month - 1);  //上一个月的总天数

        int days = daysOfMonth;
        if (dayOfWeek != 7) {
            days = days + dayOfWeek;
        }
        if (days <= 35) {//5行
            items = 35;
            CalendarConstant.scale = 0.25f;
        } else {//6行
            items = 42;
            CalendarConstant.scale = 0.2f;
        }

        getweek(year, month);
    }

    //将一个月中的每一天的值添加入数组dayNuber中
    private void getweek(int year, int month) {
        int j = 1;
        int flag = 0;
        String lunarDay = "";

        //得到当前月的所有日程日期(这些日期需要标记)
        for (int i = 0; i < dayNumber.length; i++) {
            // 周一
            if (i < dayOfWeek) {  //前一个月
                int temp = lastDaysOfMonth - dayOfWeek + 1;
                lunarDay = lc.getLunarDate(year, month - 1, temp + i, false);
                dayNumber[i] = (temp + i) + "." + lunarDay;

            } else if (i < daysOfMonth + dayOfWeek) {   //本月
                String day = String.valueOf(i - dayOfWeek + 1);   //得到的日期
                lunarDay = lc.getLunarDate(year, month, i - dayOfWeek + 1, false);
                dayNumber[i] = i - dayOfWeek + 1 + "." + lunarDay;
                //对于当前月才去标记当前日期
                if (sys_year.equals(String.valueOf(year)) && sys_month.equals(String.valueOf(month)) && sys_day.equals(day)) {
                    //标记当前日期
                    currentFlag = i;
                }
                setShowYear(String.valueOf(year));
                setShowMonth(String.valueOf(month));
                setAnimalsYear(lc.animalsYear(year));
                setLeapMonth(lc.leapMonth == 0 ? "" : String.valueOf(lc.leapMonth));
                setCyclical(lc.cyclical(year));
            } else {   //下一个月
                lunarDay = lc.getLunarDate(year, month + 1, j, false);
                dayNumber[i] = j + "." + lunarDay;
                j++;
            }
        }
    }

    public void matchScheduleDate(int year, int month, int day) {

    }

    /**
     * 点击每一个item时返回item中的日期
     *
     * @param position
     * @return
     */
    public String getDateByClickItem(int position) {
        return dayNumber[position];
    }

    /**
     * 在点击gridView时，得到这个月中第一天的位置
     *
     * @return
     */
    public int getStartPositon() {
        return dayOfWeek + 7;
    }

    /**
     * 在点击gridView时，得到这个月中最后一天的位置
     *
     * @return
     */
    public int getEndPosition() {
        return (dayOfWeek + daysOfMonth + 7) - 1;
    }

    public String getShowYear() {
        return showYear;
    }

    public void setShowYear(String showYear) {
        this.showYear = showYear;
    }

    public String getShowMonth() {
        return showMonth;
    }

    public void setShowMonth(String showMonth) {
        this.showMonth = showMonth;
    }

    public String getAnimalsYear() {
        return animalsYear;
    }

    public void setAnimalsYear(String animalsYear) {
        this.animalsYear = animalsYear;
    }

    public String getLeapMonth() {
        return leapMonth;
    }

    public void setLeapMonth(String leapMonth) {
        this.leapMonth = leapMonth;
    }

    public String getCyclical() {
        return cyclical;
    }

    public void setCyclical(String cyclical) {
        this.cyclical = cyclical;
    }
}
