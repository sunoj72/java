package com.lgcns.test.net.server;

public interface MessageHandler {
//  public String processMessage(String msg);
  public ServerMessage processMessage(ServerMessage msg);
}