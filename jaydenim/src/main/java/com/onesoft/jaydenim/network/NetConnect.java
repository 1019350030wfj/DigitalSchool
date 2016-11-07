package com.onesoft.jaydenim.network;

import com.onesoft.jaydenim.imaction.ActionType;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 专门负责连接
 */
public class NetConnect {
	private Socket mClientSocket = null;
	private boolean mIsConnected = false;

	public NetConnect() {
	}

	public void startConnect() {
		try {
			mClientSocket = new Socket();
			mClientSocket.connect(
					new InetSocketAddress(ActionType.IP, ActionType.PORT), 3000);
			if (mClientSocket.isConnected()) {
				mIsConnected = true;
			} else {
				mIsConnected = false;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean getIsConnected() {
		return mIsConnected;
	}

	public Socket getSocket() {
		return mClientSocket;
	}

}
