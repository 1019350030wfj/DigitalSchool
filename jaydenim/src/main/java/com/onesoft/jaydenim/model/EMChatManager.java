package com.onesoft.jaydenim.model;

import android.content.Context;

import com.onesoft.jaydenim.EMCallBack;
import com.onesoft.jaydenim.database.ConversationDao;
import com.onesoft.jaydenim.database.LastMessgeDao;
import com.onesoft.jaydenim.database.MessgeDao;
import com.onesoft.jaydenim.domain.EMConversation;
import com.onesoft.jaydenim.domain.EMMessage;
import com.onesoft.jaydenim.utils.FileUtils;
import com.onesoft.jaydenim.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by Jayden on 2016/9/19.
 */
public class EMChatManager {
    private Context mContext;

    public EMChatManager(Context context) {
        this.mContext = context;
    }

    /*====================== 会话 =============================*/
    private Map<String, EMConversation> allConversations;//所有会话

    /**
     * 根据聊天的对象和类型，获取会话
     *
     * @param username
     * @return
     */
    public EMConversation getConversation(String username) {
        return getAllConversations() == null ? null : getAllConversations().get(username);
    }

    /**
     * 获取所有会话
     * 这边先从内存中获取，如果没有再从数据库获取
     *
     * @return
     */
    public Map<String, EMConversation> getAllConversations() {
        if (allConversations == null || allConversations.size() == 0) {
            ConversationDao conversationDao = new ConversationDao(mContext);
            allConversations = conversationDao.getConversationList();
        }
        if (allConversations == null) {
            return new Hashtable<String, EMConversation>();
        }
        return allConversations;
    }

    public void logout() {
        if (allConversations != null){
            allConversations.clear();
            allConversations = null;
        }
        if (allMessage != null){
            allMessage.clear();
            allMessage = null;
        }
        if (allLastMessage != null){
            allLastMessage.clear();
            allLastMessage = null;
        }
    }

    /**
     * 添加会话
     *
     * @param conversation
     */
    public void addConversation(EMConversation conversation) {
        getAllConversations().put(conversation.getId(), conversation);
        ConversationDao conversationDao = new ConversationDao(mContext);
        conversationDao.saveConversation(conversation);
    }

    public void deleteConversation(String id) {
        getAllConversations().remove(id);
        ConversationDao conversationDao = new ConversationDao(mContext);
        conversationDao.deleteConversation(id);
    }
    /*====================== 会话 =============================*/

    /*====================== 最后一条消息 =============================*/
    private Map<String, EMMessage> allLastMessage;//所有最后一条消息

    public Map<String, EMMessage> getAllLastMessage() {
        if (allLastMessage == null || allLastMessage.size() == 0) {
            LastMessgeDao conversationDao = new LastMessgeDao(mContext);
            allLastMessage = conversationDao.getLastEMMessageList();
        }
        if (allLastMessage == null) {
            return new Hashtable<String, EMMessage>();
        }
        return allLastMessage;
    }

    public EMMessage getLastMessage(String id) {
        return getAllLastMessage().get(id);
    }

    public void updateLastMessage(EMMessage emMessage) {
        //更新内存
        getAllLastMessage().put(emMessage.getConversationId(), emMessage);
        //更新数据库
        LastMessgeDao conversationDao = new LastMessgeDao(mContext);
        conversationDao.saveMessage(emMessage);
    }

    /*====================== 最后一条消息=============================*/

    /*====================== 新消息产生 =============================*/
    public interface INewMessageListener{//有新消息来，通知上层
        void onNewMessage(EMMessage emMessage);
    }

    private INewMessageListener mMessageListener;

    public void setINewMessageListener(INewMessageListener messageListener) {
        mMessageListener = messageListener;
    }

    public void newMessage(EMMessage emMessage,boolean isChatPage) {
        EMConversation conversation = getAllConversations().get(emMessage.getConversationId());
        if (conversation == null) {
            conversation = new EMConversation();
            conversation.setId(emMessage.getConversationId());
            conversation.setUsername(emMessage.getConversationName());
            conversation.setConversationType(EMConversation.CHAT);
            conversation.setUnReadMsgCount(1);
            conversation.setAllMsgCount(1);
        } else {
            conversation.setUnReadMsgCount(conversation.getUnreadMsgCount() + 1);
            conversation.setAllMsgCount(conversation.getAllMsgCount() + 1);
        }
        if (isChatPage){//在聊天页面，未读消息为0
            conversation.setUnReadMsgCount(0);
        }
        addConversation(conversation);//更新会话  会话数据库
        updateLastMessage(emMessage);//更新最后一条消息  最后数据库
        addMessage(emMessage);//添加消息到缓存和数据库 消息数据库
        if (mMessageListener != null){
            mMessageListener.onNewMessage(emMessage);
        }
    }
    /*====================== 新消息产生 =============================*/

    /*====================== 聊天消息=============================*/
    private List<EMMessage> allMessage;

    /**
     * 获取所有消息
     *
     * @param conversationId
     * @return
     */
    public List<EMMessage> getAllMessage(String conversationId) {
        if (allMessage == null || allMessage.size() == 0 || !conversationId.equals(allMessage.get(0).getConversationId())) {
            MessgeDao messgeDao = new MessgeDao(mContext);
            allMessage = messgeDao.getMessageList(conversationId);
        }
        if (allMessage == null) {
            allMessage = new ArrayList<>();
        }
        return allMessage;
    }

    /**
     * 分页获取所有消息
     *
     * @param conversationId
     * @return
     */
    public List<EMMessage> getAllMessageByPage(String conversationId, int from, int to) {
        if (allMessage == null || allMessage.size() <= (Math.abs(from - to + 1)) || !conversationId.equals(allMessage.get(0).getConversationId())) {
            MessgeDao messgeDao = new MessgeDao(mContext);
            allMessage = messgeDao.getMessageListByPage(conversationId, from, to);
        }
        if (allMessage == null) {
            allMessage = new ArrayList<>();
        }
        return allMessage;
    }

    /**
     * 添加消息
     *
     * @param emMessage
     */
    public void addMessage(final EMMessage emMessage) {
        ThreadUtils.singleService.execute(new Runnable() {
            @Override
            public void run() {
                getAllMessage(emMessage.getConversationId()).add(emMessage);//缓存
                MessgeDao messgeDao = new MessgeDao(mContext);//数据库
                messgeDao.saveMessage(emMessage);
            }
        });

    }

    /**
     * 删除数据
     *
     * @param emMessage
     */
    public void deleteMessage(final EMMessage emMessage) {
        ThreadUtils.singleService.execute(new Runnable() {
            @Override
            public void run() {
                //更新内存
                getAllMessage(emMessage.getConversationId()).remove(emMessage);
                //更新数据库
                MessgeDao messgeDao = new MessgeDao(mContext);
                messgeDao.deleteMessage(emMessage.getMsgId());
            }
        });
    }
    /**
     * 删除历史消息
     *
     * @param id
     */
    public void deleteMessageByConversationId(final String id) {
        ThreadUtils.singleService.execute(new Runnable() {
            @Override
            public void run() {
                //更新内存
                getAllMessage(id).clear();
                //更新数据库
                MessgeDao messgeDao = new MessgeDao(mContext);
                messgeDao.deleteMessageByConversationId(id);
            }
        });
    }
    /*====================== 聊天消息=============================*/

    public void downloadFile(String url, String tempPath, Map<String, String> maps, EMCallBack back) {
        FileUtils.downLoad(url, tempPath, back);
    }

    public void downloadThumbnail(EMMessage message) {

    }

}
