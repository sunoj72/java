package com.lgcns.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.lgcns.test.util.PathUtil;
import com.lgcns.test.util.ProcessUtil;

public class Logics {
	public void doProcess1(String filename, String report, String inputPath, String outputPath) {
		HashMap<String, Integer> map = new HashMap<>();
		
		BufferedReader br = PathUtil.getReader(filename, inputPath);
		BufferedWriter bw = PathUtil.getWriter(report, outputPath);

		String msg;
		
		try {
			while ((msg = br.readLine()) != null) {
				String[] tokens = msg.split("#");

				if (!map.containsKey(tokens[1])) {
					map.put(tokens[1], 0);
				}
				map.put(tokens[1], map.get(tokens[1]) + 1) ;
			}
			br.close();

			Map<String, Integer> sortedMap = getSortedMap(map);

			for (Map.Entry<String, Integer> kv: sortedMap.entrySet()) {
				bw.write(String.format("%s#%d\n", kv.getKey(), kv.getValue()));
			}

			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doProcess2(String filename, String report, String inputPath, String outputPath) {
		HashMap<String, Integer> map = new HashMap<>();
		
		BufferedReader br = PathUtil.getReader(filename, inputPath);
		BufferedWriter bw = PathUtil.getWriter(report, outputPath);

		String msg = "";
		
		try {
			while ((msg = br.readLine()) != null) {
				String[] tokens = msg.split("#");

				if (!map.containsKey(tokens[1])) {
					map.put(tokens[1], 0);
				}
				map.put(tokens[1], map.get(tokens[1]) + 1) ;
			}
			br.close();

			Map<String, Integer> sortedMap = getSortedMap(map);

			for (Map.Entry<String, Integer> kv: sortedMap.entrySet()) {
				bw.write(String.format("%s#%d\n", kv.getKey(), kv.getValue()));
			}

			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	public void doProcess3(String filename, String inputPath, String report, String logPrefix, String outputPath, String execName, String execPath) {
		HashMap<String, Integer> map = new HashMap<>();
		HashMap<String, BufferedWriter> mapLog = new HashMap<>();
		
		BufferedReader br = PathUtil.getReader(filename, inputPath);
		BufferedWriter bw = PathUtil.getWriter(report, outputPath);

		String msg = "";

		try {
			while ((msg = br.readLine()) != null) {
				String[] tokens = msg.trim().split("#");

				if (!map.containsKey(tokens[1])) {
					map.put(tokens[1], 0);
					// TPYE별 로그파일
					mapLog.put(tokens[1], PathUtil.getWriter(String.format("%s%s.TXT", logPrefix, tokens[1]), outputPath));
				}

				// make report
				map.put(tokens[1], map.get(tokens[1]) + 1) ;

				// decode Message
				mapLog.get(tokens[1]).write(String.format("%s#%s#%s\n", tokens[0], tokens[1], getDecodedMessage(execName, execPath, tokens[2])));
			}
			br.close();

			Map<String, Integer> sortedMap = getSortedMap(map);

			for (Map.Entry<String, Integer> kv: sortedMap.entrySet()) {
				bw.write(String.format("%s#%d\n", kv.getKey(), kv.getValue()));
			}
			bw.close();

			for (Map.Entry<String, BufferedWriter> kv: mapLog.entrySet()) {
				try {
					kv.getValue().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void doProcess4(String filename, String inputPath, String report, String logPrefix, String outputPath, String execName, String execPath) {
		HashMap<String, Integer> map = new HashMap<>();
		ExecutorService threadPool = Executors.newFixedThreadPool(100);
		
		BufferedReader br = PathUtil.getReader(filename, inputPath);
		BufferedWriter bw = PathUtil.getWriter(report, outputPath);
	
		String msg = "";

		try {
			while ((msg = br.readLine()) != null) {
				String[] tokens = msg.trim().split("#");

				if (!map.containsKey(tokens[1])) {
					// 기존 TPYE별 로그파일이 존재하면 삭제
					Files.deleteIfExists(Paths.get(String.format("%s%s.TXT", logPrefix, tokens[1])));
					map.put(tokens[1], 0);

					// Execute Type Logs in ThreadPool(ExecutorService)
					LogWriterRunnable runnaable = new LogWriterRunnable(filename, inputPath, logPrefix, outputPath, execName, execPath, tokens[1]);
					threadPool.execute(runnaable);
				}

				// make report
				map.put(tokens[1], map.get(tokens[1]) + 1) ;
			}
			br.close();

			Map<String, Integer> sortedMap = getSortedMap(map);

			for (Map.Entry<String, Integer> kv: sortedMap.entrySet()) {
				bw.write(String.format("%s#%d\n", kv.getKey(), kv.getValue()));
			}
			bw.close();
			
			//wait to all threads closed.
			threadPool.shutdown();
			
			int i = 0;
			while(true) {
				if (threadPool.awaitTermination(100, TimeUnit.MILLISECONDS)) {
					break;
				}
				
				Thread.sleep(100);
				i++;
			}
			RunManager.printLog(String.format("awaitTermination count: %d", i));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static String getDecodedMessage(String execName, String execPath, String code) {
		String msg = "";
		
		try {
			String execFile = PathUtil.matchFirst(execName, execPath);
			ArrayList<String> args = new ArrayList<>();
			args.add(execFile);
			args.add(code);
			
			msg = ProcessUtil.executeWithReturn(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return msg;
	}
	
	private static Map<String, Integer> getSortedMap(Map<String, Integer> map) {
		Map<String, Integer> sortedMap = new TreeMap<String, Integer>(
			new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return o1.compareTo(o2);
				}
			}
		);

		sortedMap.putAll(map);

		return sortedMap;
	}

}
