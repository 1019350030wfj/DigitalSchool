package com.onesoft.jaydenim.network;

import android.content.Context;

import com.onesoft.jaydenim.imaction.ReceiveAction;
import com.onesoft.jaydenim.utils.LogUtil;

import java.io.IOException;
import java.net.Socket;

public class NetService {

    private static NetService mInstance;

    private ClientListenerThread mClientListenThread;
    private ClientSendThread mClientSendThread;
    private NetConnect mConnect;
    private Socket mClientSocket;
    private boolean mIsConnected = false;
    private Context mContext;

    private NetService() {
    }

    public void onInit(Context context) {
        this.mContext = context;
    }

    public static NetService getInstance() {
        if (mInstance == null) {
            synchronized (NetService.class) {
                if (mInstance == null) {
                    mInstance = new NetService();
                }
            }
        }
        return mInstance;
    }

    public void setupConnection() {
        mConnect = new NetConnect();
        mConnect.startConnect();
        if (mConnect.getIsConnected()) {
            mIsConnected = true;
            mClientSocket = mConnect.getSocket();
            startListener(mClientSocket);
            isNeedReconnect(mClientSocket);
            mReconnectCount = 0;//连接成功后，重置
        } else {
            mIsConnected = false;
        }
    }

    private void isNeedReconnect(final Socket socket) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean status = true;
                while(status){
                    try{
                        if (mIsConnected == false && mClientSocket == null) {
                            //主动关闭
                            status = false;
                            return;
                        }
                        if (socket.isClosed() || !socket.isConnected()){
                            LogUtil.d("客户端断开！！");
                            status = false;
                            reConnect();
                            return;
                        }
                        Thread.sleep(1000);
                    }catch(Exception e){
                       LogUtil.d("服务器断开！！");
                        status = false;
                        reConnect();
                    }
                }
            }
        }).start();
    }

    public boolean isConnected() {
        return mIsConnected;
    }

    public void startListener(Socket socket) {
        mClientSendThread = new ClientSendThread(socket);
        mClientListenThread = new ClientListenerThread(mContext, socket);
        mClientListenThread.setReceiveListener(ReceiveAction.getInstance().getReceiveActionListener());
        mClientListenThread.start();
        mClientSendThread.start();
    }

    public void send(byte[] t) {
        if (mClientSendThread != null) {
            mClientSendThread.sendMessage(t);
        }
    }

    public void closeConnection() {
        if (mClientListenThread != null) {
            mClientListenThread.close();
        }
        if (mClientSendThread != null) {
            mClientSendThread.close();
        }
        try {
            if (mClientSocket != null)
                mClientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mIsConnected = false;
        mClientSocket = null;
    }

    private int mReconnectCount = 0;
    /**
     * 重连接
     */
    public void reConnect() {
        closeConnection();//先关闭以前的连接
        mReconnectCount++;//重连次数加1
        if (mReconnectCount > 3){//重连次数大于3，就不再连接了
            return;
        }
        setupConnection();
    }
}
