package com.onesoft.netlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.onesoft.netlibrary.R;

import java.io.File;

/**
 * 图片加载工具类
 * Created by Jayden on 2016/7/26.
 */
public class ImageHandler {

    public static void resumeRequest(Activity activity) {
        Glide.with(activity).resumeRequestsRecursive();
    }

    public static void pauseRequest(Activity activity) {
        Glide.with(activity).pauseRequestsRecursive();
    }

    public static synchronized void onTrimMemory(Context context, int level) {
        Glide.get(context).trimMemory(level);
    }

    /**
     * 清空本地缓存
     */
    public static void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }

    /**
     * 清空内存缓存
     */
    public static void clearMemoryCache(Context context) {
        Glide.get(context).clearMemory();
    }

    /**
     * 从url加载banner大图
     */
    public static void getBannerImage(Context context, ImageView imageView, String url, int width, int height) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (TextUtils.isEmpty(url)) {
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.digital_default));
            return;
        }
        if (checkActivityNull(context)) {
            return;
        }
        Glide.with(context).load(url)
                .placeholder(R.drawable.digital_default).error(R.drawable.digital_default).diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop().dontAnimate().override(width, height).into(imageView);
    }

    public static void getBigImage(Activity context, ImageView image, String url, Target target) {
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        if (checkActivityNull(context)) {
            return;
        }
        Glide.with(context).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.RESULT).error(R.drawable.digital_default).into(target);
    }

    public static void getBigFileImage(Activity context, ImageView imageView, String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int width = 1080;
        int height = 1920;
        // 压缩比例参考800万像素，3264 * 2248
        if (options.outWidth * options.outHeight > width * height) {
            int min = Math.min(options.outWidth, options.outHeight);
            int max = Math.max(options.outWidth, options.outHeight);
            float ratio = Math.min(1.0f * min / 1080, 1.0f * max / 1920);
            width = (int) (options.outWidth / ratio);
            height = (int) (options.outHeight / ratio);
            LogUtil.e("width:" + width + ",height:" + height);
            if (checkActivityNull(context)) {
                return;
            }
            Glide.with(context).load(new File(path))
                    .diskCacheStrategy(DiskCacheStrategy.RESULT).error(R.drawable.digital_default)
                    .override(width, height).into(imageView);
        } else {
            if (checkActivityNull(context)) {
                return;
            }
            Glide.with(context).load(new File(path))
                    .diskCacheStrategy(DiskCacheStrategy.RESULT).error(R.drawable.digital_default)
                    .into(imageView);
        }
    }
    /**
     * 从url加载用户头像
     */
    public static void getAvater(Activity context, final ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_default_avatar));
            return;
        }
        if (checkActivityNull(context)) {
            return;
        }
        Glide.with(context).load(url).asBitmap()
                .placeholder(R.drawable.icon_default_avatar).error(R.drawable.icon_default_avatar)
                .transform(new CircleTransform(context)).centerCrop().diskCacheStrategy(DiskCacheStrategy.RESULT)
                .dontAnimate().into(imageView);
    }

    /**
     * 从url加载普通图片
     */
    public static void getImage(Context context, final ImageView imageView, String url, int defaultID) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageDrawable(context.getResources().getDrawable(defaultID));
            return;
        }
        if (checkActivityNull(context)) {
            return;
        }
        Glide.with(context).load(url).error(defaultID).placeholder(defaultID)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop().into(imageView);
    }

    /**
     * 从url加载普通图片
     */
    public static void getImage(Activity context, final ImageView mImageView, String url) {
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (TextUtils.isEmpty(url)) {
            mImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.digital_default));
            return;
        }
        if (checkActivityNull(context)) {
            return;
        }
        Glide.with(context).load(url)
                .placeholder(R.drawable.digital_default).error(R.drawable.digital_default).diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(mImageView);
    }

    /**
     * 从file记载普通图片
     */
    public static void getFileImage(Activity context, final ImageView mImageView, String path, int width, int height) {
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (TextUtils.isEmpty(path)) {
            mImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.digital_default));
            return;
        }
        if (checkActivityNull(context)) {
            return;
        }
        Glide.with(context).load(new File(path))
                .placeholder(R.drawable.digital_default).error(R.drawable.digital_default).diskCacheStrategy(DiskCacheStrategy.RESULT)
                .override(width, height).centerCrop()
                .into(mImageView);
    }

    public static void getFileImage(Activity context, final ImageView mImageView, String path, int width, int height, int place) {
        if (TextUtils.isEmpty(path)) {
            mImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.digital_default));
            return;
        }
        if (checkActivityNull(context)) {
            return;
        }
        Glide.with(context).load(new File(path))
                .placeholder(place).error(place).diskCacheStrategy(DiskCacheStrategy.RESULT)
                .override(width, height).centerCrop()
                .into(mImageView);
    }

    private static boolean checkActivityNull(Context activity) {
        if (activity instanceof Activity) {
            if (activity == null || ((Activity) activity).isFinishing()) {
                return true;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && ((Activity) activity).isDestroyed()) {
                return true;
            }
            return false;
        }
        return false;
    }

}
