package com.lgcns.test.net.client;

import com.lgcns.test.net.message.Message;
import com.lgcns.test.net.client.ClientMessageEnum;

public class ClientMessage implements Message {
  public static final String TOKEN_SEPARATOR = "#";

  private ClientMessageEnum command;
  private String messageBody;
  private String cryptKey;

  public ClientMessage(ClientMessageEnum cmd, String messageBody) {
    this.command = cmd;
    this.messageBody = messageBody;
  }

  public ClientMessage(ClientMessageEnum cmd, String messageBody, String cryptKey) {
	    this.command = cmd;
	    this.messageBody = messageBody;
	    this.cryptKey = cryptKey;
	  }
  
  
  public ClientMessageEnum getCommand() {
    return this.command;
  }

  public void setCommand(ClientMessageEnum cmd) {
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