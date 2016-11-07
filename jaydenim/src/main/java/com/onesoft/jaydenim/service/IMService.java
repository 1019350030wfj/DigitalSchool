package com.onesoft.jaydenim.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.onesoft.jaydenim.network.NetService;
import com.onesoft.jaydenim.utils.ThreadUtils;

/**
 * Created by Jayden on 2016/9/22.
 */
public class IMService extends Service {

    private NetService client;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        client=NetService.getInstance();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        ThreadUtils.singleStartIM.execute(new Runnable() {
            @Override
            public void run() {
                client.setupConnection();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        client.closeConnection();
    }
}
