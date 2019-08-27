package com.lgcns.test.macaddress;

public class MacAddressRun {
	
	public static void main(String [] args) {
		printResult("01:23:45:67:89:ab");
		printResult("0123.AbcD.Ef99");
		printResult("78-90-AB-CD-EF-GH");
	}
	
	public static void printResult(String inputMac) {
		MacAddress macAddress = new MacAddress();
		System.out.println("inputMac : " + inputMac);
		String result  = macAddress.validateMacAddress(inputMac);
		if(result != null) {
			System.out.println("올바른 MAC 주소입니다.");
			System.out.println(result);
			
		} else {
			System.out.println("올바르지 않은 MAC 주소입니다.");
		}
		System.out.println("");
		
	}

}
