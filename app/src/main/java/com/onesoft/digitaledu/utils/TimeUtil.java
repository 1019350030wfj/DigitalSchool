package com.onesoft.digitaledu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * time util
 * <br/>
 * 时间工具类
 *
 * @author wlf(Andy)
 * @email 411086563@qq.com
 */
public class TimeUtil {

    /**
     * format seconds to HH:mm:ss String
     *
     * @param seconds seconds
     * @return String of formatted in HH:mm:ss
     */
    public static String seconds2HH_mm_ss(long seconds) {

        long h = 0;
        long m = 0;
        long s = 0;
        long temp = seconds % 3600;

        if (seconds > 3600) {
            h = seconds / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    m = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            m = seconds / 60;
            if (seconds % 60 != 0) {
                s = seconds % 60;
            }
        }

        String dh = h < 10 ? "0" + h : h + "";
        String dm = m < 10 ? "0" + m : m + "";
        String ds = s < 10 ? "0" + s : s + "";

        return dh + ":" + dm + ":" + ds;
    }

    /**
     * 获取年月日
     *
     * @param time
     * @return
     */
    public static String getYMDFromServerData(String time) {
        if (time.contains("T")) {
            return time.split("T")[0];
        } else {
            return time.split(" ")[0];
        }

    }

    /**
     * 获取小时分钟
     *
     * @param time
     * @return
     */
    public static String getHMFromServerData(String time) {
        if (time.contains("T")) {
            return time.split("T")[1];
        } else {
            return time.split(" ")[1];
        }
    }

    public static int getWeekOfYear(String year, String month, String day) {
        String today = year + "-" + (month.length() == 1 ? "0" + month : month) + "-" + (day.length() == 1 ? "0" + day : day);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();//这一句必须要设置，否则美国认为第一天是周日，而我国认为是周一，对计算当期日期是第几周会有错误
        cal.setFirstDayOfWeek(Calendar.SUNDAY); // 设置每周的第一天为星期一
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }
}
