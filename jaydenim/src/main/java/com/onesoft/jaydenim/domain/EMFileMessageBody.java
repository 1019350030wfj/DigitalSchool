package com.onesoft.jaydenim.domain;

/**
 * Created by Jayden on 2016/9/19.
 */

public class EMFileMessageBody {
    public static enum EMDownloadStatus {
        DOWNLOADING,
        SUCCESSED,
        FAILED,
        PENDING;

        private EMDownloadStatus() {
        }
    }

    private String localUrl;
    private String remoteUrl;
    private String secret;

    public String getLocalUrl() {
        return localUrl;
    }

    public String getSecret() {
        return secret;
    }

    public String getRemoteUrl() {
        return remoteUrl;
    }
}
