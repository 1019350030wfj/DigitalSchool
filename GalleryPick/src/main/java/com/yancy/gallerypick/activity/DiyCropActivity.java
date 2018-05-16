package com.yancy.gallerypick.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.netlibrary.utils.ImageHandler;
import com.onesoft.netlibrary.utils.PathUtil;
import com.yancy.gallerypick.R;
import com.yancy.gallerypick.config.Constants;
import com.yancy.gallerypick.utils.BitmapUtils;
import com.yancy.gallerypick.widget.crop.ClipImageLayout;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Jayden on 2016/11/21.
 */

public class DiyCropActivity extends Activity {

    private TextView mConfirm;
    private ImageView mImageView;
    private ClipImageLayout mClipLayout;

    public static void clipPic(Activity activity, String path, int requestCode) {
        Intent intent = new Intent(activity, DiyCropActivity.class);
        intent.putExtra("path", path);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diy_crop);
        mImageView = (ImageView)findViewById(R.id.iv_logo);
        mConfirm = (TextView)findViewById(R.id.tv_header_right);
        mClipLayout = (ClipImageLayout) findViewById(R.id.clip_layout);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCropImg();
            }
        });

        Intent intent = getIntent();
        if (intent.getExtras() == null) {
            finish();
        }
        String path = intent.getStringExtra("path");
        ImageHandler.clearMemoryCache(this);
        mClipLayout.setImage(this, path);
    }

    private void saveCropImg() {
        Bitmap bitmap = mClipLayout.clip();
        bitmap = BitmapUtils.scaleImg(bitmap, Constants.UPLOAD_CROP_AVATER, Constants.UPLOAD_CROP_AVATER);
        File file = new File(PathUtil.getInstance().getCacheCropDir(), "crop_" + System.currentTimeMillis() + ".jpg");
        try {
            file.createNewFile();
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            Intent intent = new Intent();
            intent.putExtra("path", file.getPath());
            setResult(RESULT_OK, intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
                bitmap = null;
            }
        }
    }
}
