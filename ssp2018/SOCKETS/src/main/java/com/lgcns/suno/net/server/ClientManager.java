package com.lgcns.suno.net.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientManager implements Runnable {
    static final Logger LOGGER = LoggerFactory.getLogger(ClientManager.class);

	private NetworkServer server;
	private Socket client;

	public ClientManager(Socket client, NetworkServer server) {
		this.server = server;
		this.client = client;
	}

	@Override
	public void run() {
		try {
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
			server.closeClient(client);
		}
	}
	
	public synchronized void sendMessage(Socket client, String msg) {
		try {
			if (!client.isClosed()) {
				PrintWriter pw = new PrintWriter(client.getOutputStream());
				pw.println(msg);
				pw.flush();
				System.out.println(String.format("Sent to %s [%s]", client.getRemoteSocketAddress(), msg));
			} else {
				server.closeClient(client);
			}
		} catch (IOException e) {
			server.closeClient(client);
		}
		
	}

	public synchronized void sendMessage(String msg) {
		sendMessage(this.client, msg);
	}

	public synchronized void sendMessageToAll(String msg) {
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