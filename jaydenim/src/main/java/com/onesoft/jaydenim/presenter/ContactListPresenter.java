package com.onesoft.jaydenim.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.jaydenim.EMClient;
import com.onesoft.jaydenim.EaseUI;
import com.onesoft.jaydenim.domain.EaseUser;
import com.onesoft.jaydenim.http.HttpHandler;
import com.onesoft.jaydenim.http.HttpUrl;
import com.onesoft.jaydenim.model.BaseBean;
import com.onesoft.jaydenim.utils.EaseCommonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * 联系人列表
 * Created by Jayden on 2016/9/28.
 */

public class ContactListPresenter {

    public ContactListPresenter() {

    }

    public void getContactList(Context context, final String userId) {
        String url = HttpUrl.FRIEND_LIST + userId;
        HttpHandler.getInstance(context).getAsync(context, url, new HttpHandler.ResultCallback<BaseBean<EaseUser>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseBean<EaseUser> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    asyncContactInfo(userId, response.info);
                }
            }
        });
    }

    /**
     * 异步更新联系人列表
     *
     * @param bean
     */
    private void asyncContactInfo(final String userId, final List<EaseUser> bean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, EaseUser> userlist = new HashMap<String, EaseUser>();
                for (EaseUser friend : bean) {
                    EaseCommonUtils.setUserInitialLetter(friend);
                    userlist.put(friend.user_id, friend);
                }
                // save the contact list to cache
                EaseUser myinfo = EMClient.getInstance().getContactList().get(userId);
                EaseUser myselft = new EaseUser(myinfo.user_id);
                myselft.setNick(myinfo.real_name);
                myselft.user_name = myinfo.user_name;
                myselft.sex = myinfo.sex;
                myselft.user_type = myinfo.user_type;
                myselft.photo = myinfo.photo;
                myselft.mobilephone = myinfo.mobilephone;
                myselft.province = myinfo.province;
                EaseCommonUtils.setUserInitialLetter(myselft);
                userlist.put(myinfo.user_id, myselft);
                EMClient.getInstance().getContactList().clear();
                EMClient.getInstance().contactManager().saveContactList(userlist);
                EMClient.getInstance().contactManager().saveContact(myselft);
            }
        }).start();
    }


    public void asyncContactInfo(final EaseUser myself, final Map<String, EaseUser> userList) {
        EaseUI.getInstance().getContactList().clear();
        EaseUI.getInstance().saveContactList(userList);
        if (myself != null) {
            EaseUI.getInstance().saveContact(myself);
        }
    }
}
