package com.onesoft.digitaledu.view.activity.twolevel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.model.ListBean;
import com.onesoft.digitaledu.model.TopDirectory;
import com.onesoft.digitaledu.presenter.twolevel.TwoLevelPresenter;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.utils.Utils;
import com.onesoft.digitaledu.utils.ViewUtil;
import com.onesoft.digitaledu.view.activity.ThirdMenuActivity;
import com.onesoft.digitaledu.view.activity.ThirdToMainListActivity;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.activity.infomanager.attendancelog.AttendanceLogActivity;
import com.onesoft.digitaledu.view.activity.infomanager.contacts.ContactActivity;
import com.onesoft.digitaledu.view.activity.infomanager.docsendrec.DocumentSendReceiveActivity;
import com.onesoft.digitaledu.view.fragment.home.TopDirectoryAdapter;
import com.onesoft.digitaledu.view.iview.twolevel.ITwoLevelView;
import com.onesoft.digitaledu.widget.calendar.CalendarNotepadActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jayden on 2016/10/29.
 */

public class TwoLevelActivity extends ToolBarActivity<TwoLevelPresenter> implements ITwoLevelView {

    public static final String PARAM = "params";

    public static void startTwoLevelActivity(Context context, List<TopDirectory> directories, int position) {
        Intent intent = new Intent(context, TwoLevelActivity.class);
        intent.putExtra(PARAM, (Serializable) directories);
        intent.putExtra("pos", position);
        context.startActivity(intent);
    }

    private List<TopDirectory> mTopDirectory;
    private int mSelectPos;

    private Map<String, List<TopDirectory>> mCacheTwoLevel = new HashMap<>();

    private void getDataFromForward() {
        mTopDirectory = (List<TopDirectory>) getIntent().getExtras().getSerializable(PARAM);
        mSelectPos = getIntent().getExtras().getInt("pos");
    }

    private View mLLView;
    private ListView mListView;
    private GridView mGridView;
    private TwoLevelTitleAdapter mTitleAdapter;
    private TopDirectoryAdapter mTopDirectoryAdapter;

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new TwoLevelPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_two_level, null);
    }

    @Override
    public void initView() {
        mLLView = findViewById(R.id.ll_two_level);
        mListView = (ListView) findViewById(R.id.listview);
        mGridView = (GridView) findViewById(R.id.gridView);
        mTitleAdapter = new TwoLevelTitleAdapter(this);
        mTopDirectoryAdapter = new TopDirectoryAdapter(this);
        mListView.setAdapter(mTitleAdapter);
        mGridView.setAdapter(mTopDirectoryAdapter);

        mLLView.setMinimumHeight(Utils.getDisplayHeigth(this));
    }

    @Override
    public void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setTitle(mTitleAdapter.getDatas().get(position).name);
                mTitleAdapter.setSelectId(mTitleAdapter.getDatas().get(position).id);
                //请求数据
                if (mCacheTwoLevel.containsKey(mTitleAdapter.getDatas().get(position).id)) {
                    mTopDirectoryAdapter.setDatas(mCacheTwoLevel.get(mTitleAdapter.getDatas().get(position).id));
                } else {//内存缓存
                    mPresenter.getTwoLevelTitle(mTitleAdapter.getDatas().get(position).id);
                }
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TopDirectory topDirectory = mTopDirectoryAdapter.getItem(position);
                if ("126".equals(topDirectory.id)) {//个人信息
                    EventBus.getDefault().post(new BaseEvent(BaseEvent.TURN_TO_MINE, ""));
                    finish();
                } else if ("55".equals(topDirectory.id)) {//消息中心
                    EventBus.getDefault().post(new BaseEvent(BaseEvent.TURN_TO_MESSAGE, ""));
                    finish();
                } else if ("789".equals(topDirectory.id)) {//我的考勤日志
                    startActivity(new Intent(TwoLevelActivity.this, AttendanceLogActivity.class));
                } else if ("726".equals(topDirectory.id)) {//公文收发
                    startActivity(new Intent(TwoLevelActivity.this, DocumentSendReceiveActivity.class));
                } else if ("95".equals(topDirectory.id)) {//通讯录
                    startActivity(new Intent(TwoLevelActivity.this, ContactActivity.class));
                } else if ("514".equals(topDirectory.id)) {//记事日历
                    startActivity(new Intent(TwoLevelActivity.this, CalendarNotepadActivity.class));
                } else if ("1".equals(topDirectory.isLeaf)) {
                    ThirdMenuActivity.startThirdMenu(TwoLevelActivity.this, topDirectory);
                } else {//没有三级菜单
                    ListBean listBean = new ListBean(topDirectory.id, topDirectory.name);
                    ThirdToMainListActivity.startThirdToMainList(TwoLevelActivity.this, listBean);
                }
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        setTitle(mTopDirectory.get(mSelectPos).name);
        //设置壁纸
        ViewUtil.setBackGround(mLLView, SPHelper.getWallPaperPosition(this));

        mTitleAdapter.setDatas(mTopDirectory);
        mTitleAdapter.setSelectId(mTopDirectory.get(mSelectPos).id);
        mListView.setSelection(mSelectPos);

        //请求数据
        mPresenter.getTwoLevelTitle(mTopDirectory.get(mSelectPos).id);
        mPageStateLayout.onSucceed();
    }

    @Override
    public void onGetTwoLevel(String id, List<TopDirectory> twoLevelTitles) {
        mCacheTwoLevel.put(id, twoLevelTitles);//内存缓存
        mTopDirectoryAdapter.setDatas(twoLevelTitles);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mCacheTwoLevel != null) {
            mCacheTwoLevel.clear();
            mCacheTwoLevel = null;
        }
    }
}
