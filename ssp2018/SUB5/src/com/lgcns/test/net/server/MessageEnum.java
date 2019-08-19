package com.lgcns.test.net.server;

public enum MessageEnum {
  FILE("FILE", false),
  FILE2("FILE2", false),
  ACK("ACK", false),
  ERR("ERR", false),
  NUMBER("NUMBER", false),
  RESPONSE("RESPONSE", false);

  private String command;
  private boolean broadcast;

  MessageEnum(String cmd, boolean broadcast) {
    this.command = cmd;
    this.broadcast = broadcast;
  }

  public String getCommandString() {
    return this.command;
  }

  public boolean isBroadcast() {
    return this.broadcast;
  }

  public static MessageEnum getEnum(String value) {
    for(MessageEnum v : MessageEnum.values())
      if(v.getCommandString().equalsIgnoreCase(value)) return v;

      throw new IllegalArgumentException();
  }
}