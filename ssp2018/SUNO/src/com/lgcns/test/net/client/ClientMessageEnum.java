package com.lgcns.test.net.client;

public enum ClientMessageEnum {
  ACK("ACK", false),
  ERR("ERR", false),
  NUMBER("NUMBER", false);

  private String command;
  private boolean broadcast;

  ClientMessageEnum(String cmd, boolean broadcast) {
    this.command = cmd;
    this.broadcast = broadcast;
  }

  public String getCommandString() {
    return this.command;
  }

  public boolean isBroadcast() {
    return this.broadcast;
  }

  public static ClientMessageEnum getEnum(String value) {
    for(ClientMessageEnum v : ClientMessageEnum.values())
      if(v.getCommandString().equalsIgnoreCase(value)) return v;

      throw new IllegalArgumentException();
  }
}