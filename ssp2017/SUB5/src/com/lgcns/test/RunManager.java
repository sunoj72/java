package com.lgcns.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RunManager {
	public static boolean DEBUG = true;
	
	public static void main(String[] args) throws Exception {
		Logics logics = new Logics();
		
		String filename = "LOGFILE_C.TXT";
		String report = "REPORT_5.TXT";
		String logPrefix = "TYPELOG_5_";
		String inputPath = ""; // "./INPUT";
		String outputPath = ""; // "./OUTPUT";
		
		String execName = "CODECONV.EXE";
		String execPath = ""; // "./EXEC";
		
		RunManager.printLogStart("APP");
		
		logics.doProcess5(filename, inputPath, report, logPrefix, outputPath, execName, execPath, 5);
		
		RunManager.printLogEnd("APP");
	}
	
	public static void printLog(String log) {
		if (!DEBUG) {
			return;
		}
		
		SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss.SSS");		
		System.out.println(String.format("%s %s", dateFormatLocal.format(new Date()), log));
	}
	
	public static void printLogStart(String log) {
		printLog(String.format("%s [START]", log));
	}
	
	public static void printLogEnd(String log) {
		printLog(String.format("%s [END]", log));
	}
}
