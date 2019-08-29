package com.lgcns.test.examples;

import java.io.IOException;

import com.lgcns.test.net.client.NetworkClient;

public class ClientApp {
	public static void main(String[] args) {
		String serverId = "127.0.0.1";
		int serverPort = 9876;
		
		NetworkClient client;
		
		try {
			client = new NetworkClient(serverId, serverPort);
			client.startThread();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
