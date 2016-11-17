package com.onesoft.digitaledu.view.activity.person.wallpaper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.presenter.person.WallPaperPresenter;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.utils.ViewUtil;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.person.IWallPaperView;

import org.greenrobot.eventbus.EventBus;

import static com.onesoft.digitaledu.model.BaseEvent.WALLPAPER_UPDATE;

/**
 * Created by Jayden on 2016/11/17.
 */

public class WallpaperSettingActivity extends ToolBarActivity<WallPaperPresenter> implements IWallPaperView {

    private ImageView mImageView;
    private int mPos;//代表选择的壁纸的位置

    public static void startWPSettingActivity(Context context, int pos, int requestCode) {
        Intent intent = new Intent(context, WallpaperSettingActivity.class);
        intent.putExtra("pos", pos);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    private void getDataFromForward() {
        mPos = getIntent().getExtras().getInt("pos", 1);
    }

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new WallPaperPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_wallpaper_setting, null);
    }

    @Override
    public void initView() {
        mImageView = (ImageView) findViewById(R.id.imageview);
    }

    @Override
    public void initData() {
        setTitle(getString(R.string.wallpaper_setting));
        ViewUtil.setBackGround(mImageView, mPos);
        mPageStateLayout.onSucceed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wallpaper_setting, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_finish);
        View view = LayoutInflater.from(this).inflate(R.layout.menu_setting_wallpaper, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //完成
                SPHelper.saveWallPaperPosition(WallpaperSettingActivity.this, mPos);
                EventBus.getDefault().post(new BaseEvent(WALLPAPER_UPDATE, ""));
                setResult(RESULT_OK);
                finish();
            }
        });
        if (item != null) {
            item.setActionView(view);
        }

        return super.onPrepareOptionsMenu(menu);
    }
}
