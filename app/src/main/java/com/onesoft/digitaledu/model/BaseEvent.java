package com.onesoft.digitaledu.model;

/**
 * Created by Jayden on 2016/11/17.
 */

public class BaseEvent {

    public static final int WALLPAPER_UPDATE = 0;
    public static final int SEND_MESSAGE = 1;//更新发件箱列表
    public static final int UPDATE_IN_BOX = 2;//更新收件箱列表
    public static final int UPDATE_THIRD_TO_MAIN = 3;//更新动态列表页数据
    public static final int UPDATE_THIRD_MAIN_TO_LIST = 4;//更新动态列表页数据
    public static final int UPDATE_PERSON_CONTACT = 5;//更新个人通讯录
    public static final int UPDATE_AGENDA = 6;//更新日程信息
    public static final int UPDATE_AGENDA_LIST = 7;//更新日程列表
    public static final int SEND_DOCUMENT = 8;//更新公文发件箱列表
    public static final int UPDATE_INBOX_DOCUMENT = 9;//更新公文收件箱列表
    public static final int UPDATE_GROUP_CONTACT = 10;//更新群组通讯录

    public static final int UPDATE_ATTENDANCE = 11;//更新考勤日志信息
    public static final int UPDATE_ATTENDANCE_LIST = 12;//更新考勤日志列表
    public static final int TURN_TO_MESSAGE = 13;//跳转到消息中心
    public static final int TURN_TO_MINE = 14;//跳转到个人信息
    public static final int UPDATE_FILE_SELECT = 15;//更新招生工作制度添加页面的文件选择信息

    public int type;
    public String data;

    public BaseEvent(int type, String data) {
        this.type = type;
        this.data = data;
    }
}
