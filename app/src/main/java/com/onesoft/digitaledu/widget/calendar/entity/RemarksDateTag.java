package com.onesoft.digitaledu.widget.calendar.entity;

import java.io.Serializable;

/**
 * Created by liyang on 2016/9/22.
 * 备注日期标记（在日历上显示点）
 */
public class RemarksDateTag implements Serializable {
    private static final long serialVersionUID = 8081066318467348774L;
    /*标记id*/
    private int tagID;
    /*备注id*/
    private int remarksID;
    /*标记备注月份*/
    private int remarksMonth;
    /*标记备注年份*/
    private int remarksYear;
    /*标记备注日期*/
    private int remarksDay;

    public RemarksDateTag() {

    }

    public RemarksDateTag(int tagID, int year, int month, int day, int remarksID) {

        this.tagID = tagID;
        this.remarksMonth = month;
        this.remarksYear = year;
        this.remarksDay = day;
        this.remarksID = remarksID;
    }

    public int getTagID() {
        return tagID;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    public int getRemarksID() {
        return remarksID;
    }

    public void setRemarksID(int remarksID) {
        this.remarksID = remarksID;
    }

    public int getRemarksMonth() {
        return remarksMonth;
    }

    public void setRemarksMonth(int remarksMonth) {
        this.remarksMonth = remarksMonth;
    }

    public int getRemarksYear() {
        return remarksYear;
    }

    public void setRemarksYear(int remarksYear) {
        this.remarksYear = remarksYear;
    }

    public int getRemarksDay() {
        return remarksDay;
    }

    public void setRemarksDay(int remarksDay) {
        this.remarksDay = remarksDay;
    }
}
