package com.lgcns.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import com.lgcns.suno.util.PathUtil;

public class LogWriterRunnable implements Runnable {
	private String execName;
	private String execPath;
	private String logType;
	
	
	private BufferedReader input = null;
	private BufferedWriter logger = null;


	public LogWriterRunnable(String filename, String inputPath, String logPrefix, String outputPath, String execName, String execPath, String logType) {
		this.execName = execName;
		this.execPath = execPath;
		this.logType = logType;
		
		input = PathUtil.getReader(filename, inputPath);
		logger = PathUtil.getWriter(String.format("%s%s.TXT", logPrefix, logType), outputPath, true);
	}

	@Override
	public void run() {
		String msg = "";
		
        String threadName = Thread.currentThread().getName().toUpperCase();
		RunManager.printLogStart(threadName);
		
		try {
			while ((msg = input.readLine()) != null) {
				String[] tokens = msg.trim().split("#");

				try {
					if (tokens[1].contentEquals(logType)) {
						logger.write(String.format("%s#%s#%s\n", tokens[0], tokens[1], Logics.getDecodedMessage(execName, execPath, tokens[2])));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			input.close();
			
			logger.flush();
			logger.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		RunManager.printLogEnd(threadName);
	}
}
