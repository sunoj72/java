package com.lgcns.suno;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgcns.suno.net.server.NetworkServer;

public class Server {
    static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
    
	private static int LISTEN_PORT = 0;
	private static int SEND_INTERVAL = 0;

	public static void main(String[] args) {
		if (args.length != 2) {
			usage();
			System.exit(0);
		}
		
		try {
			LISTEN_PORT = Integer.parseInt(args[0]);
			SEND_INTERVAL = Integer.parseInt(args[1]);
			System.out.println(String.format("Listen to: %d, Packet Interval: %d sec", LISTEN_PORT, SEND_INTERVAL));
		} catch (NumberFormatException e) {
			System.out.println("Integer Only");
			usage();
			System.exit(0);
		}
		
		NetworkServer server = new NetworkServer(LISTEN_PORT, SEND_INTERVAL);
		try {
			server.startServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void usage() {
		System.out.println("com.lgcns.suno.Server <LISTEN_PORT> <INTERVAL>");
		System.out.println("\tINTERVAL: Set 0 to send nothing or send interval by seconds");
	}
}
