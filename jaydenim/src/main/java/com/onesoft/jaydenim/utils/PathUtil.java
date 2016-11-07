package com.onesoft.jaydenim.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by Jayden on 2016/9/19.
 */

public class PathUtil {

    public static String pathPrefix;
    public static final String HISTORYPATHNAME = "/chat/";
    public static final String IMAGEPATHNAME = "/image/";
    public static final String VOICEPATHNAME = "/voice/";
    public static final String FILEPATHNAME = "/file/";
    public static final String VIDEOPATHNAME = "/video/";
    public static final String NETDISKPATHNAME = "/netdisk/";
    public static final String MEETINGPATHNAME = "/meeting/";
    private static File storageDir = null;
    private static PathUtil instance = null;
    private String voicePath = null;
    private String imagePath = null;
    private String historyPath = null;
    private String videoPath = null;
    private String filePath;

    private PathUtil() {
    }

    public static PathUtil getInstance() {
        if (instance == null) {
            instance = new PathUtil();
        }

        return instance;
    }

    /**
     * 创建缓存目录路径
     */
    public static String getCacheDir(Context context) {
        if (TextUtils.isEmpty(pathPrefix) && android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            try {
                File f = context.getExternalCacheDir();//内部存储卡
                if (f != null && f.getParentFile() != null) {
                    if (f.getParentFile().exists()) {
                        pathPrefix = f.getParentFile().getAbsolutePath() + File.separator + "im/";
                    }
                }
            } catch (NullPointerException e) {
            } finally {
                if (TextUtils.isEmpty(pathPrefix)) {
                    pathPrefix = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "im/";
                }
            }
        }
        createDir(pathPrefix);
        checkDir(pathPrefix);
        return pathPrefix;
    }

    private static void createDir(String dir) {
        if (TextUtils.isEmpty(dir)) {
            LogUtil.d("dir is null");
            return;
        }
        File file = new File(dir);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
    }


    public static void checkDir(String dir) {
        if (TextUtils.isEmpty(dir)) {
            LogUtil.d("dir is null");
            return;
        }
        File file = new File(dir);
        if (file.exists() && file.isDirectory()) {
            LogUtil.d(dir + " is Directory");
        } else {
            if (!file.exists()) {
                LogUtil.d(dir + "文件不存在");
            }
            if (!file.isDirectory()) {
                LogUtil.d(dir + "文件不是目录");
            }

        }
    }

    public void initDirs(String key, String username, Context context) {
        getCacheDir(context);
        generateVoicePath(key, username, context);

        generateImagePath(key, username, context);

        generateHistoryPath(key, username, context);

        generateVideoPath(key, username, context);

        generateFiePath(key, username, context);

    }

    public String getImagePath() {
        return this.imagePath;
    }

    public String getVoicePath() {
        return this.voicePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public String getVideoPath() {
        return this.videoPath;
    }

    public String getHistoryPath() {
        return this.historyPath;
    }

    private void generateImagePath(String var0, String var1, Context var2) {
        if (var0 == null) {
            imagePath = pathPrefix + var1 + IMAGEPATHNAME;
        } else {
            imagePath = pathPrefix + var0 + File.separator + var1 + IMAGEPATHNAME;
        }
        createDir(imagePath);
        checkDir(imagePath);
    }

    private void generateVoicePath(String var0, String var1, Context var2) {
        if (var0 == null) {
            voicePath = pathPrefix + var1 + VOICEPATHNAME;
        } else {
            voicePath = pathPrefix + var0 + File.separator + var1 + VOICEPATHNAME;
        }
        createDir(voicePath);
        checkDir(voicePath);
    }

    private void generateFiePath(String var0, String var1, Context var2) {
        if (var0 == null) {
            filePath = pathPrefix + var1 + FILEPATHNAME;
        } else {
            filePath = pathPrefix + var0 + File.separator + var1 + FILEPATHNAME;
        }
        createDir(filePath);
        checkDir(filePath);
    }

    private void generateVideoPath(String var0, String var1, Context var2) {
        if (var0 == null) {
            videoPath = pathPrefix + var1 + VIDEOPATHNAME;
        } else {
            videoPath = pathPrefix + var0 + File.separator + var1 + VIDEOPATHNAME;
        }
        createDir(videoPath);
        checkDir(videoPath);
    }

    private void generateHistoryPath(String var0, String var1, Context var2) {
        if (var0 == null) {
            historyPath = pathPrefix + var1 + HISTORYPATHNAME;
        } else {
            historyPath = pathPrefix + var0 + File.separator + var1 + HISTORYPATHNAME;
        }
        createDir(historyPath);
        checkDir(historyPath);
    }
}
