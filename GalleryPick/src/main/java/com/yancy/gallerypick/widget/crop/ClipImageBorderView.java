package com.yancy.gallerypick.widget.crop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.yancy.gallerypick.utils.ScreenUtils;


/**
 * @author zhy
 *         http://blog.csdn.net/lmj623565791/article/details/39761281
 */
public class ClipImageBorderView extends View {
    /**
     * 水平方向与View的边距
     */
    private int mHorizontalPadding;
    /**
     * 垂直方向与View的边距
     */
    private int mVerticalPadding;
    /**
     * 绘制的矩形的宽度
     */
    private int mWidth;
    /**
     * 边框的颜色，默认为白色
     */
    private int mBorderColor = Color.parseColor("#00FFFFFF");

    /**
     * 边框的宽度 单位dp
     */
    private int mBorderWidth = 1;

    private int radius;

    private Paint mPaint;
    private Paint mPaintBorder;
    private Canvas mCanvas;// 绘制橡皮擦路径的画布
    private Bitmap fgBitmap;

    public ClipImageBorderView(Context context) {
        this(context, null);
    }

    public ClipImageBorderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipImageBorderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    private void init() {
        mBorderWidth = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, mBorderWidth, getResources()
                        .getDisplayMetrics());
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaintBorder = new Paint();
        mPaintBorder.setAntiAlias(true);
        fgBitmap = Bitmap.createBitmap(ScreenUtils.getScreenWidth(getContext()), ScreenUtils.getScreenHeight(getContext()), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(fgBitmap);
        mCanvas.drawColor(0xaa000000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 计算矩形区域的宽度
        mWidth = getWidth() - 2 * mHorizontalPadding;
        // 计算距离屏幕垂直边界 的边距
        mVerticalPadding = (getHeight() - mWidth) / 2;
        radius = (getWidth() - 2 * mHorizontalPadding) / 2;
        // 绘制半透明背景
        canvas.drawBitmap(fgBitmap, 0, 0, null);
        // 绘制白色边框
        mPaint.setColor(mBorderColor);
        mPaint.setStyle(Style.FILL);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);
        mPaint.setXfermode(null);
        mPaint.setColor(Color.parseColor("#FFFFFF"));
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setStyle(Style.STROKE);
        mCanvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);
    }

    public void setHorizontalPadding(int mHorizontalPadding) {
        this.mHorizontalPadding = mHorizontalPadding;
        invalidate();
    }

}
