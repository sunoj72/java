package com.lgcns.test.net.server;

public class Message {
  public static final String TOKEN_SEPARATOR = "#";

  private MessageEnum command;
  private String messageBody;

  public Message(MessageEnum cmd, String messageBody) {
    this.command = cmd;
    this.messageBody = messageBody;
  }

  public MessageEnum getCommand() {
    return this.command;
  }

  public void setCommand(MessageEnum cmd) {
    this.command = cmd;
  }

  public String getMessageBody() {
    return this.messageBody;
  }

  public void setMessageBody(String messageBody) {
    this.messageBody = messageBody;
  }

  @Override
  public String toString() {
    if (this.messageBody == null || this.messageBody.length() == 0) {
      return "";
    } else {
      return this.messageBody;
    }
  }
}