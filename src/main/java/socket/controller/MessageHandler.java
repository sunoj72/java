package socket.controller;

import socket.model.Message;

public interface MessageHandler {
  public String processMessage(String msg);
  public Message processMessage(Message msg);
}