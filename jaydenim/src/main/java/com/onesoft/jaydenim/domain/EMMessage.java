package com.onesoft.jaydenim.domain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.onesoft.jaydenim.EMClient;
import com.onesoft.jaydenim.imaction.SendAction;
import com.onesoft.jaydenim.utils.EaseImageUtils;
import com.onesoft.jaydenim.utils.LogUtil;

import java.io.File;
import java.io.Serializable;

/**
 * 消息实体
 * 发送方向：发出去还是接收
 * 时间戳
 * 发送状态
 * Created by Jayden on 2016/9/19.
 */

public class EMMessage implements Serializable {

    private String mMsgFrom;//消息来源id
    private String mMsgFromNick;//消息来源的昵称
    private int mMsgType;
    private int mMsgDirect;

    public void setMsgType(int msgType) {
        mMsgType = msgType;
    }

    public int getType() {
        return mMsgType;
    }

    public void setMsgDirect(int msgDirect) {
        mMsgDirect = msgDirect;
    }

    public int direct() {
        return mMsgDirect;
    }

    public void setMsgFromNick(String msgFromNick) {
        mMsgFromNick = msgFromNick;
    }

    public String getMsgFromNick() {
        return mMsgFromNick;
    }

    public void setMsgFrom(String msgFrom) {
        mMsgFrom = msgFrom;
    }

    public String getMsgFrom() {
        return mMsgFrom;
    }

    private boolean isDelivered;

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    /**
     * 是否传达
     *
     * @return
     */
    public boolean isDelivered() {
        return isDelivered;
    }

    private boolean isAcked;

    public void setAcked(boolean acked) {
        isAcked = acked;
    }

    /**
     * 是否确认
     *
     * @return
     */
    public boolean isAcked() {
        return isAcked;
    }

    public int getError() {
        byte var1 = 0;
        return var1;
    }

    private long msgTime;

    public void setMsgTime(long msgTime) {
        this.msgTime = msgTime;
    }

    /**
     * 获取时间戳
     *
     * @return
     */
    public long getMsgTime() {
        return msgTime;
    }

    public EMMessage.ChatType getChatType() {
        return EMMessage.ChatType.Chat;
    }

    private boolean isListener;

    public void setListened(boolean b) {
        this.isListener = b;
    }

    public boolean isListened() {
        return isListener;
    }

    public void setChatType(ChatType chat) {

    }

    /**
     * 接收到声音文件
     *
     * @param username
     * @return
     */
    public static EMMessage createVoiceMessage(String path, String username) {
        EMMessage message = new EMMessage();
        final String myNickname = EMClient.getInstance().contactManager().getContactList().get(username).getNick();
        File file = new File(path);
        message.setFilename(file.getName());
        message.setMsgId(String.valueOf(System.currentTimeMillis()));
        message.setMsgType(VOICE);
        message.setMsgDirect(RECEIVE);
        message.setMsgFrom(username);
        message.setMsgFromNick(myNickname);
        message.setConversationName(myNickname);
        message.setConversationId(username);
        message.setLocalUrl(path);
        message.setConversationId(username);
        return message;
    }

    /**
     * 发送声音文件
     *
     * @param path
     * @param length
     * @param username
     * @return
     */
    public static EMMessage createVoiceSendMessage(String path, int length, String username, String nick) {
        String myUserId = EMClient.getInstance().getCurrentUser();
        String myNickname = EMClient.getInstance().contactManager().getContactList().get(myUserId).getNick();
        EMMessage message = new EMMessage();
        message.setMsgId(String.valueOf(System.currentTimeMillis()));
        message.setLocalUrl(path);
        message.setLength(length);
        message.setMsgDirect(SEND);
        message.setMsgType(VOICE);
        message.setMsgFrom(myUserId);
        message.setMsgTime(System.currentTimeMillis());
        message.setMsgFromNick(myNickname);
        message.setConversationId(username);
        message.setConversationName(nick);

        SendAction.getInstance().sendFile1(username, myNickname, path, SendAction.TYPE_SEND_FILE);
        int time = SendAction.getInstance().sendFile2(path);
        SendAction.getInstance().sendFile3(time, path);
        return message;
    }

