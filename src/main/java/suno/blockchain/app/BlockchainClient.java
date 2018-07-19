package suno.blockchain.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class BlockchainClient {
  private static final String IP = "localhost";
  private static final int PORT = 1234;

  private static final String MSG_GET_BLOCK_LIST = "BLOCK_LIST";
  private static final String MSG_ADD_TRANSACTION = "TX";
  private static final String MSG_SEPARATOR = "#";

  public static void main(String[] args) {
    try {
      System.out.print(sendBlockList());
      System.out.print(sendTransaction());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static String sendBlockList() {
    StringBuilder sb = new StringBuilder();
    BufferedWriter writer = null;
    BufferedReader reader = null;

    try {
      String buf = null;
      Socket client = new Socket(IP, PORT);
      writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
      writer.write(MSG_GET_BLOCK_LIST);
      writer.flush();
      reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
      while ((buf = reader.readLine()) != null) {
        sb.append(buf);
      }
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host");
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to host");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (writer != null) {
          writer.close();
        }
        if (reader != null) {
          reader.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return sb.toString();
  }

  public static String sendTransaction() {
    StringBuilder sb = new StringBuilder();
    BufferedWriter writer = null;
    BufferedReader reader = null;

    try {
      String buf = null;
      Socket client = new Socket(IP, PORT);
      writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
      writer.write(MSG_GET_BLOCK_LIST);
      writer.flush();
      reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
      while ((buf = reader.readLine()) != null) {
        sb.append(buf);
      }
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host");
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to host");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (writer != null) {
          writer.close();
        }
        if (reader != null) {
          reader.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return sb.toString();
  }

}