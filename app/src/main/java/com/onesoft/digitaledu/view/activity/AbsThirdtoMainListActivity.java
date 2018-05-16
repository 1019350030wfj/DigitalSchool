package com.onesoft.digitaledu.view.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.app.Constant;
import com.onesoft.digitaledu.model.ListBean;
import com.onesoft.digitaledu.model.ThirdToMainBean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.presenter.ListPresenter;
import com.onesoft.digitaledu.utils.ViewUtil;
import com.onesoft.digitaledu.view.OnDeleteListener;
import com.onesoft.digitaledu.view.activity.person.DownloadFileFragment;
import com.onesoft.digitaledu.view.activity.person.OfflineDownloadActivity;
import com.onesoft.digitaledu.view.activity.person.download.DownloadedAdapter;
import com.onesoft.digitaledu.view.activity.person.download.DownloadingFileFragment;
import com.onesoft.digitaledu.view.activity.publicfoundation.menu.MenuTitleAdapter;
import com.onesoft.digitaledu.view.activity.publicfoundation.menu.ThirdToMainAdapter;
import com.onesoft.digitaledu.view.iview.IThirdToMainView;
import com.onesoft.digitaledu.widget.ptr.PtrListView;

import org.wlf.filedownloader.DownloadFileInfo;

import java.util.List;

import static com.onesoft.digitaledu.R.id.count;

/**
 * 基础列表Activity（部门列表、菜单列表、用户列表）等等
 * <p>
 * 2、初始化具体的数据Adapter
 * 3、初始化标题右边菜单图标和点击菜单的事件
 * 4、初始化页面的标题
 * 5、实现IListView时，必须指定<D>的类型。比如<MenuDetail>
 * Created by Jayden on 2016/11/22.
 */
public abstract class AbsThirdtoMainListActivity extends ToolBarActivity<ListPresenter> implements IThirdToMainView,PtrListView.OnRefreshListener, PtrListView.OnLoadMoreListener {

    private RecyclerView mRVMenuTitle;
    private ImageButton mMenuTitleBtn;
    private ImageButton mUpBtn;
    protected PtrListView mListView;
    protected MenuTitleAdapter mTitleAdapter;
    protected ThirdToMainAdapter mDetailAdapter;

    protected ListBean mListBean;
    protected TopBtn mBatchDeleteBtn;
    protected int page = 1;

    private View mLLMessageBottom;
    private View mLLMenu;
    private TextView mTvAll;

    private boolean mIsDeleteMode = false;
    private boolean isSelectAll = false;

    protected String id;//三级菜单的id

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_third_to_main_list, null);
    }

    @Override
    public void initView() {
        mRVMenuTitle = (RecyclerView) findViewById(R.id.rv_menu);
        mListView = (PtrListView) findViewById(R.id.listview);
        mMenuTitleBtn = (ImageButton) findViewById(R.id.img_btn_menu);
        mUpBtn = (ImageButton) findViewById(R.id.btn_up);

        mLLMessageBottom = findViewById(R.id.ll_message_bottom);
        mLLMenu = findViewById(R.id.ll_menu);

        mTvAll = (TextView) findViewById(R.id.tv_all);
        ViewUtil.translateToHide(this, mLLMessageBottom);
    }

    @Override
    public void initListener() {
        mListView.setOnRefreshListener(this);
        mListView.setOnLoadMoreListener(this);
        findViewById(R.id.ll_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.deleteBatch(mBatchDeleteBtn,mDetailAdapter.getDatas());
            }
        });

        findViewById(R.id.ll_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSelectAll) {
                    mTvAll.setText(getResources().getString(R.string.cancel_all_select));
                } else {
                    mTvAll.setText(getResources().getString(R.string.all_select));
                }
                isSelectAll = !isSelectAll;
                setSelectAll(isSelectAll);
            }
        });

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
                if (firstVisibleItem >= Constant.VISIBLE_ITEM_TO_TOP) {
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

    public void setSelectAll(boolean isSelectAll) {//全选
        if (mIsDeleteMode) {
            for (ThirdToMainBean boxBean : mDetailAdapter.getDatas()) {
                boxBean.isDelete = isSelectAll;
            }
            mDetailAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void initData() {
        mTitleAdapter = new MenuTitleAdapter(this);
        mDetailAdapter = new ThirdToMainAdapter(this);
        mDetailAdapter.setOnDeleteListener(new OnDeleteListener() {
            @Override
            public void onDelete() {
               updateDeleteNum();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRVMenuTitle.setLayoutManager(layoutManager);
        mRVMenuTitle.setAdapter(mTitleAdapter);
        mListView.setAdapter(mDetailAdapter);
    }


    @Override
    public void onLoadMore() {
        page++;
        mPresenter.getData(mListBean.id, page);
    }

    @Override
    public void onRefresh() {
        page = 1;
        mPresenter.getData(mListBean.id, page);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_download, menu);
        return true;
    }

    protected View mMenuView;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_download);
        View view = LayoutInflater.from(this).inflate(R.layout.menu_search, null);
        mMenuView = view.findViewById(R.id.riv_menu_main);
        if (item != null) {
            item.setActionView(view);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    public void updateDeleteNum() {
        List<ThirdToMainBean> list = mDetailAdapter.getDatas();
        int count = 0;
        for (ThirdToMainBean boxBean : list) {
            if (boxBean.isDelete) {//需要删除的
                count++;
            }
        }
        getSupportActionBar().setTitle(getResources().getString(R.string.already_select_delete1, count));
    }

    public void showDeleteMode(TopBtn topBtn) {//当是删除模式的时候
        mBatchDeleteBtn = topBtn;
        if (!mIsDeleteMode) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//变成正常模式
                    showNormalMode();
                }
            });
            setTitle("");
            updateDeleteNum();
            mMenuView.setVisibility(View.INVISIBLE);
            mLLMenu.setVisibility(View.GONE);

            ViewUtil.translateToShow(this, mLLMessageBottom);
            mDetailAdapter.setISDeleteMode(true);
            mIsDeleteMode = true;
        }
    }


    public void showNormalMode() {//正常模式
        if (mIsDeleteMode) {
            ViewUtil.translateToHide(this, mLLMessageBottom);

            initToolbar();
            mMenuView.setVisibility(View.VISIBLE);
            mLLMenu.setVisibility(View.VISIBLE);
            getTitleName();
            mDetailAdapter.setISDeleteMode(false);
            mIsDeleteMode = false;
        }
    }

    protected void getTitleName() {
    }
}
