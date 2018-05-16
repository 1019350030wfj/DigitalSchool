package com.onesoft.digitaledu.view.activity.infomanager.docsendrec;

import android.content.Context;

import com.onesoft.netlibrary.model.HttpHandler;

import java.io.File;

import okhttp3.Request;

/**
 * 公文下载帮助类
 * Created by Jayden on 2017/1/3.
 */

public class DocDownloadHelper {

    public boolean isFileExist(File file) {
        return file.exists();
    }

    public void downloadFile(Context context, String url, String saveDir, String fileName, final IDownloadListener listener) {
        HttpHandler.getInstance(context).downloadAsync(context, url, saveDir, fileName, new HttpHandler.ResultCallback<Object>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(Object response) {
                if (listener != null) {
                    listener.onFinish();
                }
            }
        });
    }

    public interface IDownloadListener {
        void onFinish();
    }
}
