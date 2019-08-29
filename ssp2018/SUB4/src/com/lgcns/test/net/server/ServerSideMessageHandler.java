package com.lgcns.test.net.server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.util.List;
import java.util.Scanner;

import com.lgcns.suno.util.PathUtil;
import com.lgcns.test.Compressor;
import com.lgcns.test.RunManager;
import com.lgcns.test.net.message.MessageHandler;

public class ServerSideMessageHandler implements MessageHandler {
	private String filename = "";
	private Scanner scan = null;
	private List<String> encrypted = null;
	private int index = 0;
	private ServerMessage resp = null;

	public synchronized ServerMessage processMessage(ServerMessage request) {
		Compressor comp = new Compressor();
		
		switch (request.getCommand()) {
		case FILE:
			filename = request.getMessageBody();
			scan = getScanner(filename, RunManager.DATA_PATH);
			if (scan == null) {
				System.err.println("File does not exists");
				System.exit(-1);
			}
			
			encrypted = comp.doLine4Compress(scan, index++);

			// send 1 line
			if (encrypted.size() == 0) {
				System.err.println("Contents does not exists");
				System.exit(-1);
			}
			resp = ServerMessageBuilder.buildResponse(encrypted.get(0));
			break;


		case ACK:
			if (encrypted.size() == index) {
				System.err.println("Send Completed");
				System.exit(-1);
			}
			resp = ServerMessageBuilder.buildResponse(encrypted.get(index++));
			
			break;

		case ERR:
			if (resp == null) {
				System.err.println("System Error");
				System.exit(-1);
			}
			
			break;

		case NUMBER:
			try {
				int num = Integer.parseInt(request.getMessageBody());
				
				if (filename != "") {
					scan.close();
					scan = getScanner(filename, RunManager.DATA_PATH);
					
					if (scan == null) {
						System.err.println("File does not exists");
						System.exit(-1);
					}

					encrypted = comp.doLine4Compress(scan, num);
					index = 0;
					
					if (encrypted.size() == 0) {
						System.err.println("Contents does not exists");
						System.exit(-1);
					}
					resp = ServerMessageBuilder.buildResponse(encrypted.get(index++));
					
							
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

		return resp;
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