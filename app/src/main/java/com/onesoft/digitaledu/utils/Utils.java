package com.onesoft.digitaledu.utils;

import android.content.Context;
import android.view.WindowManager;

import java.util.List;

/**
 * Created by Jayden on 2016/11/17.
 */

public class Utils {

    public static int getDisplayWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        return wm.getDefaultDisplay().getWidth();// 宽度
    }

    public static int getDisplayHeigth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();// 高度
//		Dimension dimen = new Dimension();
//		Display disp = wm.getDefaultDisplay();
//		Point outP = new Point();
//		disp.getRealSize(outP);
//		dimen.mWidth = outP.x ;
//		dimen.mHeight = outP.y;
//		return outP.y;
    }

    /**
     * 文件大小转换为GB MB KB
     * @param size
     * @return
     */
    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

    /**
     * 判断List是否为null或size = 0
     *
     * @param list
     * @return
     */
    public static <T> boolean isListEmpty(List<T> list) {
        if (getListSize(list) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取List数据的size
     */
    public static <T> int getListSize(List<T> list) {
        return (list == null) ? 0 : list.size();
    }
}
