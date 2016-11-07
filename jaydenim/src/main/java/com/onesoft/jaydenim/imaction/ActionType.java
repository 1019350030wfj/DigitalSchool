package com.onesoft.jaydenim.imaction;

/**
 * Created by Jayden on 2016/9/22.
 */

public class ActionType {

    public static final int OSIMMESSAGE = 0xef000000;
    public static final int OSIMMSG_LOGIN = OSIMMESSAGE + 0x01;//登录
    public static final int OSIMMSG_MESSAGE = OSIMMESSAGE + 0x02;//发送消息
    public static final int OSIMMSG_USEROK = OSIMMESSAGE + 0x03;
    public static final int OSIMMSG_USERBAD = OSIMMESSAGE + 0x04;
    public static final int OSIMMSG_GETLIST = OSIMMESSAGE + 0x05;//
    public static final int OSIMMSG_USERINFO = OSIMMESSAGE + 0x06;
    public static final int OSIMMSG_LISTEND = OSIMMESSAGE + 0x07;
    public static final int OSIMMSG_USERLIST = OSIMMESSAGE + 0x08;
    public static final int OSIMMSG_USERSTATUS = OSIMMESSAGE + 0x09;
    public static final int OSIMMSG_NEWMESSAGE = OSIMMESSAGE + 0x0a;    // 接收到文字消息
    public static final int OSIMMSG_HTTPPOST = OSIMMESSAGE + 0x0b;    // 接收到HTTP消息
    public static final int OSIMMSG_NEWFILE = OSIMMESSAGE + 0x0c;    // 接收到文件消息
    public static final int OSIMMSG_FILE = OSIMMESSAGE + 0x0d;    // 客户端接收到文件
    public static final int OSIMMSG_COVERLOGIN = OSIMMESSAGE + 0x41;    // 添加了踢人的反馈消息,覆盖登入

    // 客户端发送离线消息
    public static final int OSIMMSG_MSGOFFLINE = OSIMMESSAGE + 0x0e;
    // 客户端端请求获取离线消息
    public static final int OSIMMSG_GETMSGOFFLINE = OSIMMESSAGE + 0x0f;
    // 客户端请求获取离线消息数据
    public static final int OSIMMSG_GETDATAOFFLINE = OSIMMESSAGE + 0x10;

    // 发送短信
    public static final int OSIMMSG_SMS = OSIMMESSAGE + 0x11;

    // 客户端向服务器请求历史记录
    public static final int OSIMMSG_GETHISTORYMSG = OSIMMESSAGE + 0x12;
    // 服务器通知客户端开始接受历史记录
    public static final int OSIMMSG_RECVHISTORYDATA = OSIMMESSAGE + 0x13;
    // 客户端通知服务器开始发送历史记录数据
    public static final int OSIMMSG_SENDHISTORYDATA = OSIMMESSAGE + 0x15;
    // 服务器开始向客户端发送历史记录数据
    public static final int OSIMMSG_HISTORYDATA = OSIMMESSAGE + 0x16;


    // 客户端向服务器查询最新版本
    public static final int OSIMMSG_CHECK_VERSION = OSIMMESSAGE + 0x17;
    // 服务器发送最新版号给客户端
    public static final int OSIMMSG_VERSION = OSIMMESSAGE + 0x18;


    // 客户端发送普通短信
    public static final int OSIMMSG_NORMAL_SMS = OSIMMESSAGE + 0x21;

    // 客户端发送的短信记录
    public static final int OSIMMSG_SMS_RECORD = OSIMMESSAGE + 0x22;

    // 客户端通过程序提交短信
    public static final int OSIMMSG_APP_SMS = OSIMMESSAGE + 0x23;

    // 客户端新建项目
    public static final int OSIMMSG_NEW_PROJECT = OSIMMESSAGE + 0x24;
    // 客户端请求获取项目
    public static final int OSIMMSG_GET_PROJECT = OSIMMESSAGE + 0x25;
    // 服务器发送的项目
    public static final int OSIMMSG_PROJECT = OSIMMESSAGE + 0x26;
    // 客户端请求获取工作列表
    public static final int OSIMMSG_GET_SCHEDULE = OSIMMESSAGE + 0x27;
    // 服务器发送的工作
    public static final int OSIMMSG_SCHEDULE = OSIMMESSAGE + 0x28;

    /////这些都是消息内的图片语音
// 客户端 请求好友的socket
    public static final int OSIMMSG_FILEHEADINFO = OSIMMESSAGE + 0x30;
    // 客户端 发送个人在线文件
    public static final int OSIMMSG_ONLINEFILE = OSIMMESSAGE + 0x31;
    // 客户端 发送个人离线文件
    public static final int OSIMMSG_OFFLINEFILE = OSIMMESSAGE + 0x32;
    // 客户端 发送群组文件
    public static final int OSIMMSG_GROUPFILE = OSIMMESSAGE + 0x33;
/////////

