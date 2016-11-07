package com.onesoft.jaydenim.domain;

/**
 * Created by Jayden on 2016/9/19.
 */

public class EMImageMessageBody extends EMFileMessageBody {
    public EMDownloadStatus thumbnailDownloadStatus() {
        int type = 1;
        switch (type) {
            case 1:
                return EMDownloadStatus.DOWNLOADING;
            case 2:
                return EMDownloadStatus.SUCCESSED;
            case 3:
                return EMDownloadStatus.FAILED;
            case 4:
                return EMDownloadStatus.PENDING;
            default:
                return EMDownloadStatus.SUCCESSED;
        }
    }

    private String localPath;

    public String thumbnailLocalPath() {
        return localPath;
    }
}
