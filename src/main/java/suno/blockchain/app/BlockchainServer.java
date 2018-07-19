package suno.blockchain.app;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class BlockchainServer {
  private static final int PORT = 1234;
  private static final String MSG_SEPARATOR = "#";

  public static void main(String[] args) {
    BufferedWriter writer = null;
    BufferedReader reader = null;

    try {
      String buf = null;
      ServerSocket server = new ServerSocket(PORT);
      Socket client = server.accept();
      writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
      reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

      while (true) {
        buf = reader.readLine();
        if (buf != null) {
          writer.write(processMessage(buf));
          writer.flush();
        }
      }

    } catch (UnknownHostException e) {
      System.err.println("Don't know about host");
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to host");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static String processMessage(String buf) {
    String[] message = buf.split(MSG_SEPARATOR);

    for (int i = 0; i < message.length; i++) {
      System.out.print(message[i]);
    }
    System.out.println();

    return buf;
  }
}