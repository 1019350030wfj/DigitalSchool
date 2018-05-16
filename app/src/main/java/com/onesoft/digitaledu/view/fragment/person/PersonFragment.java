package com.onesoft.digitaledu.view.fragment.person;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.PersonEvent;
import com.onesoft.digitaledu.model.PersonInfo;
import com.onesoft.digitaledu.presenter.person.PersonInfoPresenter;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.activity.login.LoginActivity;
import com.onesoft.digitaledu.view.activity.person.CopyrightActivity;
import com.onesoft.digitaledu.view.activity.person.ModifyPwdActivity;
import com.onesoft.digitaledu.view.activity.person.OfflineDownloadActivity;
import com.onesoft.digitaledu.view.activity.person.PersonInfoActivity;
import com.onesoft.digitaledu.view.activity.person.feedback.FeedbackActivity;
import com.onesoft.digitaledu.view.activity.person.wallpaper.WallPagerActivity;
import com.onesoft.digitaledu.view.fragment.BaseFragment;
import com.onesoft.digitaledu.view.iview.person.IPersonInfoView;
import com.onesoft.digitaledu.widget.CircleImageView;
import com.onesoft.digitaledu.widget.dialog.FileDownloadDialog;
import com.onesoft.netlibrary.utils.ImageHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.wlf.filedownloader.FileDownloader;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的页面
 * Created by Jayden on 2016/10/28.
 */

public class PersonFragment extends BaseFragment<PersonInfoPresenter> implements IPersonInfoView {

    private CircleImageView mIvAvater;
    private TextView mTvUserName;
    private TextView mTvUserNumber;
    private TextView mTvUserCareer;
    private TextView mTvUserDeparment;
    private TextView mTvWallPaer;
    private TextView mTvVersion;

    private View mLLPerson;
    private View mLLPeriod;
    private View mRLOfflineDownload;
    private View mRLPwd;
    private View mRLWallpaper;
    private View mRLFeedback;
    private View mRLVersion;
    private View mBtnExit;

    private PersonInfo mPersonInfo;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_person;
    }

    @Override
    public void initView(View view) {
        mIvAvater = (CircleImageView) view.findViewById(R.id.iv_avater);
        mTvUserName = (TextView) view.findViewById(R.id.txt_name);
        mTvUserNumber = (TextView) view.findViewById(R.id.txt_number);
        mTvUserCareer = (TextView) view.findViewById(R.id.txt_career);
        mTvUserDeparment = (TextView) view.findViewById(R.id.txt_department);
        mTvWallPaer = (TextView) view.findViewById(R.id.txt_wallpaper);
        mTvVersion = (TextView) view.findViewById(R.id.txt_version);

        mLLPerson = view.findViewById(R.id.ll_person_info);
        mLLPeriod = view.findViewById(R.id.ll_person_period);
        mRLOfflineDownload = view.findViewById(R.id.rl_offline_down);
        mRLPwd = view.findViewById(R.id.rl_modify_pwd);
        mRLWallpaper = view.findViewById(R.id.rl_wallpaper);
        mRLFeedback = view.findViewById(R.id.rl_feedback);
        mRLVersion = view.findViewById(R.id.rl_update);
        mBtnExit = view.findViewById(R.id.btn_exit);
    }

    @Override
    public void initListener() {
        mLLPeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileDownloadDialog dialog = new FileDownloadDialog(getActivity());
                dialog.show();
                dialog.setFileDownloadListener(new FileDownloadDialog.IFileDownload() {
                    @Override
                    public void onConfirm() {
                        List<String> boxBeanList = new ArrayList<>();
                        boxBeanList.add(SPHelper.getCourseTable(getActivity()));//下载当前学期课表
                        FileDownloader.start(boxBeanList);
                    }
                });
            }
        });
        mRLOfflineDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//跳转到下载离线页面
                OfflineDownloadActivity.startOfflineActivity(getActivity());
            }
        });
        mRLPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//跳转到修改密码
                startActivity(new Intent(getActivity(), ModifyPwdActivity.class));
            }
        });
        mLLPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//跳转到个人信息
                startActivity(new Intent(getActivity(), PersonInfoActivity.class));
            }
        });
        mRLWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//跳转到壁纸设置
                startActivity(new Intent(getActivity(), WallPagerActivity.class));
            }
        });
        mRLFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//跳转到意见反馈
                startActivity(new Intent(getActivity(), FeedbackActivity.class));
            }
        });
        mRLVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//版本更新
                startActivity(new Intent(getActivity(), CopyrightActivity.class));
            }
        });

        mBtnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
    }

    @Override
    protected void initPresenter() {
        mPresenter = new PersonInfoPresenter(getActivity(), this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter.getPersonInfo();
        mPageStateLayout.onSucceed();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onSuccess(PersonInfo personInfo) {
        mPersonInfo = personInfo;
        //更新数据
        ImageHandler.getAvater(getActivity(), mIvAvater, personInfo.photo);
        mTvUserName.setText(personInfo.real_name);
        mTvUserNumber.setText(personInfo.user_name);
        if ("2".equals(personInfo.user_type)) {
            mTvUserCareer.setVisibility(View.GONE);
        } else {
            mTvUserCareer.setText(personInfo.jobtitle);
        }
        if ("1".equals(personInfo.sex)) {//男
            Drawable nav_up = getResources().getDrawable(R.drawable.icon_my_men);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            mTvUserName.setCompoundDrawables(null, null, nav_up, null);
        } else {
            Drawable nav_up = getResources().getDrawable(R.drawable.icon_my_woman);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            mTvUserName.setCompoundDrawables(null, null, nav_up, null);
        }
        mTvUserDeparment.setText(personInfo.depart_name);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBaseEvent(final PersonEvent event) {
        mPersonInfo = event.data;
        ImageHandler.getAvater(getActivity(), mIvAvater, mPersonInfo.photo);
    }
}