    /**
     * 接收到的文件消息
     *
     * @param path
     * @param username
     * @return
     */
    public static EMMessage createFileMessage(String path, String username) {
        EMMessage message = new EMMessage();
        final String myNickname = EMClient.getInstance().contactManager().getContactList().get(username).getNick();
        message.setMsgId(String.valueOf(System.currentTimeMillis()));
        File file = new File(path);
        message.setFilename(file.getName());
        message.setMsgType(FILE);
        message.setMsgDirect(RECEIVE);
        message.setLocalUrl(path);
        message.setConversationId(username);
        message.setMsgFromNick(myNickname);
        message.setConversationName(myNickname);
        message.setMsgFrom(username);
        return message;
    }

    /**
     * 客户端自己发送文件
     *
     * @param path
     * @param username
     * @return
     */
    public static EMMessage createFileSendMessage(final String path, final String username, String nick) {
        String myUserId = EMClient.getInstance().getCurrentUser();
        final String myNickname = EMClient.getInstance().contactManager().getContactList().get(myUserId).getNick();
        EMMessage message = new EMMessage();
        message.setMsgId(String.valueOf(System.currentTimeMillis()));
        File file = new File(path);
        message.setMsgType(FILE);
        message.setMsgDirect(SEND);
        message.setConversationId(username);
        message.setConversationName(nick);
        message.setMsgTime(System.currentTimeMillis());
        message.setMsgFrom(myUserId);
        message.setMsgFromNick(myNickname);
        message.setLocalUrl(path);
        message.setFilename(file.getName());
        message.setFileSize(file.length());
        new Thread(new Runnable() {
            @Override
            public void run() {
                SendAction.getInstance().sendFile1(username, myNickname, path, SendAction.TYPE_SEND_FILE);
                int time = SendAction.getInstance().sendFile2(path);
                SendAction.getInstance().sendFile3(time, path);
            }
        }).start();
        return message;
    }

    /**
     * 接收到文本消息
     *
     * @param content
     * @param username
     * @return
     */
    public static EMMessage createTextMessage(String content, String username) {
        EMMessage message = new EMMessage();
        final String myNickname = EMClient.getInstance().contactManager().getContactList().get(username).getNick();
        message.setMsgId(String.valueOf(System.currentTimeMillis()));
        message.setMsgType(TXT);
        message.setMsgDirect(RECEIVE);
        message.setContent(content);
        message.setMsgFrom(username);
        message.setMsgFromNick(myNickname);
        message.setConversationName(myNickname);
        message.setConversationId(username);
        return message;
    }

    /**
     * 发送文本消息
     *
     * @param content
     * @param username
     * @return
     */
    public static EMMessage createTextSendMessage(String content, String username, String nick) {
        String myUserId = EMClient.getInstance().getCurrentUser();
        EMMessage message = new EMMessage();
        message.setMsgId(String.valueOf(System.currentTimeMillis()));
        message.setMsgTime(System.currentTimeMillis());
        message.setMsgType(TXT);
        message.setMsgDirect(SEND);
        message.setConversationId(username);
        message.setConversationName(nick);
        message.setContent(content);
        message.setMsgFrom(myUserId);
        message.setMsgFromNick(EMClient.getInstance().contactManager().getContactList().get(myUserId).getNick());

        SendAction.getInstance().sendTextMessage(content, myUserId, username);
        return message;
    }

    private String mContent;
    private String mAvatar;

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getContent() {
        return mContent;
    }

    private String conversationId;
    private String conversationName;

    public void setConversationName(String conversationName) {
        this.conversationName = conversationName;
    }

