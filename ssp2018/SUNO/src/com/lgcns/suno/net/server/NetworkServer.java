package com.lgcns.suno.net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.lgcns.suno.net.controller.ClientHandler;
import com.lgcns.suno.net.controller.MessageHandler;
import com.lgcns.suno.net.controller.ServerSideMessageHandler;

public class NetworkServer {
	private static final int PORT = 7777;

	private ServerSocket server;
	private ArrayList<Socket> clients;
	private MessageHandler messageHandler;

	public NetworkServer() {
		try {
			messageHandler = new ServerSideMessageHandler();
			server = new ServerSocket(PORT);
			server.setReuseAddress(true);
		} catch (IOException e) {
			System.out.println(e.getStackTrace());
		}

		this.clients = new ArrayList<>();
	}

	public ArrayList<Socket> getClients() {
		return this.clients;
	}

	public MessageHandler getMessageHadler() {
		return this.messageHandler;
	}

	public void setMessageHadler(MessageHandler handler) {
		this.messageHandler = handler;
	}

	public void startServer() throws IOException {
		System.out.println("Accepting clients...");

		while (true) {
			// wait for a client
			Socket client = server.accept();
			clients.add(client);
			System.out.println("New client accepted..." + client.getRemoteSocketAddress());
			System.out.println("Total Users: " + clients.size());

			ClientHandler handler = new ClientHandler(client, this);
			Thread thread = new Thread(handler);
			thread.start();
		}
	}

	public static void main(String[] args) throws IOException {
		new NetworkServer().startServer();
	}
}