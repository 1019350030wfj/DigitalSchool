package com.onesoft.digitaledu.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.onesoft.netlibrary.utils.LogUtil;

import java.io.File;

/**
 * Created by Jayden on 2016/11/17.
 */

public class PathUtil {

    private static PathUtil instance = null;

    public static String pathPrefix;
    public static final String PATH_WALLPAPER = "/wallpaper/";

    private String mWallpaper = null;

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
                        pathPrefix = f.getParentFile().getAbsolutePath() + File.separator + "digitalEducation/";
                    }
                }
            } catch (NullPointerException e) {
            } finally {
                if (TextUtils.isEmpty(pathPrefix)) {
                    pathPrefix = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "digitalEducation/";
                }
            }
        }
        createDir(pathPrefix);
        checkDir(pathPrefix);
        return pathPrefix;
    }

    private static void createDir(String dir) {
        if (TextUtils.isEmpty(dir)) {
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

    public void initDir(Context context) {
        getCacheDir(context);
        generatePathWall();
    }

    private void generatePathWall() {
        mWallpaper = pathPrefix + PATH_WALLPAPER;
        createDir(mWallpaper);
        checkDir(mWallpaper);
    }

    public String getPathWallpaper() {//拍照
        return mWallpaper  + "wallpaper.jpg";
    }

}
