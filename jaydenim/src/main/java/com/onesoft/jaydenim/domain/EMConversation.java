package com.onesoft.jaydenim.domain;

import com.onesoft.jaydenim.EMClient;

import java.util.List;

/**
 * 聊天列表
 * Created by Jayden on 2016/9/19.
 */

public class EMConversation {

    private String id;//聊天的对象id
    private String username;//聊天的对象昵称
    private int allMsgCount;
    private int unReadMsgCount;
    private int mConversationType;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    /**
     * 获取用户名
     *
     * @return
     */
    public String getUserName() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setConversationType(int conversationType) {
        mConversationType = conversationType;
    }

    public void setAllMsgCount(int allMsgCount) {
        this.allMsgCount = allMsgCount;
    }

    /**
     * 获取聊天类型
     *
     * @return
     */
    public int getType() {
        return mConversationType;
    }

    public void markAllMessagesAsRead() {
        unReadMsgCount = 0;
        EMClient.getInstance().chatManager().addConversation(this);
    }

    public void setUnReadMsgCount(int unReadMsgCount) {
        this.unReadMsgCount = unReadMsgCount;
    }

    public int getUnreadMsgCount() {
        return unReadMsgCount;
    }

    public int getAllMsgCount() {
        return allMsgCount;
    }

    /**
     * 添加消息
     * @param emMessage
     */
    public void addEMMessage(EMMessage emMessage) {
       EMClient.getInstance().chatManager().addMessage(emMessage);
    }

    /**
     * 获取所有聊天记录
     * @return
     */
    public synchronized List<EMMessage> getAllMessages() {
        return EMClient.getInstance().chatManager().getAllMessage(id);
    }

    /**
     * 分页获取消息
     * @param id
     * @param from
     * @return
     */
    public List<EMMessage> loadMoreMsgFromDB(String id, int from,int to) {
        return EMClient.getInstance().chatManager().getAllMessageByPage(id,from,to);
    }

    /**
     * 获取所有最后一条聊天记录
     * @return
     */
    public EMMessage getLastMessage() {
        return EMClient.getInstance().chatManager().getLastMessage(id);
    }

    private int isGroup;

    public void setGroup(int group) {
        isGroup = group;
    }

    public int isGroup() {
        return isGroup;
    }

    public static final int CHAT = 0;
    public static final int GROUPCHAT = 1;
    public static final int CHATROOM = 2;
    public static final int DISCUSSIONGROUP = 3;
    public static final int HELPDESK = 4;
}
