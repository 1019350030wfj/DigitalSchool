package com.onesoft.jaydenim.imaction;

import com.onesoft.improtocol.FileData;
import com.onesoft.improtocol.MsgProcess;
import com.onesoft.jaydenim.EMClient;
import com.onesoft.jaydenim.utils.DateUtils;

import java.io.File;

/**
 * 发送消息
 * 调用NetService的send方法
 * Created by Jayden on 2016/9/22.
 */

public class SendAction {

    private static SendAction sInstance;

    private SendAction() {
    }

    public static SendAction getInstance() {
        if (sInstance == null) {
            synchronized (SendAction.class) {
                if (sInstance == null) {
                    sInstance = new SendAction();
                }
            }
        }
        return sInstance;
    }

    /**
     * login
     *
     * @param userId 登录用户id
     */
    public void sendLogin(String userId) {
        byte[] msgByte = MsgProcess.PackMessage(ActionType.OSIMMSG_LOGIN, userId, "1");
        EMClient.getInstance().sendMessage(msgByte);
    }

    /**
     * 发送好友列表
     */
    public void sendFriendInfo() {
        String friendUid = EMClient.getInstance().getFriendUids();
        byte[] msgByte = MsgProcess.PackMessage(ActionType.OSIMMSG_GETFRISTATE, friendUid, "");
        EMClient.getInstance().sendMessage(msgByte);
    }

    /**
     * 发送文本消息
     *
     * @param content
     * @param toUser
     */
    public void sendTextMessage(String content, String fromUser,String toUser) {
        String time = DateUtils.getDateToString(System.currentTimeMillis());
        String str = fromUser + "  " + time + "|92,3892314127,0,255,0,0,0,0,宋体|" + content;
        byte[] msgByte = MsgProcess.PackMessage(ActionType.OSIMMSG_MESSAGE, toUser, str);
        EMClient.getInstance().sendMessage(msgByte);
    }

    /*=========== 发送文件 有三部============*/
    private static final String TYPE = "%@  %@|92,3892314127,0,255,0,0,0,0,宋体|[Emo]%@[//]%d[/Emo]";
    public static final int TYPE_SEND_TEXT = 0;
    public static final int TYPE_SEND_IMAGE = 1;
    public static final int TYPE_SEND_FILE = 2;
    public static final int TYPE_SEND_VOICE = 3;

    //%d = 0文字、1图片、2文件、3语音信息大小
    private String toUserId;//文件发送
    public void sendFile1(String toUser, String nick,String path, int type) {
        toUserId = toUser;
        File file = new File(path);
        String time = DateUtils.getDateToString(System.currentTimeMillis());
        String str = nick + "  " + time + "|92,3892314127,0,255,0,0,0,0,宋体|[Emo]" + file.getName() + "[//]" + type + "[/Emo]";
        byte[] msgByte = MsgProcess.PackMessage(ActionType.OSIMMSG_MESSAGE, toUser, str);
        EMClient.getInstance().sendMessage(msgByte);
    }

    public int sendFile2(String filePath) {
        file = new File(filePath);
        int time = (int) System.currentTimeMillis();
        String str = time + "," + file.length() + "," + file.getName();
        byte[] msgByte = MsgProcess.PackMessage(ActionType.OSIMMSG_ULFILEHEAD, str, "");
        EMClient.getInstance().sendMessage(msgByte);
        return time;
    }

    private File file;

    public void sendFile3(int time, String filePath) {
        file = new File(filePath);
        long length = file.length();
        long count = length % 1024 == 0 ? length / 1024 : length / 1024 + 1;
        for (int i = 1; i <= count; i++) {
            byte[] msgByte = MsgProcess.SplitFileData(filePath, i - 1, 1024);
            if (msgByte != null) {
                byte[] msg = MsgProcess.PackFileData(new FileData(time, i, msgByte));
                EMClient.getInstance().sendMessage(msg);
            }
        }
    }

    //这一步是发完三步后，接收到服务器的反馈，然后再接着发
    public void sendFile4(String[] strs) {
        String sign = "@COMMAND@fileinfo:" + strs[1] + "," + file.length() + "," + strs[2] + "," + strs[3] + ",filetitle";
        byte[] msgByte = MsgProcess.PackMessage(ActionType.OSIMMSG_MESSAGE, toUserId, sign);
        EMClient.getInstance().sendMessage(msgByte);
    }
    /*=========== 发送文件 有三部============*/

    /*=========== 接收文件 ============*/

    /**
     * 1653562296  fileinfo:120.41.45.130,6069,e:\,icon.png,filetitle;
     * 当接收到文件类型时，取出参数，并调用此接口
     * 发送请求下载文件
     * OSIMMSG_DLFILEHEAD  20160926115152841,e:\,IMG_1393.png
     *
     * @param arr
     */
    public void sendDownloadFileHeader(String arr) {
        byte[] msgByte = MsgProcess.PackMessage(ActionType.OSIMMSG_DLFILEHEAD,
                arr, "");
        EMClient.getInstance().sendMessage(msgByte);
    }

    /*=========== 接收文件 ============*/

    /*=================好友相关=================*/

    /**
     * 添加好友
     * @param toUserId
     */
    public void addFriend(String toUserId) {
        byte[] msgByte = MsgProcess.PackMessage(ActionType.OSIMMSG_MESSAGE,
                toUserId, ReceiveAction.FRIEDN_SHAKE);
        EMClient.getInstance().sendMessage(msgByte);
    }

    /**
     * 同意好友，删除好友
     * @param toUserId 被同意好友的id
     */
    public void agreeFriend(String toUserId) {
        byte[] msgByte = MsgProcess.PackMessage(ActionType.OSIMMSG_MESSAGE,
                toUserId, ReceiveAction.FRIEDN_AGREE_STR);
        EMClient.getInstance().sendMessage(msgByte);
    }
    /*=================好友相关=================*/
    /*=================离线消息=================*/
    public void sendOffline1() {
        byte[] msgByte = MsgProcess.PackMessage(ActionType.OSIMMSG_GETMSGOFFLINE,
                "","");
        EMClient.getInstance().sendMessage(msgByte);
    }
    public void sendOffline2() {
        byte[] msgByte = MsgProcess.PackMessage(ActionType.OSIMMSG_GETDATAOFFLINE,
                "","");
        EMClient.getInstance().sendMessage(msgByte);
    }
    /*=================离线消息=================*/
}
