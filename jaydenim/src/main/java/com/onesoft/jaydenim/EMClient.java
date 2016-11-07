package com.onesoft.jaydenim;

import android.content.Context;
import android.content.Intent;

import com.onesoft.jaydenim.database.DemoDBManager;
import com.onesoft.jaydenim.domain.EaseUser;
import com.onesoft.jaydenim.imaction.SendAction;
import com.onesoft.jaydenim.model.EMChatManager;
import com.onesoft.jaydenim.model.EMContactManager;
import com.onesoft.jaydenim.model.EMMessageManager;
import com.onesoft.jaydenim.network.NetService;
import com.onesoft.jaydenim.service.IMService;
import com.onesoft.jaydenim.utils.PathUtil;

import java.util.Map;

/**
 * Created by Jayden on 2016/9/19.
 */

public class EMClient {

    private static EMClient instance;

    private EMContactManager contactManager;
    private EMMessageManager messageManager;
    private EMChatManager chatManager;
    private NetService mNetService;
    private String mCurrentUser;
    private String mFriendUids;
    private Intent mIMService;
    private Context mContext;

    private EMClient() {
        mNetService = NetService.getInstance();
    }

    public static EMClient getInstance() {
        if (instance == null) {
            synchronized (EMClient.class) {
                if (instance == null) {
                    instance = new EMClient();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        this.mContext = context;
        mNetService.onInit(context);
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    public String getCurrentUser() {
        return mCurrentUser;
    }

    public EMChatManager chatManager() {
        if (this.chatManager == null) {
            this.chatManager = new EMChatManager(mContext);
        }

        return this.chatManager;
    }

    public String getFriendUids() {
        return mFriendUids;
    }

    /**
     * 点击登录或者自动登录
     * 后台开一个服务，然后开启socket
     * @param context
     */
    public void startService(Context context) {
        mIMService = new Intent(context, IMService.class);
        context.startService(mIMService);
    }

    private String mCookie;

    public void setCookie(String cookie) {
        mCookie = cookie;
    }

    public String getCookie() {
        return mCookie;
    }

    /**
     * denglu登录php服务器成功后，登录c++（聊天）服务器
     *
     * @param userId
     * @param uidFriends
     * @param context
     */
    public void login(String userId, String uidFriends, final Context context) {
        this.mCurrentUser = userId;
        this.mFriendUids = uidFriends;
        PathUtil.getInstance().initDirs(null, userId, context);

        SendAction.getInstance().sendLogin(userId);
    }

    /*====================IM Service interface===================*/

    public void sendMessage(byte[] content) {
        mNetService.send(content);
    }

    public void logout() {
        if (mContext != null && mIMService != null){
            mContext.stopService(mIMService);
        }
        if (mNetService != null){
            mNetService.closeConnection();
        }
    }

    public boolean isConnect() {
        return mNetService != null && mNetService.isConnected();
    }

    public void closeConnection() {
        logout();
        EMClient.getInstance().chatManager().logout();
        DemoDBManager.getInstance().closeDB();
    }

    public EMContactManager contactManager() {
        if (contactManager == null) {
            contactManager = new EMContactManager(mContext);
        }
        return contactManager;
    }

    public EMMessageManager messageManager() {
        if (messageManager == null) {
            messageManager = new EMMessageManager(mContext);
        }
        return messageManager;
    }

    /**
     * get contact list
     * 获取好友列表
     *
     * @return
     */
    public Map<String, EaseUser> getContactList() {
        return contactManager().getContactList();
    }
     /*====================IM Service interface===================*/
}
