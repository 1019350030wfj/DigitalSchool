package com.onesoft.digitaledu.widget.calendar.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.onesoft.digitaledu.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ismail.khan2 on 5/26/2016.
 */
public class YearView extends LinearLayout implements MonthViewClickListeners {

    Context mContext;
    MonthView jan, feb, march, april, may, june, july, aug, sept, oct, nov, dec;
    ScrollView calendarLayout;
    int mDisplayYear;

    YearViewClickListeners mYearClickListeners;

    public YearView(Context context) {
        super(context);
        init(context, null);
    }

    public YearView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;

        View view = LayoutInflater.from(context).inflate(R.layout.year_view, this);

        jan = (MonthView) view.findViewById(R.id.mv_year_view_jan);
        feb = (MonthView) view.findViewById(R.id.mv_year_view_feb);
        march = (MonthView) view.findViewById(R.id.mv_year_view_march);
        april = (MonthView) view.findViewById(R.id.mv_year_view_april);
        may = (MonthView) view.findViewById(R.id.mv_year_view_may);
        june = (MonthView) view.findViewById(R.id.mv_year_view_jun);
        july = (MonthView) view.findViewById(R.id.mv_year_view_july);
        aug = (MonthView) view.findViewById(R.id.mv_year_view_aug);
        sept = (MonthView) view.findViewById(R.id.mv_year_view_sept);
        oct = (MonthView) view.findViewById(R.id.mv_year_view_oct);
        nov = (MonthView) view.findViewById(R.id.mv_year_view_nov);
        dec = (MonthView) view.findViewById(R.id.mv_year_view_dec);
        calendarLayout = (ScrollView) view.findViewById(R.id.sv_year_view_calendar_background);

        setupClickListeners();
    }

    private void setupClickListeners() {
        jan.registerClickListener(this);
        feb.registerClickListener(this);
        march.registerClickListener(this);
        april.registerClickListener(this);
        may.registerClickListener(this);
        june.registerClickListener(this);
        july.registerClickListener(this);
        aug.registerClickListener(this);
        sept.registerClickListener(this);
        oct.registerClickListener(this);
        nov.registerClickListener(this);
        dec.registerClickListener(this);
    }

    public void registerYearViewClickListener(YearViewClickListeners listener) {
        mYearClickListeners = listener;
    }

    public void unregisterYearViewClickListener() {
        mYearClickListeners = null;
    }

    @Override
    public void dateClicked(Date dateClicked) {
        if (mYearClickListeners != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateClicked);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            mYearClickListeners.dateClicked(year, month, day);
        }
    }

    public void updateYearCalendar(int year) {
        mDisplayYear = year;
        jan.updateCalendar(year, 0);
        feb.updateCalendar(year, 1);
        march.updateCalendar(year, 2);
        april.updateCalendar(year, 3);
        may.updateCalendar(year, 4);
        june.updateCalendar(year, 5);
        july.updateCalendar(year, 6);
        aug.updateCalendar(year, 7);
        sept.updateCalendar(year, 8);
        oct.updateCalendar(year, 9);
        nov.updateCalendar(year, 10);
        dec.updateCalendar(year, 11);
    }

    public int getDisplayYear() {
        return mDisplayYear;
    }
}
