package com.onesoft.digitaledu.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.view.activity.person.PersonInfoActivity;
import com.onesoft.digitaledu.view.fragment.cloud.CloudFragment;
import com.onesoft.digitaledu.view.fragment.home.HomeFragment;
import com.onesoft.digitaledu.view.fragment.message.MessageFragment;
import com.onesoft.digitaledu.view.fragment.person.PersonFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MainActivity extends BaseFragmentActivity implements View.OnClickListener {

    private static final String SELECT_POS = "selected_pos";

    private HomeFragment mHomeFragment;
    private MessageFragment mMessageFragment;
    private CloudFragment mCloudFragment;
    private PersonFragment mPersonFragment;

    // 我的
    private ImageView mIVPerson;
    private TextView mTVPerson;

    // 云平台
    private ImageView mIVShop;
    private TextView mTVShop;

    // 消息
    private ImageView mIVGroup;
    private TextView mTVGroup;

    // 首页
    private ImageView mIVIndex;
    private TextView mTVIndex;
    private TextView mTvAll;

    private View mLLAllBottom;
    private View mLLMessageBottom;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_home);
        setFragmentViewId(R.id.content);
        mIVIndex = findImage(R.id.iv_index);
        mTVIndex = findText(R.id.tv_index);
        setOnClickListenerEvent(R.id.ll_index);
        mIVGroup = findImage(R.id.iv_group);
        mTVGroup = findText(R.id.tv_group);
        setOnClickListenerEvent(R.id.ll_group);
        mIVShop = findImage(R.id.iv_shop);
        mTVShop = findText(R.id.tv_shop);
        setOnClickListenerEvent(R.id.ll_shop);
        mIVPerson = findImage(R.id.iv_person);
        mTVPerson = findText(R.id.tv_person);
        setOnClickListenerEvent(R.id.ll_person);

        mTvAll = (TextView) findViewById(R.id.tv_all);
        mLLAllBottom = findViewById(R.id.ll_all_bottom);
        mLLMessageBottom = findViewById(R.id.ll_message_bottom);

        findViewById(R.id.ll_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessageFragment.delete();
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
                mMessageFragment.setSelectAll(isSelectAll);
            }
        });

        EventBus.getDefault().register(this);
        normalState();
    }

    private boolean isSelectAll = false;

    public void deleteState() {
        translateToShow(mLLMessageBottom);
        translateToHide(mLLAllBottom);
        isSelectAll = false;
    }

    public void normalState() {
        translateToShow(mLLAllBottom);
        translateToHide(mLLMessageBottom);
    }

    private void translateToShow(View view) {
        AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.show_set);
        view.startAnimation(animationSet);
        view.setVisibility(View.VISIBLE);
    }

    private void translateToHide(View view) {
        AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.hide_set);
        view.startAnimation(animationSet);
        view.setVisibility(View.GONE);

    }

    private void setOnClickListenerEvent(int id) {
        findViewById(id).setOnClickListener(this);
    }

    private TextView findText(int id) {
        return (TextView) findViewById(id);
    }

    private ImageView findImage(int id) {
        return (ImageView) findViewById(id);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mHomeFragment = (HomeFragment) findFragmentByTag(HomeFragment.class.getSimpleName());
            mMessageFragment = (MessageFragment) findFragmentByTag(MessageFragment.class.getSimpleName());
            mCloudFragment = (CloudFragment) findFragmentByTag(CloudFragment.class.getSimpleName());
            mPersonFragment = (PersonFragment) findFragmentByTag(PersonFragment.class.getSimpleName());
            selected_pos = savedInstanceState.getInt(SELECT_POS);
        }
        if (mHomeFragment == null) {
            mHomeFragment = new HomeFragment();
        }
        if (mMessageFragment == null) {
            mMessageFragment = new MessageFragment();
        }
        if (mCloudFragment == null) {
            mCloudFragment = new CloudFragment();
        }
        if (mPersonFragment == null) {
            mPersonFragment = new PersonFragment();
        }

        if (selected_pos == 0) {
            selected_pos = R.id.ll_index;
        }
        onClick(findViewById(selected_pos));
    }

    private int selected_pos = 0;

    @Override
    public void onClick(View view) {
        resetStatus();
        selected_pos = view.getId();
        chooseStatus(selected_pos);
    }

    private void resetStatus() {
        mIVIndex.setImageDrawable(getResources().getDrawable(R.drawable.ic_index_unsel));
        mTVIndex.setTextColor(getResources().getColor(R.color.color_tab_unselected));
        mIVGroup.setImageDrawable(getResources().getDrawable(R.drawable.ic_group_unsel));
        mTVGroup.setTextColor(getResources().getColor(R.color.color_tab_unselected));
        mIVShop.setImageDrawable(getResources().getDrawable(R.drawable.ic_shop_unsel));
        mTVShop.setTextColor(getResources().getColor(R.color.color_tab_unselected));
        mIVPerson.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_unsel));
        mTVPerson.setTextColor(getResources().getColor(R.color.color_tab_unselected));
    }

    private void chooseStatus(int id) {
        switch (id) {
            case R.id.ll_index:
                mIVIndex.setImageDrawable(getResources().getDrawable(R.drawable.ic_index_sel));
                mTVIndex.setTextColor(getResources().getColor(R.color.color_tab_selected));
                changeFragment(mHomeFragment, HomeFragment.class.getSimpleName());
                break;
            case R.id.ll_group:
                mIVGroup.setImageDrawable(getResources().getDrawable(R.drawable.ic_group_sel));
                mTVGroup.setTextColor(getResources().getColor(R.color.color_tab_selected));
                changeFragment(mMessageFragment, MessageFragment.class.getSimpleName());
                break;
            case R.id.ll_shop:
                mIVShop.setImageDrawable(getResources().getDrawable(R.drawable.ic_shop_sel));
                mTVShop.setTextColor(getResources().getColor(R.color.color_tab_selected));
                break;
            case R.id.ll_person:
                mIVPerson.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_sel));
                mTVPerson.setTextColor(getResources().getColor(R.color.color_tab_selected));
                changeFragment(mPersonFragment, PersonFragment.class.getSimpleName());
                break;
        }
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
            case BaseEvent.TURN_TO_MESSAGE: {//消息
                onClick(findViewById(R.id.ll_group));
                break;
            }
            case BaseEvent.TURN_TO_MINE: {//我的
                onClick(findViewById(R.id.ll_person));
                startActivity(new Intent(this, PersonInfoActivity.class));
                break;
            }
        }
    }
}