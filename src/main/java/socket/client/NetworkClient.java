package socket.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import socket.model.Message;
import socket.util.MessageBuilder;
// import socket.controller.MessageHandler;
// import socket.controller.ClientSideMessageHandler;

public class NetworkClient implements Runnable {
// public class NetworkClient {
	// private MessageHandler messageHandler;
	private Thread thread = null;
	private Socket client;
	private String addr = "localhost";
	private int port = 7777;

	public NetworkClient(String addr, int port) throws IOException {
		// this.messageHandler = new ClientSideMessageHandler();
    this.addr = addr;
    this.port = port;
	}

	private void connect() throws IOException {
		// connect to server
		InetAddress host = null;
		try {
			host = InetAddress.getByName(this.addr);
		} catch (UnknownHostException e1) {
			System.out.println("Host not found");
		}
		System.out.println("You are now connected to: " + host.getHostAddress());

		client = null;
		try {
			client = new Socket(host, port);
			client.setReuseAddress(true);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("not found");
		}
	}

	private void close() throws IOException {
		client.close();
		System.out.println("Clients closed..");
	}

	private void startThread() {
		thread = new Thread(this);
		thread.start();
	}

	private void stopThread() {
		thread.stop();
	}

	public synchronized void sendMessage(String msg) throws IOException {
		if (!this.client.isClosed()) {
			PrintWriter pw = new PrintWriter(this.client.getOutputStream());
			pw.println(msg);
			pw.flush();
			System.out.println(String.format("Sent to %s: [%s]", this.client.getRemoteSocketAddress(), msg));
		}
	}

	public synchronized void sendMessage(Message msg) throws IOException {
		sendMessage(msg.toString());
	}

	public synchronized Message readMessage() throws IOException {
		Scanner inputStream = new Scanner(client.getInputStream());

		while(true) {
			if (inputStream.hasNextLine()) {
				String line = inputStream.nextLine();
				Message response = MessageBuilder.build(line);
				System.out.println(String.format("Received: [%s]", line));
				return response;
			}
		}
	}

	public synchronized Message sendMessagewithResponse(String msg) throws IOException {
		this.sendMessage(msg);
		return this.readMessage();
	}

	public synchronized Message sendMessagewithResponse(Message msg) throws IOException {
		this.sendMessage(msg);
		return this.readMessage();
	}

	public static void main(String[] args) throws Exception {
    NetworkClient networkClient = new NetworkClient(args[0], Integer.parseInt(args[1]));
		networkClient.connect();

		//TODO: process request Message
		String requestMessage = "CMD1#hello";

		if (args.length > 2) {
			requestMessage = args[2];
		}

		networkClient.startThread();

		Message request = MessageBuilder.build(requestMessage);
		networkClient.sendMessage(request);

		//TODO: process response Message
		// Message resp = networkClient.readMessage();


		Thread.sleep(5000);

		networkClient.stopThread();
		networkClient.close();

		System.exit(0);
	}

	@Override
	public void run() {
		try {
			Scanner inputStream = new Scanner(client.getInputStream());

			while (true) {
				if (inputStream.hasNextLine())
					System.out.println(String.format("Received in thread: [%s]", inputStream.nextLine()));
			}
		} catch (IOException e) {
			System.out.println(e.getStackTrace());
		}


	}

}