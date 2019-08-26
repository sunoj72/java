package com.lgcns.suno.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ProcessUtil {
	public static void execute() {
		
	}
	
	public static String executeWithReturn(List<String> exec) throws IOException, InterruptedException {
		return executeWithReturn(exec, 1);
	}
	
	public static String executeWithReturn(List<String> exec, int waitLines) throws IOException, InterruptedException {
		// List.get(0) == Programm
		StringBuilder sb = new StringBuilder();
		
		ProcessBuilder pb = new ProcessBuilder(exec);
		//pb.redirectErrorStream(true);
		Process ps = pb.start();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(ps.getInputStream()));
		for (int i = 0; i < waitLines; i++) {
			sb.append(in.readLine());
		}
		
		ps.waitFor();
		in.close();
			
		return sb.toString();
	}

	public static void executeAndWait() {
		
	}

}