package com.onesoft.digitaledu.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

import com.onesoft.digitaledu.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Jayden on 2016/11/17.
 */

public class ViewUtil {

    public  static final int REQUEST_ALBUM = 0x333;

    public static void setBackGround(View view, int pos) {
        int[] mDrawableArray = new int[]{R.drawable.w001, R.drawable.w002, R.drawable.w003, R.drawable.w004, R.drawable.w005,
                R.drawable.w006, R.drawable.w007, R.drawable.w008, R.drawable.w009, R.drawable.w010,
                R.drawable.w011, R.drawable.w012, R.drawable.w013};
        Bitmap bitmap = null;
        switch (pos) {
            case REQUEST_ALBUM://相册
            case 0: {//拍照
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(SPHelper.getWallPaperPath(view.getContext())); // 根据路径获取数据
                    bitmap = BitmapFactory.decodeStream(fis);
                    if (view instanceof ImageView) {
                        ((ImageView) view).setImageBitmap(bitmap);// 显示图片
                    } else {
                        view.setBackgroundDrawable(new BitmapDrawable(bitmap));
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (fis != null){
                            fis.close();// 关闭流
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case 1:{//默认背景
                view.setBackgroundColor(Color.parseColor("#f2f2f2"));
                break;
            }
            default:{
                view.setBackgroundResource(mDrawableArray[pos - 2]);
            }
        }

    }
}
