package com.lgcns.suno.net.controller;

import com.lgcns.suno.net.model.Message;

public interface MessageHandler {
  public String processMessage(String msg);
  public Message processMessage(Message msg);
}