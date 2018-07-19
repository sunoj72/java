package socket.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient implements Runnable {
	private Socket client;
	private PrintWriter outputStream;
	private Scanner inputStream;
	private String addr = "localhost";
	private int port = 7777;
	private String nick;

	public ChatClient(String addr, int port) throws IOException {
    this.addr = addr;
    this.port = port;
	}

	private void connect() throws IOException {
		Scanner keyboard = new Scanner(System.in);
		// get user nick
		System.out.println("What is your nick?");
		nick = keyboard.next();

		// connect to server
		InetAddress host = null;
		try {
			host = InetAddress.getByName(this.addr);
		} catch (UnknownHostException e1) {
			System.out.println("Host not found");
		}
		System.out
				.println("You are now connected to: " + host.getHostAddress());

		client = null;
		try {
			client = new Socket(host, port);
			client.setReuseAddress(true);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("not found");
		}

		inputStream = new Scanner(client.getInputStream());
		outputStream = new PrintWriter(client.getOutputStream());

		// start new thread to listen from server
		// one runnable, two threads... in which cases is this legal?
		Thread thread = new Thread(this);
		thread.start();

		// continuously listen your user input
		while (keyboard.hasNextLine()) {
			String msg = keyboard.nextLine();
			outputStream.println(nick + " says: " + msg);
			outputStream.flush();
		}

		System.out.println("Clients closed..");
		keyboard.close();
		client.close();
		System.exit(0);
	}

	public static void main(String[] args) throws Exception {
    ChatClient client = new ChatClient(args[0], Integer.parseInt(args[1]));
    client.connect();
	}

	@Override
	public void run() {
		while (true) {
			if (inputStream.hasNextLine())
				System.out.println(inputStream.nextLine());
		}
	}
}