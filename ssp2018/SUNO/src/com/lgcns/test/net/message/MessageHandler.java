package com.lgcns.test.net.message;

public interface MessageHandler {
//  public String processMessage(String msg);
  public Message processMessage(Message msg);
}