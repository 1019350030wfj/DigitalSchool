package com.onesoft.digitaledu.view.activity.publicfoundation.menu;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.MenuDetail;
import com.onesoft.digitaledu.model.MenuTitle;
import com.onesoft.digitaledu.presenter.menu.MenuManagerPresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.menu.IMenuManagerView;

import java.util.List;

/**
 * Created by Jayden on 2016/11/22.
 */
public class MenuManagerActivity extends ToolBarActivity<MenuManagerPresenter> implements IMenuManagerView {

    private RecyclerView mRVMenuTitle;
    private ImageButton mMenuTitleBtn;
    private ImageButton mUpBtn;
    private ListView mListView;
    private MenuTitleAdapter mTitleAdapter;
    private MenuDetailAdapter mDetailAdapter;

    @Override
    protected void initPresenter() {
        mPresenter = new MenuManagerPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_menu_manager, null);
    }

    @Override
    public void initView() {
        mRVMenuTitle = (RecyclerView) findViewById(R.id.rv_menu);
        mListView = (ListView) findViewById(R.id.listview);
        mMenuTitleBtn = (ImageButton) findViewById(R.id.img_btn_menu);
        mUpBtn = (ImageButton) findViewById(R.id.btn_up);
    }

    @Override
    public void initListener() {
        mUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListView.setSelection(0);
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 5) {
                    mUpBtn.setVisibility(View.VISIBLE);
                } else {
                    mUpBtn.setVisibility(View.INVISIBLE);
                }
            }
        });
        mRVMenuTitle.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
//                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    if (firstItemPosition == 0) {//说明可以向左滑动
                        mMenuTitleBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_directive_left));
                    } else {
                        mMenuTitleBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_directive_right));
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @Override
    public void initData() {
        setTitle(getString(R.string.menu_list));
        mTitleAdapter = new MenuTitleAdapter(this);
        mDetailAdapter = new MenuDetailAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRVMenuTitle.setLayoutManager(layoutManager);
        mRVMenuTitle.setAdapter(mTitleAdapter);
        mListView.setAdapter(mDetailAdapter);

        mPresenter.getData();
    }

    @Override
    public void onSuccess(List<MenuTitle> menuTitles, List<MenuDetail> menuDetails) {
        mTitleAdapter.setTitles(menuTitles);
        mDetailAdapter.setDatas(menuDetails);
        mRVMenuTitle.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPageStateLayout.onSucceed();
            }
        }, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_download, menu);
        return true;
    }

    private View mMenuView;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_download);
        View view = LayoutInflater.from(this).inflate(R.layout.menu_search, null);
        mMenuView = view.findViewById(R.id.riv_menu_main);
        mMenuView.setBackgroundResource(R.drawable.nav_btn_search_nor);
        mMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        if (item != null) {
            item.setActionView(view);
        }

        return super.onPrepareOptionsMenu(menu);
    }
}
