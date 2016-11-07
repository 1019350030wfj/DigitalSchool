package com.onesoft.jaydenim.imaction;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.SparseArray;

import com.onesoft.improtocol.FileData;
import com.onesoft.improtocol.MsgData;
import com.onesoft.improtocol.MsgProcess;
import com.onesoft.jaydenim.EMCallBack;
import com.onesoft.jaydenim.EMClient;
import com.onesoft.jaydenim.domain.EMMessage;
import com.onesoft.jaydenim.model.EaseVoiceRecorder;
import com.onesoft.jaydenim.utils.DateUtils;
import com.onesoft.jaydenim.utils.LogUtil;
import com.onesoft.jaydenim.utils.PathUtil;
import com.onesoft.jaydenim.utils.RegexUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

import static com.onesoft.jaydenim.imaction.ActionType.OSIMMSG_FILEDATA;
import static com.onesoft.jaydenim.imaction.ActionType.OSIMMSG_GETDATAOFFLINE;
import static com.onesoft.jaydenim.imaction.ActionType.OSIMMSG_GETMSGOFFLINE;
import static com.onesoft.jaydenim.imaction.ActionType.OSIMMSG_LOGINNOTIFY;
import static com.onesoft.jaydenim.imaction.ActionType.OSIMMSG_LOGOUTNOTIFY;
import static com.onesoft.jaydenim.imaction.ActionType.OSIMMSG_MESSAGE;
import static com.onesoft.jaydenim.imaction.ActionType.OSIMMSG_NEWMESSAGE;
import static com.onesoft.jaydenim.imaction.ActionType.OSIMMSG_REPFILEADDR;
import static com.onesoft.jaydenim.imaction.ActionType.OSIMMSG_RETFRISTATE;
import static com.onesoft.jaydenim.imaction.ActionType.OSIMMSG_ULFILEHEAD;
import static com.onesoft.jaydenim.imaction.ActionType.OSIMMSG_USERBAD;
import static com.onesoft.jaydenim.imaction.ActionType.OSIMMSG_USEROK;
import static com.onesoft.jaydenim.utils.ByteUtils.byteMerger;
import static com.onesoft.jaydenim.utils.ByteUtils.byteSplit;

/**
 * 接收服务器消息
 * Created by Jayden on 2016/9/22.
 */
public class ReceiveAction {
    /*===========接收到消息，通过正则表达式，确定类型=============*/
    private static final String PATTERN_TEXT_IMAGE = "(.+?)\\[Emo\\](.+?)\\[\\/\\/\\]1\\[\\/Emo\\](.+?)";
    private static final String PATTERN_IMAGE = "\\[Emo\\](.+?)\\[\\/\\/\\]1\\[\\/Emo\\]";
    private static final String PATTERN_FILE = "\\[Emo\\](.+?)\\[\\/\\/\\]2\\[\\/Emo\\]";
    private static final String PATTERN_VOICE = "\\[Emo\\](.+?)\\[\\/\\/\\]3\\[\\/Emo\\]";
     /*===========接收到消息，通过正则表达式，确定类型=============*/

    private static final String SPLIT_VERTICAL_LINE = "\\|";
    private static final String SPLIT_SPACE = "  ";
    private static final String SPLIT_COMMA = ",";
    private static final String SPLIT_BACK_DIAGONAL = "\\\\";

    public static final String FRIEDN_SHAKE = "@COMMAND@frihandshake:";//好友请求
    public static final String FRIEDN_AGREE_STR = "@COMMAND@updatefrilist:";//好友同意、好友删除

    public static final int CHAT_PAGE_CHAT = 0;//聊天页面聊天信息
    public static final int CHAT_CONVERSATION_PAGE = 1;//会话列表聊天信息
    public static final int FRIEND_ADD = 2;//好友请求
    public static final int FRIEND_AGREE = 3;//好友同意
    public static final int LOGIN_SUCCESS = 4;//好友登录
    public static final int LOGOUT_SUCCESS = 5;//退出

    private SparseArray<IMessageListener> mMsgCallback;

    private static ReceiveAction sInstance;

    private ReceiveAction() {
        mMsgCallback = new SparseArray<>();
    }

    public static ReceiveAction getInstance() {
        if (sInstance == null) {
            synchronized (SendAction.class) {
                if (sInstance == null) {
                    sInstance = new ReceiveAction();
                }
            }
        }
        return sInstance;
    }

    public void setEmCallBackNull() {
        this.mEMCallBack = null;
    }

