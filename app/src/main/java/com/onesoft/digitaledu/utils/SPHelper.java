package com.onesoft.digitaledu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Jayden on 2016/11/17
 */

public class SPHelper {

    private static final String NAME = "digitaleducation";

    /**
     * 保存选中壁纸的位置
     *
     * @param context
     * @param pos
     */
    public static void saveWallPaperPosition(Context context, int pos) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putInt("wallpaper_position", pos);
        editor.commit();
    }

    /**
     * 获取选中壁纸的位置
     *
     * @param context
     * @return
     */
    public static int getWallPaperPosition(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getInt("wallpaper_position", 1);

    }

    /**
     * 设置选中壁纸的路径
     *
     * @param context
     * @return
     */
    public static void setWallPaperPath(Context context, String arIndex) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NAME, Activity.MODE_PRIVATE);
        sharedPreferences.edit().putString("wallpath", arIndex).commit();
    }

    /**
     * 获取选中壁纸的路径
     *
     * @param context
     * @return
     */
    public static String getWallPaperPath(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getString("wallpath", "");
    }
}
