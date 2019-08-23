package com.lgcns.test.wordcount;

public class WordCountRun {
	
	public static void main(String [] args) {
		printResult("I LOVE LG LG CNS^^", "LG");
		printResult("I LOVE LG I LOVE CNS", "I");
	}
	
	public static void printResult(String message, String searchWord) {
		WordCount wordCount = new WordCount();
		System.out.println("입력된 문자열 : " + message);
		System.out.println("검색 단어 : " + searchWord);
		System.out.println("빈도 수 : " + wordCount.countWord(message, searchWord));
		System.out.println("");
	}

}
