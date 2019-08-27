package com.lgcns.test.wordcount;

public class WordCount {
	
	/**
	 * 입력 받은 message에서 searchWord의 빈도수를 구하여 결과를 리턴한다.(구현)
	 * 
	 * @param message 입력받은 문자열
	 * @param searchWord 검색단어
	 * @return 검색단어의 빈도수
	 */
	public int countWord(String message, String searchWord) {
		
		int count = 0;
		String[] st = message.split(" ");
		
		for (int j = 0; j < st.length; j++) {
			if( searchWord.equals(st[j]) ){
				count++;
			}
		}
		
		return count;
	}

}
