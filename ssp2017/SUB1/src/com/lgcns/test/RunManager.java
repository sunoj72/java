package com.lgcns.test;

public class RunManager {

	public static void main(String[] args) {
		Logics logics = new Logics();
		
		String filename = "LOGFILE_A.TXT";
		String report = "REPORT_1.TXT";
		String inputPath = ""; // "./INPUT";
		String outputPath = ""; // "./OUTPUT";
		
		logics.doProcess1(filename, report, inputPath, outputPath);
	}
}
