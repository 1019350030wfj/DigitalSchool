package com.onesoft.jaydenim;

/**
 * Created by Jayden on 2016/9/19.
 */

public interface EMCallBack {
    void onSuccess();

    void onError(int error, String msg);

    void onProgress(int progress, String status);
}
