package com.onesoft.jaydenim.network;

import android.content.Context;

import com.onesoft.jaydenim.imaction.ReceiveAction.IReceiveActionListener;
import com.onesoft.jaydenim.utils.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;

/**
 *监听服务器返回的数据
 */
public class ClientListenerThread extends Thread {
    private Socket mSocket = null;
    private InputStream mOis;

    private boolean isStart = true;
    private Context mContext;
    private IReceiveActionListener mReceiveActionListener;

    public ClientListenerThread(Context context,Socket socket) {
        this.mContext = context;
        this.mSocket = socket;
        try {
            mOis = mSocket.getInputStream();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSocket(Socket socket) {
        this.mSocket = socket;
    }

    @Override
    public void run() {
        try {
            isStart = true;
            while (isStart) {
                if (mOis.available() > 0) {
                    byte[] byteTemp = new byte[mOis.available()];
                    mOis.read(byteTemp);
                    LogUtil.d("socket","接受成功 = " + byteTemp);
                    if (mReceiveActionListener != null) {
                        mReceiveActionListener.onReceive(byteTemp);
                    }
                }
            }
        } catch (IOException e1) {
            LogUtil.d("socket"," read exception");
            e1.printStackTrace();
            NetService.getInstance().reConnect();
        }
    }

    public void close() {
        isStart = false;
        try {
            mOis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setReceiveListener(IReceiveActionListener listener) {
        this.mReceiveActionListener = listener;
    }
}
