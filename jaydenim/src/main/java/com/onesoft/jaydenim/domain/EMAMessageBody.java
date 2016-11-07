package com.onesoft.jaydenim.domain;

/**
 * Created by Jayden on 2016/9/19.
 */

public class EMAMessageBody {

    public static final int EMAMessageBodyType_TEXT = 0;
    public static final int EMAMessageBodyType_IMAGE = 1;
    public static final int EMAMessageBodyType_VIDEO = 2;
    public static final int EMAMessageBodyType_LOCATION = 3;
    public static final int EMAMessageBodyType_VOICE = 4;
    public static final int EMAMessageBodyType_FILE = 5;
    public static final int EMAMessageBodyType_COMMAND = 6;
    public int type = 0;

    public EMAMessageBody() {
    }

    public int type() {
        return this.type;
    }
}