    // 客户端获取好友的在线状态
    public static final int OSIMMSG_GETFRISTATE = OSIMMESSAGE + 0x34;
    public static final int OSIMMSG_RETFRISTATE = OSIMMESSAGE + 0x35;

    //通知其他用户
    public static final int OSIMMSG_LOGINNOTIFY = OSIMMESSAGE + 0x36;
    public static final int OSIMMSG_LOGOUTNOTIFY = OSIMMESSAGE + 0x37;

    //添加和移除好友
    public static final int OSIMMSG_ADDFRIEND = OSIMMESSAGE + 0x38;
    public static final int OSIMMSG_REMOVERRIEND = OSIMMESSAGE + 0x39;

    //消息反馈
    public static final int OSIMMSG_MSGCHECK = OSIMMESSAGE + 0x3a;

    //文件传输
    public static final int OSIMMSG_ULFILEHEAD = OSIMMESSAGE + 0x3b; //上传文件的头 c->s
    public static final int OSIMMSG_DLFILEHEAD = OSIMMESSAGE + 0x3c; //下载文件的头 c->s
    public static final int OSIMMSG_REPFILEADDR = OSIMMESSAGE + 0x3d; //返回文件地址信息等 s->c
    public static final int OSIMMSG_FILEDATA = OSIMMESSAGE + 0x3e; //发送文件数据 c->s | s->c
    public static final int OSIMMSG_DLFILEERROR = OSIMMESSAGE + 0x3f; //下载文件失败 s->c
//public static final int OSIMMSG_ULFILEREALLY

    //消息已经打开
    public static final int OSIMMSG_MSGOPENED = OSIMMESSAGE + 0x3f; //c->s->c


//////////////////////////////////////////////////////////////////////////
// 系统消息
//////////////////////////////////////////////////////////////////////////
    public static final int WM_USER = 0x10;
    public static final int UM_WriteLog = WM_USER + 0x60; //发送日志消息

    public static final int XM_USER = WM_USER + 0x65;

    public static final int XM_LOGIN = XM_USER + 0x01;
    public static final int XM_LOGOFF = XM_USER + 0x02;
    public static final int XM_MESSAGE = XM_USER + 0x03;
    public static final int XM_CONNECTED = XM_USER + 0x04;
    public static final int XM_GETLIST = XM_USER + 0x05;
    public static final int XM_HTTPPOST = XM_USER + 0x06;
    // 文件消息处理
    public static final int XM_NEWFILE = XM_USER + 0x07;

    // 双击User List TreeCtrl时发送的消息
    public static final int XM_EMDBLCLICKTREE = WM_USER + 0x08;
    // 处理用户请求的离线消息
    public static final int XM_GETMSGOFFLINE = WM_USER + 0x09;
    // 离线消息
    public static final int XM_OFFLINEMESSAGE = XM_USER + 0x10;
    // 离线消息数据
    public static final int XM_GETDATAOFFLINE = XM_USER + 0x11;

    // 短信
    public static final int XM_SMS = XM_USER + 0x12;

    // 客户端请求消息历史记录
    public static final int XM_GETHISTORYDATA = XM_USER + 0x13;

    // 客户端通知服务器开始发送历史消息数据
    public static final int XM_SENDHISTORYDATA = XM_USER + 0x15;

    // 客户端发送普通短信
    public static final int XM_NORMAL_SMS = XM_USER + 0x16;

    // 记录客户端的短信
    public static final int XM_RECORD_SMS = XM_USER + 0x18;

    // 记录客户端项目
    public static final int XM_RECORD_PROJECT = XM_USER + 0x19;

    // 读取并发送客户端项目
    public static final int XM_GET_PROJECT = XM_USER + 0x20;

    // 读取并发送客户端工作
    public static final int XM_GET_SCHEDULE = XM_USER + 0x21;

    // 读取文件的头信息
    public static final int XM_FILEHEADINFO = XM_USER + 0x22;

    public static final int XM_FILEONLINE = XM_USER + 0x23;
    //获取好友的状态
    public static final int XM_GetFriState = XM_USER + 0x24;

    //添加/移除好友
    public static final int XM_AddFriend = XM_USER + 0x25;
    public static final int XM_RemoveFriend = XM_USER + 0x26;

    //打开消息
    public static final int XM_MsgOpened = XM_USER + 0x27;

    //文件服务器的功能
    public static final int XM_ULFILEHEAD = XM_USER + 0x28;
    public static final int XM_DLFILEHEAD = XM_USER + 0x29;
    public static final int XM_RCVFILEDATA = XM_USER + 0x2a;

    // OSIM 即时通讯系统采用的 TCP 通讯端口
//public static final int PORT=	8188
    public static final int PORT = 4000;
    public static final String IP = "120.41.45.130";

    //public static final int DATA_BUFSIZE	1024 * 2
    public static final int DATA_BUFSIZE = 1024 * 2;

    //最大一次传输4KB的文件
    public static final int TRANS_FILEBUFSIZE = 4 * 1024;

    public static final int STATUS_ONLINE = 1;
    public static final int STATUS_OFFLINE = 2;
}
