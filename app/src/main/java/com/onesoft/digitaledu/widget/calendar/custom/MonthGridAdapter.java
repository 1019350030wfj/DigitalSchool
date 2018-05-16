package com.onesoft.digitaledu.widget.calendar.custom;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.onesoft.digitaledu.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MonthGridAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mInflater;

    List<String> mItemList = Collections.EMPTY_LIST;
    List<Date> mEventList = Collections.EMPTY_LIST;
    int mToday, mMonth, mYear, mDisplayMonth, mDisplayYear;

    public MonthGridAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mToday = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public MonthGridAdapter(Context context, int year, int month, int today) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mItemList = new ArrayList<>();

        Calendar calendar = new GregorianCalendar(year, month, 1);
        mItemList = getItemList(calendar);

        mToday = today;
        mMonth = mDisplayMonth = month;
        mYear = mDisplayYear = year;
    }

    /**
     * This method generates the days of the month to be displayed
     *
     * @param calendar Species for which month the days should be generated
     * @return itemsList list of days for the month to be displayed
     */
    private List<String> getItemList(Calendar calendar) {
        List<String> itemList = new ArrayList<>();
        itemList.add("日");
        itemList.add("一");
        itemList.add("二");
        itemList.add("三");
        itemList.add("四");
        itemList.add("五");
        itemList.add("六");

        int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
        int numOfDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        if (firstDayOfMonth > 0) {
            firstDayOfMonth = firstDayOfMonth - 1;
        }

        int day = 1;
        for (int i = 0; i < numOfDays + firstDayOfMonth; i++) {
            if (i >= firstDayOfMonth) {
                itemList.add("" + day);
                day++;
            } else {
                itemList.add("");
            }
        }
        return itemList;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_calendar_month, null);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        String item = mItemList.get(position);
        mHolder.tvName.setText(item);
        if (position > 7) {
            if (!item.isEmpty()) {
                if (mToday == Integer.parseInt(item) && mDisplayMonth == mMonth && mDisplayYear == mYear) {
                    //当天
                    mHolder.tvName.setBackgroundResource(R.drawable.calendar_today_bg);
                    mHolder.tvName.setTextColor(Color.WHITE);
                } else {
                    Date date = getDate(mDisplayYear, mDisplayMonth, Integer.parseInt(item));
                    if (mEventList.contains(date)) {
                        mHolder.tvName.setBackgroundResource(R.drawable.calendar_select_day_bg);
                        mHolder.tvName.setTextColor(Color.WHITE);
                    } else {
                        mHolder.tvName.setBackgroundResource(android.R.color.transparent);
                        mHolder.tvName.setTextColor(mContext.getResources().getColor(R.color.color_1A1A1A));
                    }
                }
            }
        }
        return convertView;
    }

    /**
     * This method updates the view based on the year and month passed
     *
     * @param year
     * @param month
     */
    public void updateCalendar(int year, int month) {
        mDisplayMonth = month;
        mDisplayYear = year;

        Calendar calendar = new GregorianCalendar(year, month, 1);
        mItemList = getItemList(calendar);
        notifyDataSetChanged();
    }

    /**
     * This method will highlight the event dates
     *
     * @param eventList List of dates
     */
    public void setEventList(List<Date> eventList) {
        mEventList = eventList;
        notifyDataSetChanged();
    }

    /**
     * @return mItemList - list fo days of a month
     */
    public List<String> getItemList() {
        return mItemList;
    }

    /**
     * This method takes in year, month & day integer values and generates a date object
     *
     * @param year
     * @param month
     * @param day
     * @return Date - a date object
     */
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

    class ViewHolder {

        TextView tvName;

        public ViewHolder(View view) {
            tvName = (TextView) view.findViewById(R.id.row_cm_tv_week_day_name);
        }
    }
}
