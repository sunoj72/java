package com.lgcns.suno.net.controller;

import com.lgcns.suno.net.model.Message;

public class ServerSideMessageHandler implements MessageHandler {

  public synchronized Message processMessage(Message request) {
    // TODO: build Response Message
    return new Message(request.getCommand(), request.getMessageBody());
  }

  public synchronized String processMessage(String request) {
    // TODO: build Response Message
    return request;
  }
}