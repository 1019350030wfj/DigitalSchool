package com.onesoft.digitaledu.view.activity.infomanager.contacts;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.PersonContact;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.infomanager.contacts.PersonContactPresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.fragment.contacts.PersonContactAdapter;
import com.onesoft.digitaledu.view.iview.infomanager.contacts.IPersonContactView;
import com.onesoft.digitaledu.widget.ptr.PtrListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人通讯录搜索
 * Created by Jayden on 2017/01/07.
 */
public class ContactPersonSearchActivity extends ToolBarActivity<PersonContactPresenter> implements IPersonContactView, PtrListView.OnLoadMoreListener {

    private PtrListView mListView;
    private ImageButton mUpBtn;
    private PersonContactAdapter mDetailAdapter;

    private EditText mEditText;
    private int mPageSearch = 1;
    private String mSearchKey = "";
    private List<PersonContact> mTeacherInfos = new ArrayList<>();

    @Override
    protected void initPresenter() {
        mPresenter = new PersonContactPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_select_teacher, null);
    }

    @Override
    public void initView() {
        mUpBtn = (ImageButton) findViewById(R.id.btn_up);
        mListView = (PtrListView) findViewById(R.id.listview);
        mEditText = (EditText) findViewById(R.id.edt_search);
    }

    @Override
    public void initData() {
        setTitle(getString(R.string.search));
        mDetailAdapter = new PersonContactAdapter(this);
        mListView.setAdapter(mDetailAdapter);
        mPageStateLayout.onSucceed();
    }

    @Override
    public void initListener() {
        mListView.setOnLoadMoreListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactPersonInfoActivity.startContactPersonInfo(mDetailAdapter.getItem(position), ContactPersonSearchActivity.this);
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
                mDetailAdapter.setDatas(mTeacherInfos);
                mDetailAdapter.setSearchContent("");
            }
        });

        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!TextUtils.isEmpty(mEditText.getText().toString())) {
                        mSearchKey = mEditText.getText().toString();
                        mDetailAdapter.setSearchContent(mSearchKey);
                        mPageSearch = 1;
                        mPresenter.getPersonContactByQuery(mPageSearch, mSearchKey);
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
                    mSearchKey = mEditText.getText().toString();
                    mDetailAdapter.setSearchContent(mSearchKey);
                    mPageSearch = 1;
                    mPresenter.getPersonContactByQuery(mPageSearch, mSearchKey);
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
                    mDetailAdapter.setDatas(mTeacherInfos);
                    mDetailAdapter.setSearchContent("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onSuccessSearch(final List<PersonContact> menuDetails) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mListView.stopLoadMore();
                if (mPageSearch == 1) {//这边要通过返回的条数，和限制每一页返回的条数，大小来判断是否还可以加载更多
                    if (menuDetails != null && menuDetails.size() > 0) {
                        mDetailAdapter.setDatas(menuDetails);
                    }
                } else {
                    if (menuDetails != null && menuDetails.size() > 0) {
                        mDetailAdapter.addData(menuDetails);
                    }
                }
                if (menuDetails != null && menuDetails.size() > 0) {
                    if (menuDetails.size() >= HttpUrl.PAGE_SIZE) {
                        mListView.setLoadMoreEnable(true);
                    } else {
                        mListView.setLoadMoreEnable(false);
                    }
                } else {
                    mListView.setLoadMoreEnable(false);
                }
            }
        });
    }

    @Override
    public void onSuccess(final List<PersonContact> menuDetails) {

    }

    @Override
    public void onLoadMore() {
        mPageSearch++;
        mPresenter.getPersonContactByQuery(mPageSearch, mSearchKey);
    }

}
