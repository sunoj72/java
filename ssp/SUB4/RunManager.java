import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class RunManager {

	public static void main(String[] args) throws Exception {
		String filename = "LOGFILE_B.TXT";
		String outputFile = "REPORT_4.TXT";
		String buf = "";
		HashMap<String, Integer> map = new HashMap<>();
		BufferedReader br = new BufferedReader(new FileReader(filename));

		while ((buf = br.readLine()) != null) {
			String[] tokens = buf.trim().split("#");

			if (!map.containsKey(tokens[1])) {
				Files.deleteIfExists(Paths.get(String.format("%s%s.TXT", "TYPELOG_4_", tokens[1])));
				map.put(tokens[1], 0);
			}

			// make report
			map.put(tokens[1], map.get(tokens[1]) + 1) ;

			// write logs
			LogWriterThread thread = new LogWriterThread(tokens[1], tokens[0], tokens[2]);
			thread.run();
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