    public String getConversationName() {
        return conversationName;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getConversationId() {
        return conversationId;
    }

    /*============voice========*/
    private int length;

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    private String voice;

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getVoice() {
        return voice;
    }

    private String localUrl;

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    private String msgId;

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgId() {
        return msgId;
    }

    /*============voice========*/
    /*============image========*/

    private String thumbnailLocalPath;

    public String thumbnailLocalPath() {
        return thumbnailLocalPath;
    }

    public void setThumbnailLocalPath(String thumbnailLocalPath) {
        this.thumbnailLocalPath = thumbnailLocalPath;
    }

    private EMFileMessageBody.EMDownloadStatus downloadStatus;

    public EMFileMessageBody.EMDownloadStatus thumbnailDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(EMFileMessageBody.EMDownloadStatus downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    /**
     * 收到图片消息
     *
     * @param path
     * @param username
     * @return
     */
    public static EMMessage createImageMessage(String path, String username) {
        EMMessage message = new EMMessage();
        final String myNickname = EMClient.getInstance().contactManager().getContactList().get(username).getNick();
        message.setMsgId(String.valueOf(System.currentTimeMillis()));
        message.setMsgType(IMAGE);
        message.setMsgDirect(RECEIVE);
        message.setLocalUrl(path);
        message.setThumbnailLocalPath(path);
        message.setMsgFrom(username);
        message.setConversationId(username);
        message.setMsgFromNick(myNickname);
        message.setConversationName(myNickname);
        return message;
    }

    /**
     * 发送图片消息
     *
     * @param path
     * @param username
     * @return
     */
    public static EMMessage createImageSendMessage(final String path, final String username, String nick) {
        String myUserId = EMClient.getInstance().getCurrentUser();
        final String myNickname = EMClient.getInstance().contactManager().getContactList().get(myUserId).getNick();
        EMMessage message = new EMMessage();
        message.setMsgId(String.valueOf(System.currentTimeMillis()));
        message.setMsgDirect(SEND);
        message.setLocalUrl(path);
        message.setMsgType(IMAGE);
        message.setMsgTime(System.currentTimeMillis());
        message.setMsgFromNick(myNickname);
        message.setMsgFrom(myUserId);
        message.setConversationName(nick);
        message.setConversationId(username);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                String tempPath = EaseImageUtils.getThumbnailImage(path, bitmap.getWidth() / 2);
                SendAction.getInstance().sendFile1(username, myNickname, tempPath, SendAction.TYPE_SEND_IMAGE);
                int time = SendAction.getInstance().sendFile2(tempPath);
                SendAction.getInstance().sendFile3(time, tempPath);
            }
        }).start();

        return message;
    }

    /*============image========*/
/*============file========*/
    private String filename;

    public String getFileName() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    private long fileSize;

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    private String commaStr;//下载文件的字符串

    public void setCommaStr(String commaStr) {
        this.commaStr = commaStr;
    }

    public String getCommaStr() {
        return commaStr;
    }

    public long getFileSize() {
        return fileSize;
    }

    private String remoteUrl;

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    /*============file========*/

    public static final int TXT = 0;
    public static final int IMAGE = 1;
    public static final int VIDEO = 2;
    public static final int LOCATION = 3;
    public static final int VOICE = 4;
    public static final int FILE = 5;
    public static final int CMD = 6;

    public static enum ChatType {
        Chat,
        GroupChat,
        ChatRoom;

        private ChatType() {
        }
    }

    public static final int SEND = 0;
    public static final int RECEIVE = 1;

    private EMMessage.Status mMsgStatus;

    public EMMessage.Status status() {
        return EMMessage.Status.CREATE;
    }

    public void setMsgStatus(Status msgStatus) {
        mMsgStatus = msgStatus;
    }

    public static enum Status {
        SUCCESS,
        FAIL,
        INPROGRESS,
        CREATE;

        private Status() {
        }
    }

    public static EMMessage createVideoSendMessage(String var0, String var1, int var2, String var3) {
        File var4 = new File(var0);
        if (!var4.exists()) {
            LogUtil.e("msg", "video file does not exist");
            return null;
        } else {
//            EMMessage var5 = createSendMessage(EMMessage.Type.VIDEO);
//            var5.setReceipt(var3);
//            EMVideoMessageBody var6 = new EMVideoMessageBody(var0, var1, var2, var4.length());
//            var5.addBody(var6);
//            return var5;
            return null;
        }
    }
}
