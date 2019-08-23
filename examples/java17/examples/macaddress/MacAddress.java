package com.lgcns.test.macaddress;

public class MacAddress {
	
	/**
	 * 입력받은 MAC주소를 검증하고 Type 3 형식으로 변환하여 리턴한다 (구현)
	 * 
	 * @param inputMac 입력 MAC주소
	 * @return 변환된 MAC주소, 올바르지 않은 형식은 null을 리턴
	 */
	public String validateMacAddress(String inputMac) {
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		for (int i = 0; i < inputMac.length(); i++) {
			if( inputMac.charAt(i) == '-' ) count1++;
			if( inputMac.charAt(i) == ':' ) count2++;
			if( inputMac.charAt(i) == '.' ) count3++;
		}
		if( count1 > 0 ) {
			if( count2>0 || count3>0 ) {
				return null;
			}
		}
		if( count2 > 0 ) {
			if( count1>0 || count3>0 ) {
				return null;
			}
		}
		if( count3 > 0 ) {
			if( count1>0 || count2>0 ) {
				return null;
			}
		}
		
		inputMac = inputMac.replaceAll("\\.", "");
		inputMac = inputMac.replaceAll(":", "");
		inputMac = inputMac.replaceAll("-", "");
		inputMac = inputMac.toLowerCase();
		if( inputMac.length() != 12 ) {
			return null;
		}
		
		for (int i = 0; i < inputMac.length(); i++) {
			if(		!(
						( inputMac.charAt(i) >= '0' && inputMac.charAt(i) <= '9')
						|| ( inputMac.charAt(i) >= 'a' && inputMac.charAt(i) <= 'f')
					)
			  ) {
				return null;
			}
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(inputMac.substring(0, 4));
		sb.append(".");
		sb.append(inputMac.substring(4, 8));
		sb.append(".");
		sb.append(inputMac.substring(8, 12));
		
		return sb.toString();
	}

}
