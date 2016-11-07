/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.onesoft.jaydenim.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.onesoft.jaydenim.R;
import com.onesoft.jaydenim.model.EaseImageCache;
import com.onesoft.jaydenim.utils.EaseImageUtils;
import com.onesoft.jaydenim.utils.EaseLoadLocalBigImgTask;
import com.onesoft.jaydenim.utils.LogUtil;
import com.onesoft.jaydenim.widget.EaseAlertDialog;
import com.onesoft.jaydenim.widget.photoview.EasePhotoView;

import java.io.File;

/**
 * download and show original image
 */
public class EaseShowBigImageActivity extends EaseBaseActivity {

    private static final String TAG = "ShowBigImage";
    private EasePhotoView image;
    private int default_res = R.drawable.ease_default_image;
    private Bitmap bitmap;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.ease_activity_show_big_image);
        super.onCreate(savedInstanceState);

        image = (EasePhotoView) findViewById(R.id.image);
        ProgressBar loadLocalPb = (ProgressBar) findViewById(R.id.pb_load_local);
        default_res = getIntent().getIntExtra("default_image", R.drawable.ease_default_avatar);
        final Uri uri = getIntent().getParcelableExtra("uri");

        //show the image if it exist in local path
        if (uri != null && new File(uri.getPath()).exists()) {
            LogUtil.d(TAG, "showbigimage file exists. directly show it");
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            bitmap = EaseImageCache.getInstance().get(uri.getPath());
            if (bitmap == null) {
                EaseLoadLocalBigImgTask task = new EaseLoadLocalBigImgTask(this, uri.getPath(), image,
                        loadLocalPb, EaseImageUtils.SCALE_IMAGE_WIDTH,
                        EaseImageUtils.SCALE_IMAGE_HEIGHT);
                if (android.os.Build.VERSION.SDK_INT > 10) {
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    task.execute();
                }
            } else {
                image.setImageBitmap(bitmap);
            }
        } else {
            image.setImageResource(default_res);
        }

        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String msg = getResources().getString(R.string.Whether_to_save_image);
                new EaseAlertDialog(EaseShowBigImageActivity.this, null, msg, null,
                        new EaseAlertDialog.AlertDialogUser() {
                            @Override
                            public void onResult(boolean confirmed, Bundle bundle) {
                                if (confirmed) {
                                    // 其次把文件插入到系统图库
                                    MediaStore.Images.Media.insertImage(EaseShowBigImageActivity.this.getContentResolver(),
                                            EaseImageCache.getInstance().get(uri.getPath()), "title", "description");
                                    // 最后通知图库更新
                                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                                    Toast.makeText(EaseShowBigImageActivity.this, "保存图片成功！", Toast.LENGTH_LONG).show();
                                }
                            }
                        }, true).show();
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
