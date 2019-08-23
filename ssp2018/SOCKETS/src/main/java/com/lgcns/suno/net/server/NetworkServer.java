package com.lgcns.suno.net.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkServer {
    static final Logger LOGGER = LoggerFactory.getLogger(NetworkServer.class);
	private int port = 0;
	private int interval = 0;
	private ServerSocket server;
	private NotificationManager nman = null;
	private ArrayList<Socket> clients;
	
	public class NotificationManager implements Runnable {
		private NetworkServer server = null;
		private int interval = 0;

		public NotificationManager(NetworkServer server, int interval) {
			this.server = server;
			this.interval = interval;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				String msg = "LOG LOG";
				LOGGER.info(String.format("Broadcast Message [%s]", msg));
				sendMessageToAll(msg);
				try {
					Thread.sleep(interval * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
	

	public NetworkServer(int port, int interval) {
		this.port = port;
		this.interval = interval;
		
		
		try {
			this.server = new ServerSocket(this.port);
			this.server.setReuseAddress(true);
			
			if (this.interval > 0) {
			}
		} catch (IOException e) {
			System.out.println(e.getStackTrace());
		}

		this.clients = new ArrayList<>();
	}

	public ArrayList<Socket> getClients() {
		return this.clients;
	}

	public void startServer() throws IOException {
		if (this.interval > 0) {
			this.nman = new NotificationManager(this, this.interval);
			Thread notify = new Thread(nman);
			notify.start();
		}
		
		System.out.println("Accepting clients...");
		
		while (true) {
			// wait for a client
			Socket client = server.accept();
			clients.add(client);
			System.out.println("New client accepted..." + client.getRemoteSocketAddress());
			System.out.println("Total Connections: " + clients.size());

			ClientManager handler = new ClientManager(client, this);
			Thread thread = new Thread(handler);
			thread.start();
		}
	}

	public synchronized void closeClient(Socket client) {
		try {
			System.out.println(String.format("Client Closed %s", client.getRemoteSocketAddress()));
			client.close();
			clients.remove(client);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void sendMessage(Socket client, String msg) {
		try {
			if (!client.isClosed()) {
				PrintWriter pw = new PrintWriter(client.getOutputStream());
				pw.println(msg);
				pw.flush();

				System.out.println(String.format("Sent to %s: [%s]", client.getRemoteSocketAddress(), msg));
			} else {
				closeClient(client);
			}
		} catch (IOException e) {
			closeClient(client);
		}
		
	}


	public synchronized void sendMessageToAll(String msg) {
		for (Iterator<Socket> it = clients.iterator(); it.hasNext();) {
			try {
				Socket client = it.next();
				sendMessage(client, msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
//	public static void main(String[] args) throws IOException {
//		new NetworkServer().startServer();
//	}
}
