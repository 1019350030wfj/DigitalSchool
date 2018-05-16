package com.onesoft.digitaledu.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.model.ListBean;
import com.onesoft.digitaledu.model.ThirdToMainBean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.menu.SearchPresenter;
import com.onesoft.digitaledu.view.activity.publicfoundation.menu.ThirdToMainAdapter;
import com.onesoft.digitaledu.view.iview.menu.ISearchView;
import com.onesoft.digitaledu.widget.ptr.PtrListView;
import com.yancy.gallerypick.utils.AppUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Jayden on 2016/11/23.
 */

public class BaseSearchActivity extends ToolBarActivity<SearchPresenter> implements ISearchView, PtrListView.OnLoadMoreListener {

    private TopBtn mTopBtn;
    protected ListBean mListBean;

    public static void startSearch(Context context, TopBtn topBtn, ListBean listBean) {
        Intent intent = new Intent(context, BaseSearchActivity.class);
        intent.putExtra("data", topBtn);
        intent.putExtra("listBean", listBean);
        context.startActivity(intent);
    }

    private void getDataFromForward() {
        if (getIntent() != null) {
            if (getIntent().getExtras() != null) {
                mTopBtn = (TopBtn) getIntent().getExtras().getSerializable("data");
                mListBean = (ListBean) getIntent().getExtras().getSerializable("listBean");
            }
        }
    }

    private View mllEmpty;
    private PtrListView mListView;
    private EditText mEditText;
    private TextView mTextSearchHint;
    private ImageButton mUpBtn;
    private ThirdToMainAdapter mDetailAdapter;

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new SearchPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_search_menu, null);
    }

    @Override
    public void initView() {
        mUpBtn = (ImageButton) findViewById(R.id.btn_up);
        mListView = (PtrListView) findViewById(R.id.listview);
        mllEmpty = findViewById(R.id.ll_search_empty);
        mTextSearchHint = (TextView) findViewById(R.id.txt_search_hint);
        mEditText = (EditText) findViewById(R.id.edt_search);
    }

    @Override
    public void initData() {
        setTitle(getString(R.string.search));
        mDetailAdapter = new ThirdToMainAdapter(this);
        mListView.setAdapter(mDetailAdapter);
        mTextSearchHint.setText(getString(R.string.search_hint, mListBean.name));
        changeVisibleState();
        mEditText.setHint(mTopBtn.notice);
        mPageStateLayout.onSucceed();
        EventBus.getDefault().register(this);
    }

    private void changeVisibleState() {
        if (mDetailAdapter.getDatas() == null || mDetailAdapter.getDatas().size() == 0) {
            mllEmpty.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        } else {
            mllEmpty.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initListener() {
        mListView.setOnLoadMoreListener(this);
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

        findViewById(R.id.img_search_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText("");
                mDetailAdapter.setDatas(null);
                changeVisibleState();
            }
        });

        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!TextUtils.isEmpty(mEditText.getText().toString())) {
                        mPage = 1;
                        mSearchKey = mEditText.getText().toString();
                        mPresenter.search(mSearchKey, mPage, mListBean, mTopBtn);
                        return true;
                    }
                }
                return false;
            }
        });
        findViewById(R.id.img_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mEditText.getText().toString())) {
                    mPage = 1;
                    mSearchKey = mEditText.getText().toString();
                    mPresenter.search(mSearchKey, mPage, mListBean, mTopBtn);
                }
            }
        });
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString()) && start == 0 && count == 0) {
                    mDetailAdapter.setDatas(null);
                    changeVisibleState();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onSuccess(final List<ThirdToMainBean> menuDetails) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mListView.stopLoadMore();
                if (mPage == 1) {//这边要通过返回的条数，和限制每一页返回的条数，大小来判断是否还可以加载更多
                    mDetailAdapter.setSearchContent(mSearchKey);
                    mDetailAdapter.setDatas(menuDetails);
                    changeVisibleState();
                } else {
                    if (menuDetails != null && menuDetails.size() > 0) {
                        mDetailAdapter.setSearchContent(mSearchKey);
                        mDetailAdapter.addData(menuDetails);
                    }
                }
                if (menuDetails != null && menuDetails.size() > 0) {
                    if (menuDetails.size() == HttpUrl.PAGE_SIZE) {
                        mListView.setLoadMoreEnable(true);
                    } else {
                        mListView.setLoadMoreEnable(false);
                    }
                } else {
                    mListView.setLoadMoreEnable(false);
                }
                AppUtils.hideSoftKeyBoard(BaseSearchActivity.this);//隐藏软键盘
            }
        });
    }

    private int mPage = 1;
    private String mSearchKey = "";

    @Override
    public void onLoadMore() {
        mPage++;
        mPresenter.search(mSearchKey, mPage, mListBean, mTopBtn);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 使用事件总线监听回调
     *
     * @param event
     */
    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBaseEvent(final BaseEvent event) {
        switch (event.type) {
            case BaseEvent.UPDATE_THIRD_MAIN_TO_LIST://这是从动态页的动态页进入的搜索页面
            case BaseEvent.UPDATE_THIRD_TO_MAIN: {//这是从动态页进入的搜索页面
                mPage = 1;
                mPresenter.search(mSearchKey, mPage, mListBean, mTopBtn);
                break;
            }
        }
    }

}
