package com.onesoft.digitaledu.view.activity.person;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.presenter.person.ChangeAvatarPresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.IChangeAvatarView;
import com.onesoft.digitaledu.widget.crop.GlideImageLoader;
import com.onesoft.netlibrary.utils.ImageHandler;
import com.onesoft.netlibrary.utils.LogUtil;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jayden on 2016/11/14.
 */

public class ChangeAvatarActivity extends ToolBarActivity<ChangeAvatarPresenter> implements IChangeAvatarView {

    private ImageView mImageView;
    private Button mBtnChangeAvatar;

    private List<String> path = new ArrayList<>();
    private GalleryConfig galleryConfig;
    private IHandlerCallBack iHandlerCallBack;

    private String mImagePath;

    private void getDataFromForward() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mImagePath = bundle.getString("path");
            }
        }
    }

    public static void startChangeAvatar(Context context, String path, int requestCode) {
        Intent intent = new Intent(context, ChangeAvatarActivity.class);
        intent.putExtra("path", path);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new ChangeAvatarPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_change_avatar, null);
    }

    @Override
    public void initToolbar() {
        super.initToolbar();
        hideToolbar();
        hideTitleLine();
    }

    @Override
    public void initView() {
        mBtnChangeAvatar = (Button) findViewById(R.id.btn_change_avatar);
        mImageView = (ImageView) findViewById(R.id.img_avatar);
    }

    @Override
    public void initListener() {
        mBtnChangeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryConfig.getBuilder().isOpenCamera(false).build();
                initPermissions();
            }
        });

        findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击确定，修改头像
                if (path != null && path.size() > 1) {
                    mPresenter.changeAvatar(path.get(0));
                }
            }
        });
    }

    // 授权管理
    private void initPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请在 设置-应用管理 中开启此应用的储存授权。", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        } else {
            GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(ChangeAvatarActivity.this);
        }
    }

    private final int PERMISSIONS_REQUEST_READ_CONTACTS = 8;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(ChangeAvatarActivity.this);
            } else {
                LogUtil.e("拒绝授权");
            }
        }
    }

    @Override
    public void initData() {
        iHandlerCallBack = new IHandlerCallBack() {
            @Override
            public void onStart() {
                LogUtil.e("onStart: 开启");
            }

            @Override
            public void onSuccess(List<String> photoList) {
                LogUtil.e("onSuccess: 返回数据");
                path.clear();
                for (String s : photoList) {
                    LogUtil.e(s);
                    path.add(s);
                }
                if (path != null && path.size() > 1) {
                    //修改上面那张头像图片
                    ImageHandler.getImage(ChangeAvatarActivity.this, mImageView, path.get(0));
                }
            }

            @Override
            public void onCancel() {
                LogUtil.e("onCancel: 取消");
            }

            @Override
            public void onFinish() {
                LogUtil.e("onFinish: 结束");
            }

            @Override
            public void onError() {
                LogUtil.e("onError: 出错");
            }
        };

        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GlideImageLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .pathList(path)                         // 记录已选的图片
                .multiSelect(false)                      // 是否多选   默认：false
                .multiSelect(false, 9)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                .maxSize(9)                             // 配置多选时 的多选数量。    默认：9
                .crop(true)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                .crop(true, 1, 1, 280, 280)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
                .isShowCamera(true)                     // 是否现实相机按钮  默认：false
                .filePath("/Gallery/Pictures")          // 图片存放路径
                .build();


        ImageHandler.getImage(this, mImageView, mImagePath);
        mPageStateLayout.onSucceed();
    }

    public void onCancel(View view) {
        finish();
    }

    @Override
    public void onSuccess() {
        Intent intent = new Intent();
        intent.putExtra("data", path.get(1));
        setResult(RESULT_OK, intent);
        finish();
    }
}
