package com.lgcns.test.net.server;

import com.lgcns.test.net.message.Message;

public class ServerMessage implements Message {
  public static final String TOKEN_SEPARATOR = "#";

  private ServerMessageEnum command;
  private String messageBody;
  private String cryptKey;

  public ServerMessage(ServerMessageEnum cmd, String messageBody) {
    this.command = cmd;
    this.messageBody = messageBody;
  }

  public ServerMessage(ServerMessageEnum cmd, String messageBody, String cryptKey) {
	    this.command = cmd;
	    this.messageBody = messageBody;
	    this.cryptKey = cryptKey;
	  }
  
  
  public ServerMessageEnum getCommand() {
    return this.command;
  }

  public void setCommand(ServerMessageEnum cmd) {
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