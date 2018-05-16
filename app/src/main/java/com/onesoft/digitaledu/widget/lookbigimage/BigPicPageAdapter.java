package com.onesoft.digitaledu.widget.lookbigimage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.onesoft.digitaledu.R;
import com.onesoft.netlibrary.utils.ImageHandler;

/**
 * 查看大图Adapter
 */
public class BigPicPageAdapter extends BasePageAdapter<String> {
    private OnFadeOutListener mListener;

    public BigPicPageAdapter(Activity context) {
        super(context);
    }

    public void setOnFadeOutListener(OnFadeOutListener l) {
        mListener = l;
    }

    @Override
    public View initConvertView(LayoutInflater mInflater, String item) {
        View view = mInflater.inflate(R.layout.item_big_pic_page, null);
        final ProgressWheel progress = (ProgressWheel) view.findViewById(R.id.progress);
        final PhotoView photo = (PhotoView) view.findViewById(R.id.photo);
        photo.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                if (mListener != null) {
                    mListener.fadeOut();
                }
            }
        });
        if (item.startsWith("http://") || item.startsWith("https://")) {//网络图片
            ImageHandler.getBigImage(getContext(), photo, item, new BitmapImageViewTarget(photo) {
                @Override
                protected void setResource(Bitmap resource) {
                    super.setResource(resource);
                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    super.onLoadFailed(e, errorDrawable);
                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onLoadCleared(Drawable placeholder) {
                    super.onLoadCleared(placeholder);
                    progress.setVisibility(View.GONE);
                }


            });
        } else {
            ImageHandler.getBigFileImage(getContext(), photo, item, new GlideDrawableImageViewTarget(photo) {
                @Override
                protected void setResource(GlideDrawable resource) {
                    super.setResource(resource);
                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onLoadCleared(Drawable placeholder) {
                    super.onLoadCleared(placeholder);
                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    super.onLoadFailed(e, errorDrawable);
                    progress.setVisibility(View.GONE);
                }
            });
        }
        return view;
    }
}
