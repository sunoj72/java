package com.lgcns.test.net.server;

public enum ServerMessageEnum {
  FILE("FILE", false),
  FILE2("FILE2", false),
  ACK("ACK", false),
  ERR("ERR", false),
  NUMBER("NUMBER", false),
  RESPONSE("RESPONSE", false);

  private String command;
  private boolean broadcast;

  ServerMessageEnum(String cmd, boolean broadcast) {
    this.command = cmd;
    this.broadcast = broadcast;
  }

  public String getCommandString() {
    return this.command;
  }

  public boolean isBroadcast() {
    return this.broadcast;
  }

  public static ServerMessageEnum getEnum(String value) {
    for(ServerMessageEnum v : ServerMessageEnum.values())
      if(v.getCommandString().equalsIgnoreCase(value)) return v;

      throw new IllegalArgumentException();
  }
}