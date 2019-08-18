package com.lgcns.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class RunManager {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		String filename = "LOGFILE_A.TXT";
		String outputFile = "REPORT_1.TXT";
		String buf = "";
		HashMap<String, Integer> map = new HashMap<>();
		BufferedReader br = new BufferedReader(new FileReader(filename));

		while ((buf = br.readLine()) != null) {
			String[] tokens = buf.split("#");

			if (!map.containsKey(tokens[1])) {
				map.put(tokens[1], 0);
			}
			map.put(tokens[1], map.get(tokens[1]) + 1) ;
		}
		br.close();

		Map<String, Integer> sortedMap = getSortedMap(map);

		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
		for (Map.Entry<String, Integer> kv: sortedMap.entrySet()) {
			bw.write(String.format("%s#%d\n", kv.getKey(), kv.getValue()));
		}

		bw.close();
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
