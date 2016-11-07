package com.onesoft.jaydenim.model;

import android.content.Context;

import com.onesoft.jaydenim.database.InviteMessgeDao;

/**
 * 消息相关管理类
 * Created by Jayden on 2016/9/29.
 */

public class EMMessageManager {

    private Context mContext;
    public EMMessageManager(Context context) {
        this.mContext = context;
    }

    public int getFriendRequestCount(){
        InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(mContext);
        return inviteMessgeDao.getUnreadMessagesCount();
    }

    public void saveFriendRequestCount(int count){
        InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(mContext);
        inviteMessgeDao.saveUnreadMessageCount(count + getFriendRequestCount());
    }
}
