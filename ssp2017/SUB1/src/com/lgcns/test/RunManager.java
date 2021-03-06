package com.lgcns.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RunManager {
	public static boolean DEBUG = false;
	
	public static void main(String[] args) {
		Logics logics = new Logics();
		
		String filename = "LOGFILE_A.TXT";
		String report = "REPORT_1.TXT";
		String inputPath = ""; // "./INPUT";
		String outputPath = ""; // "./OUTPUT";
		
		logics.doProcess1(filename, report, inputPath, outputPath);
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
