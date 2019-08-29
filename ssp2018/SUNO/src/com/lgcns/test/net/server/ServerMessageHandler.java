package com.lgcns.test.net.server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.util.List;
import java.util.Scanner;

import com.lgcns.test.examples.ServerApp;
import com.lgcns.test.net.message.Message;
import com.lgcns.test.net.message.MessageHandler;
import com.lgcns.test.util.PathUtil;

public class ServerMessageHandler implements MessageHandler {
	private String filename = "";
	private Scanner scan = null;
	private List<String> encrypted = null;
	private int index = 0;
	private ServerMessage response = null;
	private ServerMessage request = null;
//	private String cryptKey = null;

	@Override
	public synchronized ServerMessage processMessage(Message req) {
		request = (ServerMessage)req;
		
		switch (request.getCommand()) {
		case FILE2:
			filename = request.getMessageBody();
			scan = getScanner(filename, ServerApp.DATA_PATH);
			if (scan == null) {
				System.err.println("File does not exists");
				System.exit(-1);
			}
//			cryptKey = request.getCryptKey();
//			encrypted = comp.doLine5Compress(scan, index++, request.getCryptKey());

			// send 1 line
			if (encrypted.size() == 0) {
				System.err.println("Contents does not exists");
				System.exit(-1);
			}
			response = ServerMessageBuilder.buildResponse(encrypted.get(0));
			break;

		case FILE:
			filename = request.getMessageBody();
			scan = getScanner(filename, ServerApp.DATA_PATH);
			if (scan == null) {
				System.err.println("File does not exists");
				System.exit(-1);
			}
			
//			encrypted = comp.doLine4Compress(scan, index++);

			// send 1 line
			if (encrypted.size() == 0) {
				System.err.println("Contents does not exists");
				System.exit(-1);
			}
			response = ServerMessageBuilder.buildResponse(encrypted.get(0));
			break;


		case ACK:
			if (encrypted.size() == index) {
				System.err.println("Send Completed");
				System.exit(-1);
			}
			response = ServerMessageBuilder.buildResponse(encrypted.get(index++));
			
			break;

		case ERR:
			if (response == null) {
				System.err.println("System Error");
				System.exit(-1);
			}
			
			break;

		case NUMBER:
			try {
//				int num = Integer.parseInt(request.getMessageBody());
				
				if (filename != "") {
					scan.close();
//					scan = getScanner(filename, ServerApp.DATA_PATH);
					
					if (scan == null) {
						System.err.println("File does not exists");
						System.exit(-1);
					}

//					if (cryptKey == null) {
//						encrypted = comp.doLine4Compress(scan, num);
//					} else {
//						encrypted = comp.doLine5Compress(scan, num, cryptKey);
//					}
					
					index = 0;
					
					if (encrypted.size() == 0) {
						System.err.println("Contents does not exists");
						System.exit(-1);
					}
					response = ServerMessageBuilder.buildResponse(encrypted.get(index++));
					
							
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


	private Scanner getScanner(String filename, String path) {
		try {
			String filepath = PathUtil.matchFirst("glob:**" + File.separator + filename, FileSystems.getDefault().getPath(path, "").toString());
			File inFile = new File(filepath);
			InputStream in = new BufferedInputStream(new FileInputStream(inFile));
			return new Scanner(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}