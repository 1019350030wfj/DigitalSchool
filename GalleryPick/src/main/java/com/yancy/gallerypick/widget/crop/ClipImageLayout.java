package com.yancy.gallerypick.widget.crop;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.onesoft.netlibrary.utils.ImageHandler;
import com.yancy.gallerypick.utils.AppUtils;
import com.yancy.gallerypick.utils.ScreenUtils;


/**
 * http://blog.csdn.net/lmj623565791/article/details/39761281
 *
 * @author zhy
 */
public class ClipImageLayout extends RelativeLayout {
    /**
     * 上传裁剪头像的宽高
     */
    public static final float CROP_AVATER_RATIO = 0.7f;

    private ClipZoomImageView mZoomImageView;
    private ClipImageBorderView mClipImageView;

    /**
     * 这里测试，直接写死了大小，真正使用过程中，可以提取为自定义属性
     */
    private int mHorizontalPadding = 20;

    public ClipImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mZoomImageView = new ClipZoomImageView(context);
        mClipImageView = new ClipImageBorderView(context);

        android.view.ViewGroup.LayoutParams lp = new LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT);

        this.addView(mZoomImageView, lp);
        this.addView(mClipImageView, lp);
        float dp = ScreenUtils.getScreenWidth(getContext()) * (1.0f - CROP_AVATER_RATIO) / 2;
        dp = AppUtils.px2dip(getContext(), dp);
        setHorizontalPadding((int) dp);
    }

    /**
     * 对外公布设置边距的方法,单位为dp
     *
     * @param padding
     */
    public void setHorizontalPadding(int padding) {
        mHorizontalPadding = padding;
        // 计算padding的px
        padding = (int) AppUtils.dipToPx(getContext(), padding);
        mZoomImageView.setHorizontalPadding(padding);
        mClipImageView.setHorizontalPadding(padding);
    }

    /**
     * 裁切图片
     *
     * @return
     */
    public Bitmap clip() {
        return mZoomImageView.clip();
    }

    /**
     * 设置图片
     */
    public void setImage(Activity activity, String path) {
        ImageHandler.getBigFileImage(activity, mZoomImageView, path);
    }

}
