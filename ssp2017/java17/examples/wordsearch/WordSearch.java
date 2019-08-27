package com.lgcns.test.wordsearch;

import java.util.ArrayList;

public class WordSearch {

	/**
	 * 해당 단어가 격자 내에 존재 하는 경우 시작좌표를 반환하는 메소드 (구현)
	 * 
	 * @param matrix
	 *            알파벳 대문자가 포함된 격자
	 * @param word
	 *            격자 내에서 찾을 단어
	 * @return 입력 받은 단어가 격자 내에 존재할 경우 시작좌표
	 */
	public String[] findWordCount(String[][] matrix, String word) {
		ArrayList<String> al = new ArrayList<String>();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (garofind(matrix, word, i, j) != null)
					al.add(garofind(matrix, word, i, j));
				if (serofind(matrix, word, i, j) != null)
					al.add(serofind(matrix, word, i, j));
				if (diagofind(matrix, word, i, j) != null)
					al.add(diagofind(matrix, word, i, j));
			}
		}
		String[] poslist = null;
		if (!al.isEmpty()) {
			poslist = new String[al.size()];
			for (int i = 0; i < poslist.length; i++) {
				poslist[i] = al.get(i);
			}
		}
		return poslist;
	}

	private String garofind(String[][] matrix, String word, int posx, int posy) {
		String pos = null;
		int length = matrix[posx].length;
		boolean correct = true;
		if (length - posy - word.length() < 0)
			return pos;
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) != matrix[posx][posy + i].charAt(0))
				correct = false;
		}
		if (correct) {
			pos = String.valueOf((char) ('a' + posx)).concat(String.valueOf(posy));
		}
		return pos;
	}

	private String serofind(String[][] matrix, String word, int posx, int posy) {
		String pos = null;
		int length = matrix.length;
		boolean correct = true;

		if (length - posx - word.length() < 0)
			return pos;
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) != matrix[posx + i][posy].charAt(0))
				correct = false;
		}
		if (correct) {
			pos = String.valueOf((char) ('a' + posx)).concat(String.valueOf(posy));
		}
		return pos;
	}

	private String diagofind(String[][] matrix, String word, int posx, int posy) {
		String pos = null;
		int garolength = matrix[posx].length;
		int serolength = matrix.length;
		boolean correct = true;
		if (garolength - posy - word.length() < 0
				|| serolength - posx - word.length() < 0)
			return pos;
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) != matrix[posx + i][posy + i].charAt(0))
				correct = false;
		}
		if (correct) {
			pos = String.valueOf((char) ('a' + posx)).concat(String.valueOf(posy));
		}
		return pos;
	}
}
