package com.onesoft.digitaledu.view.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.pagestate.PageStateLayout;

import static com.onesoft.digitaledu.R.id.toolbar;


/**
 * 包含Toolbar的基类，可以配置是否需要滑动退出
 * <p>
 * Created by Jayden on 2016/4/18.
 * Email : 1570713698@qq.com
 */
public abstract class ToolBarActivity<T extends BasePresenter> extends BaseActivity<T> implements IActivityView {

    private FrameLayout mContainer;
    protected Toolbar mToolbar;
    private View mTopMargin;
    private View mLine;
    private View mTitleRightImg;
    private View mTitleContainer;
    private TextView tvTitle;

    protected PageStateLayout mPageStateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContainer = (FrameLayout) findViewById(R.id.fl_container);
        mPageStateLayout = new PageStateLayout(this);
        initPageState();
        mPageStateLayout.setOnEmptyListener(mOnEmptyListener)
                .setOnErrorListener(mOnErrorListener)
                .load(mContainer, initContentView(getLayoutInflater()));
//        mContainer.addView(initContentView(getLayoutInflater()));
        mPageStateLayout.onLoading();
        mTopMargin = findViewById(R.id.v_top);
        mToolbar = (Toolbar) findViewById(toolbar);
        mLine = findViewById(R.id.v_line);
        mTitleRightImg = findViewById(R.id.head_title_img);
        mTitleContainer = findViewById(R.id.title_container);
        tvTitle = (TextView) findViewById(R.id.tv_title);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        initToolbar();

        initView();
        initListener();
        initData();
    }

    protected void initPageState() {//这边可以自定义空状态页面

    }

    private View.OnClickListener mOnEmptyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(ToolBarActivity.this, "Do something when empty", Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener mOnErrorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(ToolBarActivity.this, "Do something when error", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.base_toolbar_activity;
    }

    /* ============================ Toolbar Setting =============================== */
    public void initToolbar() {
        mToolbar.setNavigationIcon(R.drawable.nav_btn_back_nor);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void showToolbar() {
        ViewCompat.animate(mToolbar).translationY(0);
        ViewCompat.animate(mToolbar).alpha(1);
        mTopMargin.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideToolbar() {
        ViewCompat.setTranslationY(mToolbar, -getResources().getDimension(R.dimen.toolbar_height));
        ViewCompat.setAlpha(mToolbar, 0);
        mTopMargin.setVisibility(View.GONE);
    }

    /* ============================ Toolbar Setting =============================== */

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
    }

    /**
     * 获取内容显示父容器
     *
     * @return
     */
    @Override
    public FrameLayout getmContainer() {
        return mContainer;
    }

    /**
     * 设置状态栏透明
     *
     * @param on
     */
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 设置全屏
     *
     * @param on
     */
    protected void setFullscreen(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 隐藏toolbar下面的横线
     */
    public void hideTitleLine() {
        mLine.setVisibility(View.GONE);
    }

    /**
     * 不设置页面距离上边距的高度，为toolbar的高度
     */
    public void clipToTop() {
        mTopMargin.setVisibility(View.GONE);
    }

    /**
     * 设置页面的标题
     *
     * @param title
     */
    public void setTitle(String title) {
        tvTitle.setText(title);
        findViewById(R.id.iv_logo).setVisibility(View.GONE);
    }

    /**
     * 没有设置标题，在标题的位置设置logo
     *
     * @param logo
     */
    public void setLogo(Drawable logo) {
        ImageView ivLogo = (ImageView) findViewById(R.id.iv_logo);
        ivLogo.setImageDrawable(logo);
        ivLogo.setVisibility(View.VISIBLE);
        mTitleContainer.setVisibility(View.GONE);
    }

    /**
     * 没有设置标题，在标题的位置设置logo
     *
     * @param id
     */
    public void setLogo(int id) {
        ImageView ivLogo = (ImageView) findViewById(R.id.iv_logo);
        ivLogo.setImageResource(id);
        ivLogo.setVisibility(View.VISIBLE);
        mTitleContainer.setVisibility(View.GONE);
    }

    /**
     * 设置title，后面的ImageView
     * 比如向下展开的图标
     *
     * @param isVisible
     */
    public void setmTitleRightImg(int isVisible) {
        mTitleRightImg.setVisibility(isVisible);
    }
}
