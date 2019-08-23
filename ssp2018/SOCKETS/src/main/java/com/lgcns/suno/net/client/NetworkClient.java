package com.lgcns.suno.net.client;

import java.io.IOException;
import java.io.InputStream;
import java.lang.IllegalArgumentException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgcns.suno.Client;


public class NetworkClient implements Runnable {
    static final Logger LOGGER = LoggerFactory.getLogger(NetworkClient.class);

	private Thread thread = null;
	private Socket client;
	private String addr = "localhost";
	private int port = 7777;
	private int interval = 0;
	
	public class NotificationManager implements Runnable {
		private NetworkClient client = null;
		private int interval = 0;

		public NotificationManager(NetworkClient client, int interval) {
			this.client = client;
			this.interval = interval;
		}
		
		@Override
		public void run() {
		}

	}

	public NetworkClient(String addr, int port, int interval) {
	    this.addr = addr;
	    this.port = port;
	    this.interval = interval;
	}

	public void connect() throws IOException {
		// connect to server
		InetAddress host = null;
		try {
			host = InetAddress.getByName(this.addr);
		} catch (UnknownHostException e1) {
			System.out.println("Host not found");
		}

		try {
			client = new Socket(host, port);
			client.setReuseAddress(true);
			client.setSoTimeout(15000);
			
			System.out.println("You are now connected to: " + client.getRemoteSocketAddress() + ", local(" + client.getLocalPort() + ")");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("not found");
		}
		
	}

	public void close() {
		try {
			client.close();
			System.out.println("Clients closed..");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startThread() {
		try {
			connect();
			thread = new Thread(this);
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
			close();
		}

	}

	public void stopThread() {
	    if (thread != null) {
	      thread.interrupt();
	    }
	}

	public synchronized void sendMessage(String msg) {
		try {
			if (!this.client.isClosed()) {
				PrintWriter pw = new PrintWriter(this.client.getOutputStream());
				pw.println(msg);
				pw.flush();
				System.out.println(String.format("Sent to %s: [%s]", this.client.getRemoteSocketAddress(), msg));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void readMessage(String msg) {
		System.out.println(String.format("Received in thread: [%s]", msg));
	}

	@Override
	public void run() {
		try {
//			Scanner inputStream = new Scanner(client.getInputStream());
//
//			while (true) {
//				if (inputStream.hasNextLine()) {
//					String msg = inputStream.nextLine();
//					readMessage(msg);
//				}
//			}
			InputStream in = client.getInputStream();

			while (true) {
				byte b[] = new byte[1024];
                if((in.read(b)) > 0)
                {
    				String line = new String(b);
    				System.out.println(String.format("Received from %s: [%s]", client.getRemoteSocketAddress(), line.trim()));
                }
			}
		} catch (IOException e) {
			e.printStackTrace();
			close();
			System.exit(-1);
		}
	}
}