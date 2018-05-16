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

    /**
     * 用户角色
     * @param context
     * @param role
     */
    public static void setUserRole(Context context,String role) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NAME, Activity.MODE_PRIVATE);
        sharedPreferences.edit().putString("userrole", role).commit();
    }

    /**
     * 用户角色
     *
     * @param context
     * @return
     */
    public static String getUserRole(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getString("userrole", "");
    }

    /**
     * 用户类型
     * @param context
     * @param role
     */
    public static void setUserType(Context context,String role) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NAME, Activity.MODE_PRIVATE);
        sharedPreferences.edit().putString("usertype", role).commit();
    }

    /**
     * 用户类型
     *
     * @param context
     * @return
     */
    public static String getUserType(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getString("usertype", "");
    }

    /**
     * 用户id
     * @param context
     * @param role
     */
    public static void setUserId(Context context,String role) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NAME, Activity.MODE_PRIVATE);
        sharedPreferences.edit().putString("userid", role).commit();
    }

    /**
     * 用户id
     *
     * @param context
     * @return
     */
    public static String getUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getString("userid", "");
    }

    /**
     * 用户课程表下载链接
     * @param context
     * @param role
     */
    public static void setCourseTable(Context context,String role) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NAME, Activity.MODE_PRIVATE);
        sharedPreferences.edit().putString("CourseTable", role).commit();
    }

    /**
     * 用户课程表下载链接
     *
     * @param context
     * @return
     */
    public static String getCourseTable(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getString("CourseTable", "");
    }

    /**
     * 用户id
     * @param context
     * @param role
     */
    public static void setMappedId(Context context,String role) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NAME, Activity.MODE_PRIVATE);
        sharedPreferences.edit().putString("mappedid", role).commit();
    }

    /**
     * 用户id
     *
     * @param context
     * @return
     */
    public static String getMappedId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getString("mappedid", "");
    }

    /**
     * 用户名称
     * @param context
     * @param role
     */
    public static void setUserName(Context context,String role) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NAME, Activity.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", role).commit();
    }

    /**
     * 用户名称
     *
     * @param context
     * @return
     */
    public static String getUserName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getString("username", "");
    }

    /**
     * 是否登录
     * @param context
     * @param b
     */
    public static void setIsLogin(Context context, boolean b) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NAME, Activity.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("isLogin", b).commit();
    }

    /**
     * 是否登录
     * @param context
     */
    public static boolean getIsLogin(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLogin", false);
    }
}
