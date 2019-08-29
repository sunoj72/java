package com.lgcns.test.net.client;

import java.util.List;
import java.util.Scanner;

import com.lgcns.test.net.message.Message;
import com.lgcns.test.net.message.MessageHandler;
import com.lgcns.test.net.client.ClientMessage;

public class ClientMessageHandler implements MessageHandler {
	private String filename = "";
	private Scanner scan = null;
	private List<String> encrypted = null;
	private int index = 0;
	private ClientMessage response = null;
	private ClientMessage request = null;

	@Override
	public synchronized ClientMessage processMessage(Message req) {
		request = (ClientMessage)request;
		switch (request.getCommand()) {
		case ACK:
			if (encrypted.size() == index) {
				System.err.println("Send Completed");
				System.exit(-1);
			}
			response = ClientMessageBuilder.buildResponse(encrypted.get(index++));
			
			break;

		case ERR:
			if (response == null) {
				System.err.println("System Error");
				System.exit(-1);
			}
			
			break;

		case NUMBER:
			try {
				if (filename != "") {
					scan.close();
//					scan = getScanner(filename, ServerApp.DATA_PATH);
					
					if (scan == null) {
						System.err.println("File does not exists");
						System.exit(-1);
					}

					index = 0;
					
					if (encrypted.size() == 0) {
						System.err.println("Contents does not exists");
						System.exit(-1);
					}
					response = ClientMessageBuilder.buildResponse(encrypted.get(index++));
					
							
				} else {
					System.err.println("Filename is undefined");
					System.exit(-1);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
			break;
		default:
			break;
		}

		return response;
	}

//	private Scanner getScanner(String filename, String path) {
//		try {
//			String filepath = PathUtil.matchFirst("glob:**" + File.separator + filename, FileSystems.getDefault().getPath(path, "").toString());
//			File inFile = new File(filepath);
//			InputStream in = new BufferedInputStream(new FileInputStream(inFile));
//			return new Scanner(in);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}
}