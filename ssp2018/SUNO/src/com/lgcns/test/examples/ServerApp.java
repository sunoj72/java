package com.lgcns.test.examples;

import java.io.IOException;

import com.lgcns.test.net.server.NetworkServer;

public class ServerApp {
	public static final String DATA_PATH="./BIGFILE";

	public static void main(String[] args) {
		NetworkServer server = new NetworkServer();
		
		try {
			server.startServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
