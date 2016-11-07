package com.onesoft.jaydenim.model;

import android.content.Context;

import com.onesoft.jaydenim.EMClient;
import com.onesoft.jaydenim.database.UserDao;
import com.onesoft.jaydenim.domain.EaseUser;
import com.onesoft.jaydenim.presenter.ContactListPresenter;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 更新内存数据
 * 然后更新数据库数据
 * Created by Jayden on 2016/9/22.
 */

public class EMContactManager {

    private Context mContext;

    public EMContactManager(Context context) {
        this.mContext = context;
    }

    /**
     * 获取黑名单列表
     * @return
     */
    public List<String> getBlackListUsernames() {
        return new ArrayList<>();
    }

    /**
     * 是否已经包含有该好友
     * @param s
     * @return
     */
    public boolean containsKey(String s) {
        return getContactList().containsKey(s);
    }

    private Map<String, EaseUser> contactList;
    /**
     * get contact list
     * 获取好友列表
     * @return
     */
    public Map<String, EaseUser> getContactList() {
        if (contactList == null) {
            UserDao dao = new UserDao(mContext);
            contactList = dao.getContactList();
        }

        // return a empty non-null object to avoid app crash
        if(contactList == null){
            return new Hashtable<String, EaseUser>();
        }
        return contactList;
    }

    /**
     * 添加好友
     * @param user
     */
    public void saveContact(EaseUser user) {
        getContactList().put(user.user_id,user);
        UserDao dao = new UserDao(mContext);
        dao.saveContact(user);
    }

    public void saveContactList(Map<String, EaseUser> userlist) {
       getContactList().putAll(userlist);
        // save the contact list to database
        UserDao dao = new UserDao(mContext);
        List<EaseUser> users = new ArrayList<EaseUser>(userlist.values());
        dao.saveContactList(users);
    }

    /**
     * 删除联系人
     * @param username
     */
    public void deleteContact(String username){
        getContactList().remove(username);//联系人
        UserDao dao = new UserDao(mContext);
        dao.deleteContact(username);
        EMClient.getInstance().chatManager().deleteMessageByConversationId(username);//聊天记录
        //删除会话记录
        EMClient.getInstance().chatManager().deleteConversation(username);
    }

    public void refreshList(String username) {
//        EMClient.getInstance().chatManager().deleteMessageByConversationId(username);//聊天记录
//        //删除会话记录
//        EMClient.getInstance().chatManager().deleteConversation(username);
        ContactListPresenter presenter = new ContactListPresenter();
        presenter.getContactList(mContext, EMClient.getInstance().getCurrentUser());
    }
}
