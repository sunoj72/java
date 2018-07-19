package socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import socket.controller.ClientHandler;

public class ChatServer {
  private static final int PORT = 7777;

  private ServerSocket server;
  private ArrayList<Socket> clients;

  public ChatServer() {
		try {
			server = new ServerSocket(PORT);
			server.setReuseAddress(true);
		} catch (IOException e)
		{
			System.out.println(e.getStackTrace());
    }

    clients = new ArrayList<>();
  }

  public ArrayList<Socket> getClients() {
    return clients;
  }

	public void startServer() throws IOException {
    System.out.println("Accepting clients...");

		while(true)
		{
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
      new ChatServer().startServer();
  }
}