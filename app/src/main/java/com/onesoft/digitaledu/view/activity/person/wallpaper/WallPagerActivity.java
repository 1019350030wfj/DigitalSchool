package com.onesoft.digitaledu.view.activity.person.wallpaper;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.presenter.person.WallPaperPresenter;
import com.onesoft.digitaledu.utils.PermissionUtils;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.utils.ViewUtil;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.person.IWallPaperView;
import com.onesoft.digitaledu.widget.DividerGridItemDecoration;
import com.onesoft.digitaledu.widget.FullyGridLayoutManager;
import com.onesoft.netlibrary.utils.PathUtil;

import java.io.File;

/**
 * Created by Jayden on 2016/11/16.
 */
public class WallPagerActivity extends ToolBarActivity<WallPaperPresenter> implements IWallPaperView {

    private static final int REQUEST_ITEM = 0x110;
    private static final int REQUEST_CAMERA = 0x111;
    private static final int REQUEST_ALBUM_OK = 0x112;
    private static final int SELECT_PIC_KITKAT = 0x113;
    private static final int SELECT_PIC = 0x114;

    private View mRlAlbum;
    private RecyclerView mRecyclerView;
    private WallPaperAdapter mPaperAdapter;

    @Override
    protected void initPresenter() {
        mPresenter = new WallPaperPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_wallpaper, null);
    }

    @Override
    public void initView() {
        mRlAlbum = findViewById(R.id.rl_album);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new FullyGridLayoutManager(this, 3));
    }

    @Override
    public void initData() {
        setTitle(getString(R.string.wallpaper_setting));
        mPaperAdapter = new WallPaperAdapter(this);
        mPaperAdapter.setOnClickItemListener(new WallPaperAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(int pos) {
                switch (pos) {
                    case 0: {//拍摄照片
                        // 拍照后存储并显示图片
                        PermissionUtils.requestPermission(WallPagerActivity.this, PermissionUtils.CODE_CAMERA, mPermissionGrant);
                        break;
                    }
                    default: {//跳转到壁纸设置完成界面
                        WallpaperSettingActivity.startWPSettingActivity(WallPagerActivity.this, pos, REQUEST_ITEM);
                        break;
                    }
                }
            }
        });
        mRecyclerView.setAdapter(mPaperAdapter);
        DividerGridItemDecoration dividerItemDecoration = new DividerGridItemDecoration(this);
        dividerItemDecoration.setDivider(getResources().getDrawable(R.drawable.shape_divider_db_3));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mPageStateLayout.onSucceed();
    }

    @Override
    public void initListener() {
        mRlAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到相册
                Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(albumIntent, REQUEST_ALBUM_OK);

//                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);//ACTION_OPEN_DOCUMENT
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.setType("image/*");
//                if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.KITKAT){
//                    startActivityForResult(intent, SELECT_PIC_KITKAT);
//                }else{
//                    startActivityForResult(intent, SELECT_PIC);
//                }
            }
        });
    }


    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
        Uri photoUri = Uri.fromFile(new File(PathUtil.getInstance().getPathWallpaper())); // 传递路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);// 更改系统默认存储路径
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回数据
            if (requestCode == REQUEST_CAMERA) {
                SPHelper.setWallPaperPath(WallPagerActivity.this, PathUtil.getInstance().getPathWallpaper());
                WallpaperSettingActivity.startWPSettingActivity(WallPagerActivity.this, 0, REQUEST_CAMERA);
                finish();
            } else if (requestCode == REQUEST_ALBUM_OK) {
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    SPHelper.setWallPaperPath(WallPagerActivity.this, path);
                    WallpaperSettingActivity.startWPSettingActivity(WallPagerActivity.this, ViewUtil.REQUEST_ALBUM, REQUEST_ALBUM_OK);
                }
                finish();
            } else if (requestCode == REQUEST_ITEM) {
                finish();
            }
        }
    }

    /*=======================权限处理==============================*/
    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_RECORD_AUDIO:
                    Toast.makeText(WallPagerActivity.this, "Result Permission Grant CODE_RECORD_AUDIO", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_GET_ACCOUNTS:
                    Toast.makeText(WallPagerActivity.this, "Result Permission Grant CODE_GET_ACCOUNTS", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_PHONE_STATE:
                    Toast.makeText(WallPagerActivity.this, "Result Permission Grant CODE_READ_PHONE_STATE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_CALL_PHONE:
                    Toast.makeText(WallPagerActivity.this, "Result Permission Grant CODE_CALL_PHONE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_CAMERA:
                    openCamera();
                    break;
                case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
                    Toast.makeText(WallPagerActivity.this, "Result Permission Grant CODE_ACCESS_FINE_LOCATION", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
                    Toast.makeText(WallPagerActivity.this, "Result Permission Grant CODE_ACCESS_COARSE_LOCATION", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
                    Toast.makeText(WallPagerActivity.this, "Result Permission Grant CODE_READ_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                    Toast.makeText(WallPagerActivity.this, "Result Permission Grant CODE_WRITE_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);
    }
    /*=======================权限处理==============================*/
}
