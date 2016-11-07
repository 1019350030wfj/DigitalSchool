package com.onesoft.jaydenim.network;

import com.onesoft.jaydenim.utils.ByteUtils;
import com.onesoft.jaydenim.utils.LogUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ClientSendThread extends Thread {

    private Socket mSocket;
    private OutputStream oos;
    private boolean isStart = true;
    private byte[] mBytes;

    public ClientSendThread(Socket socket) {
        this.mSocket = socket;
        try {
            oos = mSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            isStart = true;
            while (isStart) {
                byte[] data = getTempByte();
                if (data != null && data.length > 0) {
                    oos.write(data);
                    oos.flush();
                    LogUtil.d("socket","write data");
                }
            }
        } catch (IOException e1) {
            LogUtil.d("socket","write IOException");
            e1.printStackTrace();
            NetService.getInstance().reConnect();
        }
    }

    public void sendMessage(byte[] t) {
        synchronized (ClientSendThread.class) {
            if (mBytes == null) {
                mBytes = t;
            } else {
                mBytes = ByteUtils.byteMerger(mBytes,t);
            }
        }
    }

    private byte[] getTempByte() {
        byte[] temp = null;
        synchronized (ClientSendThread.class) {
            temp = mBytes;
            mBytes = null;
        }
        return temp;
    }

    public void close() {
        isStart = false;
        try {
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
