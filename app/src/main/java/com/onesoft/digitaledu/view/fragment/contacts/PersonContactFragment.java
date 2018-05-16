package com.onesoft.digitaledu.view.fragment.contacts;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.model.PersonContact;
import com.onesoft.digitaledu.presenter.infomanager.contacts.PersonContactPresenter;
import com.onesoft.digitaledu.view.activity.infomanager.contacts.ContactPersonInfoActivity;
import com.onesoft.digitaledu.view.fragment.BaseFragment;
import com.onesoft.digitaledu.view.iview.infomanager.contacts.IPersonContactView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 个人通讯录
 * Created by Jayden on 2016/11/5.
 */

public class PersonContactFragment extends BaseFragment<PersonContactPresenter> implements IPersonContactView {

    private ListView mListView;
    private PersonContactAdapter mBoxAdapter;

    public static PersonContactFragment newInstance(String s) {
        PersonContactFragment newFragment = new PersonContactFragment();
        Bundle bundle = new Bundle();
        bundle.putString("hello", s);
        newFragment.setArguments(bundle);
        //bundle还可以在每个标签里传送数据
        return newFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_listview;
    }

    @Override
    public void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.listview);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mBoxAdapter = new PersonContactAdapter(getActivity());
        mListView.setAdapter(mBoxAdapter);
        mPresenter.getPersonContacts();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new PersonContactPresenter(getActivity(), this);
    }

    @Override
    public void onSuccess(List<PersonContact> personContacts) {
        mBoxAdapter.setDatas(personContacts);
        mPageStateLayout.onSucceed();
    }

    @Override
    public void onSuccessSearch(List<PersonContact> info) {

    }

    @Override
    public void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactPersonInfoActivity.startContactPersonInfo(mBoxAdapter.getItem(position), getActivity());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
            case BaseEvent.UPDATE_PERSON_CONTACT: {//成功
                mPresenter.getPersonContacts();//刷新列表
                break;
            }
        }
    }
}
