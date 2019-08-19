package com.lgcns.test.net.server;

public class Message {
  public static final String TOKEN_SEPARATOR = "#";

  private MessageEnum command;
  private String messageBody;
  private String cryptKey;

  public Message(MessageEnum cmd, String messageBody) {
    this.command = cmd;
    this.messageBody = messageBody;
  }

  public Message(MessageEnum cmd, String messageBody, String cryptKey) {
	    this.command = cmd;
	    this.messageBody = messageBody;
	    this.cryptKey = cryptKey;
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

  public String getCryptKey() {
    return this.cryptKey;
  }

  public void setCryptKey(String cryptKey) {
    this.cryptKey = cryptKey;
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