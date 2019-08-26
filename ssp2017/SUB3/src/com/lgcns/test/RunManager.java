package com.lgcns.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RunManager {

	public static void main(String[] args) {
		Logics logics = new Logics();
		
		String filename = "LOGFILE_B.TXT";
		String report = "REPORT_3.TXT";
		String logPrefix = "TYPELOG_3_";
		String inputPath = ""; // "./INPUT";
		String outputPath = ""; // "./OUTPUT";
		
		String execName = "CODECONV.EXE";
		String execPath = ""; // "./EXEC";
		
		SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss.SSS");		
		System.out.println(String.format("%s Start", dateFormatLocal.format(new Date())));
		
		logics.doProcess3(filename, inputPath, report, logPrefix, outputPath, execName, execPath);
		
		System.out.println(String.format("%s End", dateFormatLocal.format(new Date())));
	}

}
