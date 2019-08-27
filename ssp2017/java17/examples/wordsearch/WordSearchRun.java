package com.lgcns.test.wordsearch;

import java.util.Arrays;

public class WordSearchRun {
	
	public static void main(String [] args) {
		printResult(getMatrix(), new String[]{"BLACK", "RED", "GREEN", "BROWN", "WHITE", "BLUE", "ORANGE"});
	}
	
	public static void printResult(String [][] matrix, String [] words) {
		System.out.println("검색 단어 목록 : " + Arrays.toString(words));
		System.out.println("검색 결과");
		for(String word : words) {
			WordSearch wordSearch = new WordSearch();
			String [] result = wordSearch.findWordCount(matrix, word);
			if(result != null) {
				System.out.println("단어 : " + word + ", 시작좌표 : " + Arrays.toString(result));		
			} else {
				System.out.println("단어 : " + word + ", 시작좌표 : 없음");		
			}
		}
	}
	
	public static String [][] getMatrix() {
		String[][] matrix = {
	        { "B", "H", "E", "Z", "R", "B", "U", "O", "C", "Z" },
	        { "L", "S", "L", "Z", "K", "E", "O", "B", "S", "A" },
	        { "A", "Y", "P", "M", "D", "E", "D", "A", "Q", "Y" },
	        { "C", "W", "R", "P", "G", "D", "T", "N", "D", "I" },
	        { "K", "F", "U", "I", "R", "T", "Y", "K", "N", "N" },
	        { "B", "G", "P", "R", "E", "D", "G", "E", "W", "W" },
	        { "L", "G", "I", "Y", "E", "R", "O", "V", "U", "O" },
	        { "U", "N", "Y", "L", "N", "P", "U", "X", "W", "R" },
	        { "K", "V", "O", "E", "S", "I", "T", "B", "C", "B" },
	        { "I", "W", "N", "Z", "W", "H", "I", "T", "E", "Y" }	
		};
		return matrix;
	}

}
