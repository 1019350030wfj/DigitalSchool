package com.onesoft.digitaledu.app;

import android.app.Application;

import com.onesoft.netlibrary.utils.ImageHandler;


/**
 * Created by Jayden on 2016/10/28.
 */

public class DigitalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        ImageHandler.onTrimMemory(getApplicationContext(), level);
    }
}
