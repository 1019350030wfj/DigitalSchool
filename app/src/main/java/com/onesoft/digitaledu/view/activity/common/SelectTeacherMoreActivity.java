package com.onesoft.digitaledu.view.activity.common;

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
import com.onesoft.digitaledu.model.TeacherInfo;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.common.TeacherPresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.adapter.SelectTeacherMoreAdapter;
import com.onesoft.digitaledu.view.iview.common.ISelectTeacherView;
import com.onesoft.digitaledu.view.iview.template.IAddView;
import com.onesoft.digitaledu.widget.ptr.PtrListView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 教师列表
 * Created by Jayden on 2016/11/23.
 */

public class SelectTeacherMoreActivity extends ToolBarActivity<TeacherPresenter> implements ISelectTeacherView, PtrListView.OnLoadMoreListener {

    private String mGroupId;

    public static void startSelectMoreTeacher(Context context,String groupId){
        Intent intent = new Intent(context,SelectTeacherMoreActivity.class);
        intent.putExtra("groupId",groupId);
        context.startActivity(intent);
    }

    private void getDataFromForward(){
        if (getIntent() != null && getIntent().getExtras() != null){
            mGroupId = getIntent().getExtras().getString("groupId");
        }
    }

    private PtrListView mListView;
    private ImageButton mUpBtn;
    private SelectTeacherMoreAdapter mDetailAdapter;

    private EditText mEditText;
    private int mPage = 1;
    private int mPageSearch = 1;
    private boolean isSearchMode = false;
    private String mSearchKey = "";
    private List<TeacherInfo> mTeacherInfos = new ArrayList<>();

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new TeacherPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_select_teacher_more, null);
    }

    @Override
    public void initView() {
        mUpBtn = (ImageButton) findViewById(R.id.btn_up);
        mListView = (PtrListView) findViewById(R.id.listview);

        mEditText = (EditText) findViewById(R.id.edt_search);
    }

    @Override
    public void initData() {
        setTitle(getString(R.string.teacher_list));
        mDetailAdapter = new SelectTeacherMoreAdapter(this);
        mListView.setAdapter(mDetailAdapter);
        mPresenter.getTeacherList(1);
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

        findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//确定，添加群组成员
                AddTeacherToGroupHelper addTeacherToGroupHelper = new AddTeacherToGroupHelper();
                StringBuilder idBuilder = new StringBuilder();
                for (TeacherInfo item : mDetailAdapter.getDatas()) {
                    if (item.isSelect) {
                        idBuilder.append(item.id).append(",");
                    }
                }
                if (idBuilder.length() > 0) {
                    idBuilder.deleteCharAt(idBuilder.length() - 1);
                }

                addTeacherToGroupHelper.addGroupUser(SelectTeacherMoreActivity.this, mGroupId, idBuilder.toString(), new IAddView() {
                    @Override
                    public void onAddSuccess() {
                        EventBus.getDefault().post(new BaseEvent(BaseEvent.UPDATE_GROUP_CONTACT, ""));
                        finish();
                    }
                });
            }
        });
        findViewById(R.id.img_search_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText("");
                isSearchMode = false;
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
                        isSearchMode = true;
                        mPresenter.getTeacherListByQuery(mPageSearch, mSearchKey);
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
                    isSearchMode = true;
                    mPresenter.getTeacherListByQuery(mPageSearch, mSearchKey);
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
                    isSearchMode = false;
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
    public void onSuccessSearch(final List<TeacherInfo> menuDetails) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mListView.stopLoadMore();
                if (mPageSearch == 1) {//这边要通过返回的条数，和限制每一页返回的条数，大小来判断是否还可以加载更多
                    if (menuDetails != null && menuDetails.size() > 0) {
                        mDetailAdapter.setDatas(menuDetails);
                        mPageStateLayout.onSucceed();
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
    public void onSuccess(final List<TeacherInfo> menuDetails) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mListView.stopLoadMore();
                if (mPage == 1) {//这边要通过返回的条数，和限制每一页返回的条数，大小来判断是否还可以加载更多
                    mTeacherInfos.clear();
                    if (menuDetails != null && menuDetails.size() > 0) {
                        mTeacherInfos.addAll(menuDetails);
                        mDetailAdapter.setDatas(mTeacherInfos);
                        mPageStateLayout.onSucceed();
                    }
                } else {
                    if (menuDetails != null && menuDetails.size() > 0) {
                        mTeacherInfos.addAll(menuDetails);
                        mDetailAdapter.setDatas(mTeacherInfos);
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
    public void onLoadMore() {
        if (isSearchMode) {
            mPageSearch++;
            mPresenter.getTeacherListByQuery(mPageSearch, mSearchKey);
            return;
        }
        mPage++;
        mPresenter.getTeacherList(mPage);
    }

}
