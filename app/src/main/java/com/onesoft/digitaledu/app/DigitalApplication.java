package com.onesoft.digitaledu.app;

import android.app.Application;
import android.os.Environment;

import com.onesoft.digitaledu.utils.PathUtil;
import com.onesoft.netlibrary.utils.ImageHandler;

import org.wlf.filedownloader.FileDownloadConfiguration;
import org.wlf.filedownloader.FileDownloader;

import java.io.File;


/**
 * Created by Jayden on 2016/10/28.
 */

public class DigitalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // init FileDownloader
        initFileDownloader();
        PathUtil.getInstance().initDir(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        // release FileDownloader
        releaseFileDownloader();
    }

    // init FileDownloader
    private void initFileDownloader() {

        // 1.create FileDownloadConfiguration.Builder
        FileDownloadConfiguration.Builder builder = new FileDownloadConfiguration.Builder(this);

        // 2.config FileDownloadConfiguration.Builder
        builder.configFileDownloadDir(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
                "FileDownloader"); // config the download path
        // builder.configFileDownloadDir("/storage/sdcard1/FileDownloader");

        // allow 3 download tasks at the same time
        builder.configDownloadTaskSize(3);

        // config retry download times when failed
        builder.configRetryDownloadTimes(5);

        // enable debug mode
        //builder.configDebugMode(true);

        // config connect timeout
        builder.configConnectTimeout(25000); // 25s

        // 3.init FileDownloader with the configuration
        FileDownloadConfiguration configuration = builder.build(); // build FileDownloadConfiguration with the builder
        FileDownloader.init(configuration);
    }

    // release FileDownloader
    private void releaseFileDownloader() {
        FileDownloader.release();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        ImageHandler.onTrimMemory(getApplicationContext(), level);
    }
}
