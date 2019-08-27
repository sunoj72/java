package com.lgcns.test;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.lgcns.test.util.PathUtil;

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
