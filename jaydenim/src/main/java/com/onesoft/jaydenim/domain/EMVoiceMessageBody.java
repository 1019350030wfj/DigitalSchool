package com.onesoft.jaydenim.domain;

/**
 * Created by Jayden on 2016/9/19.
 */

public class EMVoiceMessageBody {
    public int getLength() {
        return 0;
    }

    public EMFileMessageBody.EMDownloadStatus downloadStatus() {
        return null;
    }

    private String localURl;
    public String getLocalUrl() {
        return localURl;
    }
}
