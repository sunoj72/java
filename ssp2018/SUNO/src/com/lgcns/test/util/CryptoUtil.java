package com.lgcns.test.util;

public class CryptoUtil {
	public static final int ENCRYPT_CAESAR = 0;
	public static final int ENCRYPT_KEYWORD = 1;

	public static String compreassString(String buff) {
		try {
			char[] buffC = buff.toCharArray();
			StringBuilder sb = new StringBuilder();

			int charCounter = 0;
			char charPrev = buffC[0];
			for(char charNew : buffC) {
				if (charPrev == charNew) {
					charCounter++;
					continue;
				} else {
					if (charCounter > 2) {
						sb.append(String.format("%d%s", charCounter, charPrev));
					} else if (charCounter == 2) {
						sb.append(String.format("%s%s", charPrev, charPrev));
					} else {
						sb.append(charPrev);
					}
					charCounter = 1;					
					charPrev = charNew;
				}
			}
			
			if (charCounter > 2) {
				sb.append(String.format("%d%s", charCounter, charPrev));
			} else if (charCounter == 2) {
				sb.append(String.format("%s%s", charPrev, charPrev));
			} else {
				sb.append(charPrev);
			}
			
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String encryptStringByCaesar(String buff) {
		String encryptKey = "VWXYZABCDEFGHIJKLMNOPQRSTU";
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < buff.length(); i++) {
			char tmp = buff.charAt(i);
			if (tmp >= 'A' && tmp <= 'Z') {
				int idx = tmp - 'A';
				sb.append(encryptKey.charAt(idx));
			} else {
				sb.append(tmp);
			}
		}
		
		return sb.toString();
	}

	public static String encryptStringByKeyword(String buff, String key) {
		String encryptKey = null;
		StringBuilder sb = new StringBuilder();
		
		for (char i = 'A'; i <= 'Z'; i++) {
			boolean skip = false;
			for (int j = 0; j < key.length(); j++) {
				if (i == key.charAt(j)) {
					skip = true;
					break;
				}
			}
			
			if (!skip) {
				sb.append(i);
			}
		}
		encryptKey = key + sb.toString();
		
		sb = new StringBuilder();
		for (int i = 0; i < buff.length(); i++) {
			char tmp = buff.charAt(i);
			if (tmp >= 'A' && tmp <= 'Z') {
				int idx = tmp - 'A';
				sb.append(encryptKey.charAt(idx));
			} else {
				sb.append(tmp);
			}
		}
		
		return sb.toString();
	}
	
	public static String encryptAndCompressString(String buff, int mode, String key) {
		if (mode == ENCRYPT_CAESAR) {
			return encryptStringByCaesar(compreassString(buff));
		}
		
		return encryptStringByKeyword(compreassString(buff), key);
	}


}