    /**
     * 回调处理服务器返回的数据
     * 接着再利用handler把数据返回到主线程
     * 避免复杂数据在主线程处理
     */
    public interface IReceiveActionListener {
        void onReceive(byte[] bytes);
    }

    public IReceiveActionListener getReceiveActionListener() {
        return new IReceiveActionListener() {
            @Override
            public void onReceive(byte[] bytes) {
                handleMessage(bytes);
            }
        };
    }

    /**
     * 处理从服务器返回的数据
     *
     * @param bytes
     */
    private void handleMessage(byte[] bytes) {
        tempByte = byteMerger(tempByte, bytes);
        while (tempByte.length >= 8) {
            int tempInt = (tempByte[4] & 0xff) | ((tempByte[5] & 0xff) << 8) | ((tempByte[6] & 0xff) << 16) | ((tempByte[7] & 0xff) << 24);
            if (tempByte.length - tempInt - 11 < 0) {
                return;
            }
            byte[] newTmp = new byte[tempInt + 8];
            System.arraycopy(tempByte, 0, newTmp, 0, newTmp.length);
            tempByte = byteSplit(tempInt + 11, tempByte);
            MsgData msgData = MsgProcess.UnpackMessage(newTmp);
            //根据类型，返回对应的回调
            switch (msgData.getMsgType()) {
                case OSIMMSG_FILEDATA: {//接收文件消息，要下载的文件数据是2进制的
                    LogUtil.d("receive = " + OSIMMSG_FILEDATA);
                    FileData fileData = MsgProcess.UnpackFileData(msgData.getData());
                    if (DEFAULT_FILE_ID == mFileId || fileData.getFlId() == mFileId) {
                        //通过fileId来判断接收的数据是不是属于同一个文件
                        File file = new File(mFilePath);
                        try {
                            mFileId = fileData.getFlId();
                            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                            randomAccessFile.seek(1024 * (fileData.getFlIdx() - 1));
                            randomAccessFile.write(fileData.getData());
                            if (mMsgType == SendAction.TYPE_SEND_FILE && mEMCallBack != null) {
                                //文件下载回调进度
                                mEMCallBack.onProgress((int) (100 * (randomAccessFile.length() / mFileLength)), "");
                            }
                            if (randomAccessFile.length() == mFileLength) {
                                randomAccessFile.close();
                                mFileId = DEFAULT_FILE_ID;
                                if (mMsgType == SendAction.TYPE_SEND_FILE && mEMCallBack != null) {
                                    //文件下载回调进度
                                    mEMCallBack.onSuccess();
                                } else {
                                    //下载完，刷新页面
                                    mHandler.sendMessage(mHandler.obtainMessage(OSIMMSG_NEWMESSAGE));
                                }
                            } else {
                                randomAccessFile.close();
                            }
                        } catch (FileNotFoundException e) {
                            mFileId = DEFAULT_FILE_ID;
                            e.printStackTrace();
                            if (mMsgType == SendAction.TYPE_SEND_FILE && mEMCallBack != null) {
                                //文件下载
                                mEMCallBack.onError(0, "");
                            }
                        } catch (IOException e) {
                            mFileId = DEFAULT_FILE_ID;
                            e.printStackTrace();
                            if (mMsgType == SendAction.TYPE_SEND_FILE && mEMCallBack != null) {
                                //文件下载
                                mEMCallBack.onError(0, "");
                            }
                        }
                    }
                    break;
                }
                case OSIMMSG_NEWMESSAGE: {//来自谁的消息//消息内容 属于普通聊天
                    receiveNewMessage(msgData);
                    break;
                }
                case OSIMMSG_USEROK: {//登录成功
                    LogUtil.d("receive = " + OSIMMSG_USEROK);
                    if (mMsgCallback.get(LOGIN_SUCCESS) != null) {//会话列表
                        //通知刷新
                        mMsgCallback.get(LOGIN_SUCCESS).onMessageCallBack(null);
                    }
                    SendAction.getInstance().sendFriendInfo();
                    break;
                }
                case OSIMMSG_USERBAD: {//登录失败
                    LogUtil.d("receive = " + OSIMMSG_USERBAD);
                    break;
                }
                case OSIMMSG_RETFRISTATE: {//好友的状态消息
                    LogUtil.d("receive = OSIMMSG_RETFRISTATE" + OSIMMSG_RETFRISTATE);
                    SendAction.getInstance().sendOffline1();
                    break;
                }
                case OSIMMSG_GETMSGOFFLINE: {//离线消息
                    try {
                        Thread.sleep(2000);
                        SendAction.getInstance().sendOffline2();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case OSIMMSG_GETDATAOFFLINE: {//离线消息
                    receiveNewMessage(msgData);
                    break;
                }
                case OSIMMSG_LOGINNOTIFY: {//好友登入通知
                    LogUtil.d("receive = " + OSIMMSG_LOGINNOTIFY);
                    break;
                }
                case OSIMMSG_LOGOUTNOTIFY: {//退出通知
                    LogUtil.d("receive = " + OSIMMSG_LOGOUTNOTIFY);
                    if (mMsgCallback.get(LOGOUT_SUCCESS) != null) {
                        //退出
                        mMsgCallback.get(LOGOUT_SUCCESS).onMessageCallBack(null);
                    }
                    break;
                }
                case OSIMMSG_MESSAGE: {
                    LogUtil.d("receive = " + OSIMMSG_MESSAGE);
                    break;
                }
                case OSIMMSG_ULFILEHEAD: {
                    LogUtil.d("receive = " + OSIMMSG_ULFILEHEAD);
                    break;
                }
                case OSIMMSG_REPFILEADDR: {//发送完文件，服务器通知客户端
                    LogUtil.d("receive = " + OSIMMSG_REPFILEADDR);
                    //11318,fileserver,e:\,Balance(magazine)-05-2.3.001-bigpicture_05_9.jpg
                    if (msgData.getData() != null) {
                        try {
                            String str = new String(msgData.getData(), "GBK");
                            String[] strings = str.split(SPLIT_COMMA);
                            if (strings != null) {
                                SendAction.getInstance().sendFile4(strings);
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
            }
        }
    }

    /**
     * 新消息到来
     *
     * @param msgData
     */
    private void receiveNewMessage(MsgData msgData) {
        if (msgData.getData() != null) {
            try {
                String str = new String(msgData.getData(), "GBK");
                String[] strings = str.split(SPLIT_VERTICAL_LINE);
                //长度如果等于3
                if (3 == strings.length) {//第一次返回基本消息信息
                    //判断是文件，图片，声音，还是文本
                    isWhichType(strings);
                } else if (1 == strings.length) {//第二次返回要向服务器请求的参数
                    String[] spaceStr = str.split(SPLIT_SPACE);
                    if (spaceStr != null && spaceStr.length > 1) { //这边可以发送给服务器
                        String[] commaStr = spaceStr[1].split(SPLIT_COMMA);
                        if (commaStr != null && commaStr.length > 3 && message != null) {
                            mFileLength = Long.parseLong(commaStr[1]);
                            String signStr = System.currentTimeMillis() + "," + commaStr[2] + "," + commaStr[3];
                            message.setFileSize(mFileLength);//通知页面刷新
                            message.setCommaStr(signStr);//点击文件才下载
                            Message msg = mHandler.obtainMessage();
                            msg.obj = message;
                            msg.what = OSIMMSG_NEWMESSAGE;
                            mHandler.sendMessage(msg);//通知页面刷新
                            if (mMsgType != SendAction.TYPE_SEND_FILE) {
                                mFileId = DEFAULT_FILE_ID;
                                SendAction.getInstance().sendDownloadFileHeader(signStr);
                            }
                        } else if (commaStr != null && commaStr.length > 0) {
                            if (commaStr[0].contains(FRIEDN_SHAKE)) {//好友请求
                                EMClient.getInstance().messageManager().saveFriendRequestCount(1);
                                mHandler.sendEmptyMessage(FRIEND_ADD);
                            }
                            if (commaStr[0].contains(FRIEDN_AGREE_STR)) {//好友同意、好友删除
                                //需要更新联系人列表
                                EMClient.getInstance().contactManager().refreshList(spaceStr[0]);
                                mHandler.sendEmptyMessage(FRIEND_AGREE);
                            }
                        }
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private int mMsgType;
    private String mFilePath;
    private static final int DEFAULT_FILE_ID = -129;
    private SparseArray<FileType> mFileTypes;
    private int mFileId = DEFAULT_FILE_ID;
    private long mFileLength;

    private class FileType {
        private int mMsgType;
    }

    private EMMessage message = null;

    /**
     * 判断是文件，图片，声音，还是文本
     *
     * @param strings 消息基本信息
     *                1653562296  jqchen  2016-09-23 15:11:53|92,3892314127,0,225,0,0,0,0,宋体|[Emo]D:\workspace\HQ_QQ\res\drawable-hdpi\activity.png[//]2[/Emo]
     */
    private void isWhichType(String[] strings) {
        String[] strings1 = strings[0].split(SPLIT_SPACE);//分割完是用户id，昵称，时间
        if (3 == strings1.length) {
            if (RegexUtil.startCheck(PATTERN_FILE, strings[2])) {//文件
                String filename = RegexUtil.getCheck(PATTERN_FILE, strings[2]);
                String[] filenames = filename.split(SPLIT_BACK_DIAGONAL);
                if (filenames.length > 0) {
                    if (filename.contains(EaseVoiceRecorder.EXTENSION)) {//声音
                        mFilePath = PathUtil.getInstance().getVoicePath() + getFileName(filenames[filenames.length - 1]);
                        message = EMMessage.createVoiceMessage(mFilePath, strings1[0]);
                        mMsgType = SendAction.TYPE_SEND_VOICE;
                    } else {//文件
                        mFilePath = PathUtil.getInstance().getFilePath() + getFileName(filenames[filenames.length - 1]);
                        message = EMMessage.createFileMessage(mFilePath, strings1[0]);
                        mMsgType = SendAction.TYPE_SEND_FILE;
                    }
                }
            } else if (RegexUtil.startCheck(PATTERN_IMAGE, strings[2])) {//图片
                String filename = RegexUtil.getCheck(PATTERN_IMAGE, strings[2]);
                String[] filenames = filename.split(SPLIT_BACK_DIAGONAL);
                if (filenames.length > 0) {
                    mFilePath = PathUtil.getInstance().getImagePath() + getFileName(filenames[filenames.length - 1]);
                    message = EMMessage.createImageMessage(mFilePath, strings1[0]);
                    mMsgType = SendAction.TYPE_SEND_IMAGE;
                }
            } else if (RegexUtil.startCheck(PATTERN_TEXT_IMAGE, strings[2])){//图文一起发
                String filename = RegexUtil.getCheck(PATTERN_IMAGE, strings[2]);
                String[] filenames = filename.split(SPLIT_BACK_DIAGONAL);
            } else {//文本
                message = EMMessage.createTextMessage(strings[2], strings1[0]);
                mMsgType = SendAction.TYPE_SEND_TEXT;
            }
            message.setMsgTime(DateUtils.getDateFromString(strings1[2]));
            if (mMsgType == SendAction.TYPE_SEND_TEXT) {
                Message msg = mHandler.obtainMessage();
                msg.obj = message;
                msg.what = OSIMMSG_NEWMESSAGE;
                mHandler.sendMessage(msg);
            }
        }
    }

    private String getFileName(String filename) {
        if (filename.contains("[//Size]")){
            int index = filename.indexOf("[//Size]");
            return filename.substring(0,index);
        } else {
            return filename;
        }
    }

    public void addMessageListener(int key, IMessageListener listener) {
        mMsgCallback.put(key, listener);
    }

    public void removeMessageListener(int key) {
        mMsgCallback.remove(key);
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case OSIMMSG_NEWMESSAGE: {//消息到来
                    //更新数据
                    if (msg.obj != null) {//判断的原因是：有可能只是通知页面刷新，比如文件下载
                        EMClient.getInstance().chatManager().newMessage((EMMessage) msg.obj,false);
                    }
                    if (mMsgCallback.get(CHAT_CONVERSATION_PAGE) != null) {//会话列表
                        //通知刷新
                        mMsgCallback.get(CHAT_CONVERSATION_PAGE).onMessageCallBack(msg.obj);
                    }
                    if (mMsgCallback.get(CHAT_PAGE_CHAT) != null) {//聊天列表
                        mMsgCallback.get(CHAT_PAGE_CHAT).onMessageCallBack(msg.obj);
                    }
                    break;
                }
                case FRIEND_ADD: {//好友请求
                    if (mMsgCallback.get(FRIEND_ADD) != null) {
                        mMsgCallback.get(FRIEND_ADD).onMessageCallBack(msg.obj);
                    }
                    break;
                }
                case FRIEND_AGREE: {//好友删除、好友同意
                    if (mMsgCallback.get(FRIEND_AGREE) != null) {
                        mMsgCallback.get(FRIEND_AGREE).onMessageCallBack(msg.obj);
                    }
                    break;
                }
            }
        }
    };

    private byte[] tempByte;
    private EMCallBack mEMCallBack;

    public void setMsgType(EMMessage emMessage, EMCallBack emCallBack) {
        this.mMsgType = SendAction.TYPE_SEND_FILE;
        this.mEMCallBack = emCallBack;
        mFileId = DEFAULT_FILE_ID;
        this.mFilePath = emMessage.getLocalUrl();
        this.mFileLength = emMessage.getFileSize();
        SendAction.getInstance().sendDownloadFileHeader(emMessage.getCommaStr());
    }
}
