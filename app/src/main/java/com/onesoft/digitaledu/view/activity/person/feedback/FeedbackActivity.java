package com.onesoft.digitaledu.view.activity.person.feedback;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.Feedback;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.person.FeedbackPresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.person.IFeedbackView;
import com.onesoft.digitaledu.widget.face.FaceRelativeLayout;
import com.onesoft.digitaledu.widget.ptr.PtrListView;
import com.yancy.gallerypick.utils.AppUtils;

import java.util.List;

/**
 * Created by Jayden on 2016/11/18.
 */
public class FeedbackActivity extends ToolBarActivity<FeedbackPresenter> implements PtrListView.OnRefreshListener, PtrListView.OnLoadMoreListener, IFeedbackView {

    private View mViewGoUp;
    private Button mBtnSend;
    private PtrListView mListView;
    private FeedbackAdapter mAdapter;
    private EditText mEditTextContent;
    private FaceRelativeLayout faceRelativeLayout;

    private Feedback mCurFeedbackToComment;//当前要被回复的意见反馈

    @Override
    protected void initPresenter() {
        mPresenter = new FeedbackPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_feedback, null);
    }

    @Override
    public void initView() {
        mListView = (PtrListView) findViewById(R.id.listview);
        mBtnSend = (Button) findViewById(R.id.btn_send);
        mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
        faceRelativeLayout = (FaceRelativeLayout) findViewById(R.id.FaceRelativeLayout);

        mViewGoUp = findViewById(R.id.btn_up);
        mViewGoUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListView.setSelection(0);
            }
        });
    }

    @Override
    public void initData() {
        setTitle(getString(R.string.feedback));
        mAdapter = new FeedbackAdapter(this);
        mAdapter.setFeedbackListener(new FeedbackAdapter.OnFeedbackListener() {
            @Override
            public void onComment(Feedback feedback) {
                mCurFeedbackToComment = feedback;
                mEditTextContent.requestFocus();
                //调出软键盘
                AppUtils.showSoftKeyBoard(FeedbackActivity.this, mEditTextContent);
            }

            @Override
            public void onDelete(Feedback feedback) {
                mPresenter.delete(feedback.id);
            }
        });
        mListView.setAdapter(mAdapter);
        mListView.refresh();
    }

    @Override
    public void initListener() {
        mListView.setOnRefreshListener(this);
        mListView.setOnLoadMoreListener(this);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                hideInputMethod();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 5) {
                    mViewGoUp.setVisibility(View.VISIBLE);
                } else {
                    mViewGoUp.setVisibility(View.INVISIBLE);
                }
            }
        });

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurFeedbackToComment == null) {
                    mPresenter.addFeedback("", mEditTextContent.getText().toString().trim());
                } else {
                    mPresenter.addFeedback(mCurFeedbackToComment.id, mEditTextContent.getText().toString().trim());
                }
            }
        });

        mViewGoUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListView.setSelection(1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideInputMethod();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideInputMethod();
    }

    private void hideInputMethod() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditTextContent.getWindowToken(), 0);
        faceRelativeLayout.hideFaceView();
    }

    @Override
    public void onSuccess(List<Feedback> boxBeanList) {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        if (page == 1) {//这边要通过返回的条数，和限制每一页返回的条数，大小来判断是否还可以加载更多
            if (boxBeanList != null && boxBeanList.size() > 0) {
                mAdapter.setDatas(boxBeanList);
                mPageStateLayout.onSucceed();
            } else {
                mPageStateLayout.onEmpty();
            }
        } else {
            if (boxBeanList != null && boxBeanList.size() > 0) {
                mAdapter.addData(boxBeanList);
            }
        }
        if (boxBeanList != null && boxBeanList.size() > 0) {
            if (boxBeanList.size() == HttpUrl.PAGE_SIZE) {
                mListView.setLoadMoreEnable(true);
            } else {
                mListView.setLoadMoreEnable(false);
            }
        } else {
            mListView.setLoadMoreEnable(false);
        }
    }

    @Override
    public void onDelSuccess() {
        onRefresh();
    }

    @Override
    public void onAddSuccess() {
        mEditTextContent.setText("");
        onRefresh();
    }

    private int page = 1;

    @Override
    public void onLoadMore() {
        page++;
        mPresenter.getDataFeedback(page);
    }

    @Override
    public void onRefresh() {
        page = 1;
        mCurFeedbackToComment = null;
        mPresenter.getDataFeedback(page);
    }
}
