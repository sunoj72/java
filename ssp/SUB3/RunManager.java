import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class RunManager {

	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
		String filename = "LOGFILE_B.TXT";
		String outputFile = "REPORT_3.TXT";
		String logFilePrefix = "TYPELOG_3_";
		String buf = "";
		HashMap<String, Integer> map = new HashMap<>();
		HashMap<String, BufferedWriter> mapLog = new HashMap<>();
		BufferedReader br = new BufferedReader(new FileReader(filename));

		while ((buf = br.readLine()) != null) {
			String[] tokens = buf.split("#");

			if (!map.containsKey(tokens[1])) {
				map.put(tokens[1], 0);
				mapLog.put(tokens[1], new BufferedWriter(new FileWriter(String.format("%s%s.TXT", logFilePrefix, tokens[1]))));
			}

			// make report
			map.put(tokens[1], map.get(tokens[1]) + 1) ;

			// decode Message
			mapLog.get(tokens[1]).write(String.format("%s#%s#%s\n", tokens[0], tokens[1], getDecodedMessage(tokens[2])));
		}
		br.close();

		Map<String, Integer> sortedMap = getSortedMap(map);

		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
		for (Map.Entry<String, Integer> kv: sortedMap.entrySet()) {
			bw.write(String.format("%s#%d\n", kv.getKey(), kv.getValue()));
		}
		bw.close();

		for (Map.Entry<String, BufferedWriter> kv: mapLog.entrySet()) {
			kv.getValue().close();
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

	private static String getDecodedMessage(String code) throws IOException, InterruptedException {
		String execFile = "CODECONV.EXE";
		String buf = null;
		ProcessBuilder pb = new ProcessBuilder(execFile, code);
		//pb.redirectErrorStream(true);
		Process ps = pb.start();
		BufferedReader in = new BufferedReader(new InputStreamReader(ps.getInputStream()));
		buf = in.readLine();
		ps.waitFor();
		in.close();

		return buf;
	}
}
