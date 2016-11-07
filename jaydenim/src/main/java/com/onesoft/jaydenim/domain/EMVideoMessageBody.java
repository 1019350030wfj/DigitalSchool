package com.onesoft.jaydenim.domain;

/**
 * Created by Jayden on 2016/9/19.
 */

public class EMVideoMessageBody {
    private String localThumb;
    private String localThumbnailUrl;

    public String getLocalThumb() {
        return localThumb;
    }

    public String getThumbnailUrl() {
        return localThumbnailUrl;
    }

    public int getDuration() {
        return 0;
    }

    public long getVideoFileLength() {
        return 0;
    }

    private String localUrl;
    public String getLocalUrl() {
        return localUrl;
    }

    public EMFileMessageBody.EMDownloadStatus thumbnailDownloadStatus() {
        return null;
    }

    public boolean getSecret() {
        return false;
    }

    private String remoteUrl;
    public String getRemoteUrl() {
        return remoteUrl;
    }
}
