package com.lgcns.test.net.server;

public class ServerMessageBuilder {
  public static ServerMessage buildRequest(String msg) {
    if (msg == null || msg.length() == 0) {
      return null;
    }

      ServerMessageEnum cmd =  null;
      
      try {
//          String[] tokens = tokenize(msg);
//          MessageEnum cmd =  MessageEnum.getEnum(tokens[0]);

    	  Integer.parseInt(msg);
    	  cmd = ServerMessageEnum.NUMBER;
    	  
      } catch (NumberFormatException e) {
          if (msg.equals("ACK") || msg.equals("ERR")) {
              cmd =  ServerMessageEnum.getEnum(msg);
          } else {
        	  if (msg.contains(ServerMessage.TOKEN_SEPARATOR)) {
    	          String[] tokens = tokenize(msg);
        		  cmd = ServerMessageEnum.FILE2;
            	  return new ServerMessage(cmd, tokens[0], tokens[1]);
        	  } else {
            	  cmd = ServerMessageEnum.FILE;
        	  }
          }
      }
      
	  return new ServerMessage(cmd, msg);
  }

  public static ServerMessage buildResponse(String msg) {
	    if (msg == null || msg.length() == 0) {
	      return null;
	    }

	      ServerMessageEnum cmd =  null;
	      
	      try {
//	          String[] tokens = tokenize(msg);
//	          MessageEnum cmd =  MessageEnum.getEnum(tokens[0]);

	    	  Integer.parseInt(msg);
	    	  cmd = ServerMessageEnum.NUMBER;
	    	  
	      } catch (NumberFormatException e) {
	          if (msg.equals("ACK") || msg.equals("ERR")) {
	              cmd =  ServerMessageEnum.getEnum(msg);
	          } else {
	        	  cmd = ServerMessageEnum.FILE;
	          }
	      }
	      
	      return new ServerMessage(cmd, msg);
	  }
  

  private static String[] tokenize(String msg) {
    return tokenize(msg, ServerMessage.TOKEN_SEPARATOR);
  }

  private static String[] tokenize(String msg, String tokenSeparator) {
    return msg.split(tokenSeparator);
  }
}