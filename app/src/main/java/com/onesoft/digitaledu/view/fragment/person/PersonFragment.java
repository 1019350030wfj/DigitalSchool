package com.onesoft.digitaledu.view.fragment.person;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.view.activity.person.CopyrightActivity;
import com.onesoft.digitaledu.view.activity.person.ModifyPwdActivity;
import com.onesoft.digitaledu.view.activity.person.OfflineDownloadActivity;
import com.onesoft.digitaledu.view.activity.person.PersonInfoActivity;
import com.onesoft.digitaledu.view.activity.person.wallpaper.WallPagerActivity;
import com.onesoft.digitaledu.view.fragment.BaseFragment;
import com.onesoft.digitaledu.widget.CircleImageView;
import com.onesoft.digitaledu.widget.dialog.FileDownloadDialog;

import org.wlf.filedownloader.FileDownloader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jayden on 2016/10/28.
 */

public class PersonFragment extends BaseFragment {

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
                        boxBeanList.add("http://mp4.28mtv.com/mp41/1862-刘德华-余生一起过[68mtv.com].mp4");
                        boxBeanList.add("http://img13.360buyimg.com/n1/g14/M01/1B/1F/rBEhVlM03iwIAAAAAAFJnWsj5UAAAK8_gKFgkMAAUm1950.jpg");
                        boxBeanList.add("http://sqdd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk");
                        boxBeanList.add("http://down.sandai.net/thunder7/Thunder_dl_7.9.41.5020.exe");
                        boxBeanList.add("http://182.254.149.157/ftp/image/shop/product/@#_% &.apk");
                        boxBeanList.add("http://dx500.downyouxi.com/minglingyuzhengfu4.rar");
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

            }
        });
        mRLVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//版本更新
                startActivity(new Intent(getActivity(), CopyrightActivity.class));
            }
        });
    }

    @Override
    protected void initPresenter() {

    }
}
