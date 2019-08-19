package com.lgcns.test.net.server;

public class MessageBuilder {
  public static Message buildRequest(String msg) {
    if (msg == null || msg.length() == 0) {
      return null;
    }

      MessageEnum cmd =  null;
      
      try {
//          String[] tokens = tokenize(msg);
//          MessageEnum cmd =  MessageEnum.getEnum(tokens[0]);

    	  Integer.parseInt(msg);
    	  cmd = MessageEnum.NUMBER;
    	  
      } catch (NumberFormatException e) {
          if (msg.equals("ACK") || msg.equals("ERR")) {
              cmd =  MessageEnum.getEnum(msg);
          } else {
        	  cmd = MessageEnum.FILE;
          }
      }
      
      return new Message(cmd, msg);
  }

  public static Message buildResponse(String msg) {
	    if (msg == null || msg.length() == 0) {
	      return null;
	    }

	      MessageEnum cmd =  null;
	      
	      try {
//	          String[] tokens = tokenize(msg);
//	          MessageEnum cmd =  MessageEnum.getEnum(tokens[0]);

	    	  Integer.parseInt(msg);
	    	  cmd = MessageEnum.NUMBER;
	    	  
	      } catch (NumberFormatException e) {
	          if (msg.equals("ACK") || msg.equals("ERR")) {
	              cmd =  MessageEnum.getEnum(msg);
	          } else {
	        	  cmd = MessageEnum.FILE;
	          }
	      }
	      
	      return new Message(cmd, msg);
	  }
  
//
//  private static String[] tokenize(String msg) {
//    return tokenize(msg, Message.TOKEN_SEPARATOR);
//  }
//
//  private static String[] tokenize(String msg, String tokenSeparator) {
//    return msg.split(tokenSeparator);
//  }
}