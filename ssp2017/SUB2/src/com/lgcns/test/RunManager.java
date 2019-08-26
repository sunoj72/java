package com.lgcns.test;

public class RunManager {

	public static void main(String[] args) {
		Logics logics = new Logics();
		
		String filename = "LOGFILE_B.TXT";
		String report = "REPORT_2.TXT";
		String inputPath = ""; // "./INPUT";
		String outputPath = ""; // "./OUTPUT";
		
		logics.doProcess2(filename, report, inputPath, outputPath);
	}
}
