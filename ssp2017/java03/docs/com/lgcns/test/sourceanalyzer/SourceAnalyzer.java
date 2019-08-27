package com.lgcns.test.sourceanalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SourceAnalyzer {
	
	int sourceCount = 0;
	int lineCount = 0;
	int patternCount = 0;
	int pFileCount = 0;
    /**
     * 전체 소스 개수를 반환하는 메소드.
     * @return
     */
    public int getJavaFileCount() {
        return sourceCount;
    }
    
    /**
     * 문제 소스 개수를 반환하는 메소드.
     * @return
     */
    public int getProblemFileCount() {
        return pFileCount;
    }
    
    /**
     * 소스 전체 Line 수를 반환하는 메소드.
     * @return
     */
    public int getTotalLineCount() {
        return lineCount;
    }
    
    /**
     * 검색된 패턴 수를 반환하는 메소드.
     * @return
     */
    public int getPatternCount() {
        return patternCount;
    }
    
	/**
	 * 디렉토리 하위 java 파일을 분석하는 메소드.
	 * 패턴을 검색할 소스코드는 주석을 포함한다.
	 * 
	 * @param dirName 분석 대상 디렉토리
	 * @param pattern 분석 대상 패턴
	 */
	public void analyze(String dirName, String pattern) {
		sourceCount = 0;
		lineCount = 0;
		patternCount = 0;
		pFileCount = 0;
		
		File file = new File(dirName);
		File[] listFile = file.listFiles();

		pattern = pattern.replace(" ","");
		Set<String> set = new HashSet<>();
		for(File f : listFile){
			if(f.getAbsolutePath().endsWith(".java")){
				sourceCount++;
				List<String> fileContent = readFile(f);
				String resultStr="";
				for(String str : fileContent){
					str=str.replace(" ", "");
					resultStr += str;
					if(!str.isEmpty()){
						lineCount++;
					}
				}
				while(resultStr.indexOf(pattern) >= 0){
					patternCount++;
					set.add(f.getAbsolutePath());
					resultStr = resultStr.substring(resultStr.indexOf(pattern)+pattern.length());
				}
			}
		}
		pFileCount = set.size();
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
