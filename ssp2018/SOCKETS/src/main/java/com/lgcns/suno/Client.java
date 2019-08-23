package com.lgcns.suno;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgcns.suno.net.client.NetworkClient;

public class Client {
    static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    private static String SERVER_IP = "";
	private static int LISTEN_PORT = 0;
	private static int SEND_INTERVAL = 0;

	public static void main(String[] args) {
		if (args.length != 2) {
			usage();
			System.exit(0);
		}
		
		try {
			String[] tmp = args[0].split(":");
			SERVER_IP = tmp[0];
			LISTEN_PORT = Integer.parseInt(tmp[1]);
			SEND_INTERVAL = Integer.parseInt(args[1]);
			System.out.println(String.format("Connect to: %s:%d, Packet Interval: %d sec", SERVER_IP, LISTEN_PORT, SEND_INTERVAL));
		} catch (NumberFormatException e) {
			System.out.println("Invalid Input Parameter(s)");
			usage();
			System.exit(0);
		}
		
		NetworkClient client = new NetworkClient(SERVER_IP, LISTEN_PORT, SEND_INTERVAL);
		client.startThread();
	}
	
	private static void usage() {
		System.out.println("com.lgcns.suno.Client <SERVER_IP>:<LISTEN_PORT> <INTERVAL>");
		System.out.println("\tINTERVAL: Set 0 to send nothing or send interval by seconds");
	}
}
