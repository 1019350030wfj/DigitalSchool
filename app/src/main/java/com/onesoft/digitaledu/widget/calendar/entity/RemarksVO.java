package com.onesoft.digitaledu.widget.calendar.entity;

import java.io.Serializable;

/**
 * Created by ly on 2016/9/22.
 * 备注VO类
 */
public class RemarksVO implements Serializable {
    private static final long serialVersionUID = 8081066318467348774L;
    /*备注id*/
    private int remarksID;
    /*备注类型id*/
    private int remarksTypeID;
    /*备注提醒id*/
    private int remindID;
    /*备注内容*/
    private String remarksContent;
    /*备注时间*/
    private String remarksDate;

    public RemarksVO(){

    }

    public int getRemarksID() {
        return remarksID;
    }

    public void setRemarksID(int remarksID) {
        this.remarksID = remarksID;
    }

    public int getRemarksTypeID() {
        return remarksTypeID;
    }

    public void setRemarksTypeID(int remarksTypeID) {
        this.remarksTypeID = remarksTypeID;
    }

    public int getRemindID() {
        return remindID;
    }

    public void setRemindID(int remindID) {
        this.remindID = remindID;
    }

    public String getRemarksContent() {
        return remarksContent;
    }

    public void setRemarksContent(String remarksContent) {
        this.remarksContent = remarksContent;
    }

    public String getRemarksDate() {
        return remarksDate;
    }

    public void setRemarksDate(String remarksDate) {
        this.remarksDate = remarksDate;
    }
}
