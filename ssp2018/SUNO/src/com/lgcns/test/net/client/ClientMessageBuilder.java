package com.lgcns.test.net.client;

public class ClientMessageBuilder {
	public static ClientMessage buildRequest(String msg) {
		if (msg == null || msg.length() == 0) {
			return null;
		}

		ClientMessageEnum cmd = null;

		try {
//          String[] tokens = tokenize(msg);
//          MessageEnum cmd =  MessageEnum.getEnum(tokens[0]);

			Integer.parseInt(msg);
			cmd = ClientMessageEnum.NUMBER;

		} catch (NumberFormatException e) {
			if (msg.equals("ACK") || msg.equals("ERR")) {
				cmd = ClientMessageEnum.getEnum(msg);
			}
		}

		return new ClientMessage(cmd, msg);
	}

	public static ClientMessage buildResponse(String msg) {
		if (msg == null || msg.length() == 0) {
			return null;
		}

		ClientMessageEnum cmd = null;

		try {
//	          String[] tokens = tokenize(msg);
//	          MessageEnum cmd =  MessageEnum.getEnum(tokens[0]);

			Integer.parseInt(msg);
			cmd = ClientMessageEnum.NUMBER;

		} catch (NumberFormatException e) {
			if (msg.equals("ACK") || msg.equals("ERR")) {
				cmd = ClientMessageEnum.getEnum(msg);
			}
		}

		return new ClientMessage(cmd, msg);
	}

	private static String[] tokenize(String msg) {
		return tokenize(msg, ClientMessage.TOKEN_SEPARATOR);
	}

	private static String[] tokenize(String msg, String tokenSeparator) {
		return msg.split(tokenSeparator);
	}
}