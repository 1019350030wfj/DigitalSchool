package com.onesoft.digitaledu.widget.calendar.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onesoft.digitaledu.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ismail.khan2 on 4/22/2016.
 */
public class MonthView extends LinearLayout {

    Context mContext;
    TextView mTvMonthName;
    CustomGridView mGvMonth;

    MonthGridAdapter mMonthGridAdapter;
    int selectedYear, selectedMonth;

    MonthViewClickListeners mListener;

    public MonthView(Context context) {
        super(context);
        initLayout(context, null);
    }

    public MonthView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initLayout(context, attributeSet);
    }

    public void registerClickListener(MonthViewClickListeners listener) {
        mListener = listener;
    }

    public void unregisterClickListener() {
        mListener = null;
    }

    private void initLayout(Context context, AttributeSet attrs) {
        mContext = context;

        LayoutInflater.from(context).inflate(R.layout.month_view, this);

        mTvMonthName = (TextView) findViewById(R.id.tv_month_view_name);
        mGvMonth = (CustomGridView) findViewById(R.id.gv_month_view);

        mMonthGridAdapter = new MonthGridAdapter(context);
        mGvMonth.setAdapter(mMonthGridAdapter);
        mGvMonth.setExpanded(true);

        mGvMonth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<String> itemList = mMonthGridAdapter.getItemList();
                String item = itemList.get(position);
                if (position >= 7) {
                    if (!item.isEmpty()) {
                        int day = Integer.parseInt(item);
                        if (mListener != null) {
                            mListener.dateClicked(getDate(selectedYear, selectedMonth, day));
                        }
                    }
                }
            }
        });
    }

    public void setSelectedYear(int year) {
        selectedYear = year;
    }

    public void setSelectedMonth(int month) {
        selectedMonth = month;
    }

    public void updateCalendar(int year, int month) {
        if (mMonthGridAdapter != null) {
            mMonthGridAdapter.updateCalendar(year, month);
            mTvMonthName.setText(getMonthName(month));
            setSelectedYear(year);
            setSelectedMonth(month);
        }
    }

    public void setEventList(List<Date> eventList) {
        mMonthGridAdapter.setEventList(eventList);
    }

    private String getMonthName(int month) {
        switch (month) {
            case 0:
                return "一月";
            case 1:
                return "二月";
            case 2:
                return "三月";
            case 3:
                return "四月";
            case 4:
                return "五月";
            case 5:
                return "六月";
            case 6:
                return "七月";
            case 7:
                return "八月";
            case 8:
                return "九月";
            case 9:
                return "十月";
            case 10:
                return "十一月";
            case 11:
                return "十二月";
            default:
                return "";
        }
    }

    private Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
