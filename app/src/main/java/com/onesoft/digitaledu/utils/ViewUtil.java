package com.onesoft.digitaledu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.netlibrary.utils.PathUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Jayden on 2016/11/17.
 */

public class ViewUtil {

    public static final int REQUEST_ALBUM = 0x333;

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
                        if (fis != null) {
                            fis.close();// 关闭流
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case 1: {//默认背景
                view.setBackgroundColor(Color.parseColor("#f2f2f2"));
                break;
            }
            default: {
                view.setBackgroundResource(mDrawableArray[pos - 2]);
            }
        }
    }

    public static void translateToShow(Context context, View view) {
        AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(context, R.anim.show_set);
        view.startAnimation(animationSet);
        view.setVisibility(View.VISIBLE);
    }

    public static void translateToHide(Context context, View view) {
        AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(context, R.anim.hide_set);
        view.startAnimation(animationSet);
        view.setVisibility(View.GONE);
    }

    /**
     * 设置textview部分文字颜色
     *
     * @param tvColor
     * @param content
     * @param key
     */
    public static void setPartOfTextViewColor(TextView tvColor, String content, String key) {
        int fstart = content.indexOf(key);
        if (fstart == -1) {//content中没有包含key值
            tvColor.setText(content);
        } else {
            int fend = fstart + key.length();
            SpannableStringBuilder style = new SpannableStringBuilder(content);
//        style.setSpan(new BackgroundColorSpan(Color.RED),bstart,bend,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new ForegroundColorSpan(tvColor.getContext().getResources().getColor(R.color.color_tab_selected)), fstart, fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            tvColor.setText(style);
        }
    }

    public static void showImageByFileType(ImageView imageView, String fileName, View view) {
        if (fileName.toLowerCase().contains("doc") || fileName.toLowerCase().contains(".docx")) {
            imageView.setBackgroundDrawable(imageView.getContext().getResources().getDrawable(R.drawable.icon_my_word));
        } else if (fileName.toLowerCase().contains(".xls") || fileName.toLowerCase().contains(".xlsx")) {
            imageView.setBackgroundDrawable(imageView.getContext().getResources().getDrawable(R.drawable.icon_my_exl));
        } else if (fileName.toLowerCase().contains(".pptx") || fileName.toLowerCase().contains(".ppt")) {
            imageView.setBackgroundDrawable(imageView.getContext().getResources().getDrawable(R.drawable.icon_my_ppt));
        } else if (fileName.toLowerCase().contains(".txt")) {
            imageView.setBackgroundDrawable(imageView.getContext().getResources().getDrawable(R.drawable.icon_my_txt));
        } else if (fileName.toLowerCase().contains(".mov") || fileName.toLowerCase().contains(".rm") || fileName.toLowerCase().contains(".rmvb")
                || fileName.toLowerCase().contains(".wmv") || fileName.toLowerCase().contains(".mpg") || fileName.toLowerCase().contains(".gdf")
                || fileName.toLowerCase().contains(".mp4") || fileName.toLowerCase().contains(".avi") || fileName.toLowerCase().contains(".3gp")
                ) {
            imageView.setBackgroundDrawable(imageView.getContext().getResources().getDrawable(R.drawable.icon_annex_video));
            if (view != null) {
                MediaMetadataRetriever media = new MediaMetadataRetriever();
                media.setDataSource(PathUtil.getInstance().getDownloadDir() + fileName);
                Bitmap bitmap = media.getFrameAtTime();
                view.setBackgroundDrawable(new BitmapDrawable(bitmap));
            }
        } else {
            imageView.setBackgroundDrawable(imageView.getContext().getResources().getDrawable(R.drawable.icon_annex_papers));
        }
    }
}
