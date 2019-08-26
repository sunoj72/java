package com.lgcns.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import com.lgcns.suno.util.PathUtil;

public class LogWriterThread  extends Thread {
	private String execName;
	private String execPath;
	private String logType;
	
	
	private BufferedReader input = null;
	private BufferedWriter logger = null;


	public LogWriterThread(String filename, String inputPath, String logPrefix, String outputPath, String execName, String execPath, String logType) {
		this.execName = execName;
		this.execPath = execPath;
		this.logType = logType;
		
		input = PathUtil.getReader(filename, inputPath);
		logger = PathUtil.getWriter(String.format("%s%s.TXT", logPrefix, logType), outputPath, true);
	}

	@Override
	public void run() {
		String msg = "";
		
        String threadName = Thread.currentThread().getName();
        System.out.println("Thread Name : " + threadName + " Start");
		
		try {
			while ((msg = input.readLine()) != null) {
				String[] tokens = msg.trim().split("#");

				try {
					if (tokens[1].contentEquals(logType)) {
						logger.write(String.format("%s#%s#%s\n", tokens[1], tokens[0], Logics.getDecodedMessage(execName, execPath, tokens[2])));
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
		
        System.out.println("Thread Name : " + threadName + " End");
		
	}

//	public void writeLog(String logType, String logDate, String logCode) {
//		try {
//			logger.write(String.format("%s#%s#%s\n", logDate, logType, Logics.getDecodedMessage(execName, execPath, logCode)));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
//	public void closeLog() {
//		try {
//			logger.flush();
//			logger.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
//	public void stopThread() {
//		closeLog();
//		this.running.set(false);
//	}
	
}