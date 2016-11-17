package com.onesoft.digitaledu.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by Jayden on 2016/11/17.
 */

public class Utils {

    public static int getDisplayWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        return  wm.getDefaultDisplay().getWidth();// 宽度
    }

    public static int getDisplayHeigth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return  wm.getDefaultDisplay().getHeight();// 高度
//		Dimension dimen = new Dimension();
//		Display disp = wm.getDefaultDisplay();
//		Point outP = new Point();
//		disp.getRealSize(outP);
//		dimen.mWidth = outP.x ;
//		dimen.mHeight = outP.y;
//		return outP.y;
    }
}
