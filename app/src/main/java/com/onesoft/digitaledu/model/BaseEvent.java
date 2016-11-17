package com.onesoft.digitaledu.model;

/**
 * Created by Jayden on 2016/11/17.
 */

public class BaseEvent {

    public static final int WALLPAPER_UPDATE = 0;

    public int type;
    public String data;

    public BaseEvent(int type, String data) {
        this.type = type;
        this.data = data;
    }
}
