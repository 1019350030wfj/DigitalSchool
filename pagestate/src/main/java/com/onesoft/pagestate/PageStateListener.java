package com.onesoft.pagestate;

/**
 * Created by Syehunter on 16/1/9.
 */
public interface PageStateListener {

    void onLoading();

    void onError();

    void onEmpty();

    void onSucceed();

    void onRequesting();
}
