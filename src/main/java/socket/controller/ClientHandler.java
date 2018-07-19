package socket.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.Scanner;

import socket.server.ChatServer;

public class ClientHandler implements Runnable {
	private ChatServer server;
  private Socket client;
  private Scanner inputStream;

  public ClientHandler(Socket client, ChatServer server) {
    this.server = server;
    this.client = client;
  }

	@Override
	public void run() {
		try {
			inputStream = new Scanner(client.getInputStream());
			while (true) {
				if(!inputStream.hasNext())
					return;
				String msg = inputStream.nextLine();
				this.sendMessageToAll(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
    }
  }

	public synchronized void processMessage(Socket client, String msg) throws IOException {
    if (!client.isClosed()) {
      PrintWriter pw = new PrintWriter(client.getOutputStream());
      pw.println(msg);
      pw.flush();
      //System.out.println("Sent to: " + client.getRemoteSocketAddress());
    }
  }

	public synchronized void sendMessageToAll(String msg) throws IOException {
		for(Iterator<Socket> it = server.getClients().iterator(); it.hasNext();) {
      Socket client = it.next();

			if (!client.isClosed()) {
				PrintWriter pw = new PrintWriter(client.getOutputStream());
				pw.println(msg);
				pw.flush();
				System.out.println("Sent to: " + client.getRemoteSocketAddress());
	}
		}
	}

}