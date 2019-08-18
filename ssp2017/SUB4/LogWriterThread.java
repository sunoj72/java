import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogWriterThread implements Runnable {
  private String logFilePrefix = "TYPELOG_4_";
  private String logType;
  private String logDate;
  private String logCode;

  public LogWriterThread(String logType, String logDate, String logCode){
    this.logType = logType;
    this.logDate = logDate;
    this.logCode = logCode;
  }

  @Override
  public void run() {
    BufferedWriter bw = null;
    try {
      bw = new BufferedWriter(new FileWriter(String.format("%s%s.TXT", logFilePrefix, logType), true));
      bw.write(String.format("%s#%s#%s\n", logDate, logType, getDecodedMessage(logCode)));
    } catch (Exception e) {
      e.printStackTrace();;
    } finally {
      try {
        bw.close();
      } catch (Exception e) {}
    }
  }

  private String getDecodedMessage(String code) throws IOException, InterruptedException {
    String execFile = "CODECONV.EXE";
    String buf = null;
    ProcessBuilder pb = new ProcessBuilder(execFile, code);
    Process ps = pb.start();
    BufferedReader in = new BufferedReader(new InputStreamReader(ps.getInputStream()));
    buf = in.readLine();
    ps.waitFor();
    in.close();

    return buf;
  }

}