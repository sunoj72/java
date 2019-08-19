package com.lgcns.test.net.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.Scanner;

public class ClientManager implements Runnable {
	private NetworkServer server;
	private Socket client;
	private Scanner scanner;

	public ClientManager(Socket client, NetworkServer server) {
		this.server = server;
		this.client = client;
	}

	@Override
	public void run() {
		try {
			InputStream in = client.getInputStream();
			scanner = new Scanner(in);

			while (true) {
				byte b[] = new byte[1024];
                int read = 0;
                if((read = in.read(b)) > 0)
                {
    				String line = new String(b);
    				Message msg = MessageBuilder.buildRequest(line.trim());
    				this.processMessage(msg);
                }
				
				
//				if (!scanner.hasNext())
//					return;

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized void processMessage(Message req) throws IOException {
		if (!client.isClosed()) {
			Message resp = server.getMessageHadler().processMessage(req);

			if (req.getCommand().isBroadcast()) {
				sendMessageToAll(resp);
			} else {
				sendMessage(resp);
			}
		}
	}
	
	public synchronized void sendMessage(Socket client, Message msg) throws IOException {
		if (!client.isClosed()) {
			PrintWriter pw = new PrintWriter(client.getOutputStream());
			pw.print(msg);
//			pw.println(msg);
			pw.flush();
			System.out.println(String.format("Sent to %s: [%s]", client.getRemoteSocketAddress(), msg));
		}
	}

	public synchronized void sendMessage(Message msg) throws IOException {
		sendMessage(this.client, msg);
	}

	public synchronized void sendMessageToAll(Message msg) {
		for (Iterator<Socket> it = server.getClients().iterator(); it.hasNext();) {
			try {
				Socket client = it.next();
				sendMessage(client, msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}