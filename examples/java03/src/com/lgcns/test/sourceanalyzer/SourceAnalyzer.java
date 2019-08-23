package com.lgcns.test.sourceanalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SourceAnalyzer {
	
    /**
     * 전체 소스 개수를 반환하는 메소드.
     * @return
     */
    public int getJavaFileCount() {
        return 0;
    }
    
    /**
     * 문제 소스 개수를 반환하는 메소드.
     * @return
     */
    public int getProblemFileCount() {
        return 0;
    }
    
    /**
     * 소스 전체 Line 수를 반환하는 메소드.
     * @return
     */
    public int getTotalLineCount() {
        return 0;
    }
    
    /**
     * 검색된 패턴 수를 반환하는 메소드.
     * @return
     */
    public int getPatternCount() {
        return 0;
    }
    
	/**
	 * 디렉토리 하위 java 파일을 분석하는 메소드.
	 * 패턴을 검색할 소스코드는 주석을 포함한다.
	 * 
	 * @param dirName 분석 대상 디렉토리
	 * @param pattern 분석 대상 패턴
	 */
	public void analyze(String dirName, String pattern) {
	}
	
	/**
	 * 제공되는 파일의 내용을 line 단위로 읽어 String List로 리턴하는 메소드(제공).
	 * @param file
	 * @return
	 */
	private List<String> readFile(File file) {
		List<String> strList = new ArrayList<String>();

		BufferedReader buffer = null;
		try {
			buffer = new BufferedReader(new FileReader(file));
			String line;
			while ((line = buffer.readLine()) != null) {
				strList.add(line);
			}
		} catch (IOException e) {
			System.out.println("IOException occurred. " + e.getMessage());
		} finally {
			if (buffer != null) {
				try {
					buffer.close();
				} catch (Exception e) {
					System.out.println("Error occurred while closing buffer.");
				}
			}
		}
		return strList;
	}
}
