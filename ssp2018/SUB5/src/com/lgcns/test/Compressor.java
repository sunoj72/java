package com.lgcns.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.lgcns.suno.util.CryptoUtil;

public class Compressor {
	private static final String COUNTER_FLAG = "#";	
	
	public void doLineCompress (InputStream in, OutputStream out) {
		Scanner scan = new Scanner(in);

		try {
			OutputStreamWriter writer = new OutputStreamWriter(out);
			
			int lineCounter = 1;
			String linePrev = scan.nextLine();
			while (scan.hasNext()) {
				String lineNew = scan.nextLine();
				
				if (linePrev.equals(lineNew)) {
					lineCounter++;
					continue;
				} else {
					if (lineCounter > 1) {
						writer.write(String.format("%d%s%s%s", lineCounter, COUNTER_FLAG, linePrev, System.lineSeparator()));
					} else {
						writer.write(String.format("%s%s", linePrev, System.lineSeparator()));
					}
					lineCounter = 1;					
					linePrev = lineNew;
				}
				
				writer.flush();
			}
			
			if (lineCounter > 1) {
				writer.write(String.format("%d%s%s%s", lineCounter, COUNTER_FLAG, linePrev, System.lineSeparator()));
			} else {
				writer.write(String.format("%s%s", linePrev, System.lineSeparator()));
			}
			
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			scan.close();
		}
	}
	
	public void doLine2Compress (InputStream in, OutputStream out) {
		Scanner scan = new Scanner(in);

		try {
			OutputStreamWriter writer = new OutputStreamWriter(out);
			
			int lineCounter = 1;
			String linePrev = CryptoUtil.compreassString(scan.nextLine());
			while (scan.hasNext()) {
				String lineNew = CryptoUtil.compreassString(scan.nextLine());
				
				if (linePrev.equals(lineNew)) {
					lineCounter++;
					continue;
				} else {
					if (lineCounter > 1) {
						writer.write(String.format("%d%s%s%s", lineCounter, COUNTER_FLAG, linePrev, System.lineSeparator()));
					} else {
						writer.write(String.format("%s%s", linePrev, System.lineSeparator()));
					}
					lineCounter = 1;					
					linePrev = lineNew;
				}
				
				writer.flush();
			}
			
			if (lineCounter > 1) {
				writer.write(String.format("%d%s%s%s", lineCounter, COUNTER_FLAG, linePrev, System.lineSeparator()));
			} else {
				writer.write(String.format("%s%s", linePrev, System.lineSeparator()));
			}
			
			writer.flush();
			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scan.close();
		}
	}

	public void doLine3Compress (InputStream in, OutputStream out) {
		Scanner scan = new Scanner(in);

		try {
			OutputStreamWriter writer = new OutputStreamWriter(out);
			
			int lineCounter = 1;
			String linePrev = CryptoUtil.encryptAndCompressString(scan.nextLine(), CryptoUtil.ENCRYPT_CAESAR, null);
			while (scan.hasNext()) {
				String lineNew = CryptoUtil.encryptAndCompressString(scan.nextLine(), CryptoUtil.ENCRYPT_CAESAR, null);
				
				if (linePrev.equals(lineNew)) {
					lineCounter++;
					continue;
				} else {
					if (lineCounter > 1) {
						writer.write(String.format("%d%s%s%s", lineCounter, COUNTER_FLAG, linePrev, System.lineSeparator()));
					} else {
						writer.write(String.format("%s%s", linePrev, System.lineSeparator()));
					}
					lineCounter = 1;					
					linePrev = lineNew;
				}
				
				writer.flush();
			}
			
			if (lineCounter > 1) {
				writer.write(String.format("%d%s%s%s", lineCounter, COUNTER_FLAG, linePrev, System.lineSeparator()));
			} else {
				writer.write(String.format("%s%s", linePrev, System.lineSeparator()));
			}
			
			writer.flush();
			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scan.close();
		}
	}

	public List<String> doLine4Compress (Scanner scan, int skipLines) {
		List<String> encrpyted = new ArrayList<>();

		try {
			
			if (skipLines > 0) {
				for (int i = 0; i < (skipLines - 1); i++) {
					scan.nextLine();
				}
			}
			
			int lineCounter = 1;
			String linePrev = CryptoUtil.encryptAndCompressString(scan.nextLine(), CryptoUtil.ENCRYPT_CAESAR, null);
			
			while (scan.hasNext()) {
				String lineNew = CryptoUtil.encryptAndCompressString(scan.nextLine(), CryptoUtil.ENCRYPT_CAESAR, null);
				
				if (linePrev.equals(lineNew)) {
					lineCounter++;
					continue;
				} else {
					if (lineCounter > 1) {
						encrpyted.add(String.format("%d%s%s", lineCounter, COUNTER_FLAG, linePrev));
					} else {
						encrpyted.add(String.format("%s", linePrev, System.lineSeparator()));
					}
					lineCounter = 1;					
					linePrev = lineNew;
				}
			}
			
			if (lineCounter > 1) {
				encrpyted.add(String.format("%d%s%s", lineCounter, COUNTER_FLAG, linePrev));
			} else {
				encrpyted.add(String.format("%s", linePrev));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return encrpyted;
	}

	public List<String> doLine5Compress (Scanner scan, int skipLines, String cryptKey) {
		List<String> encrpyted = new ArrayList<>();

		try {
			
			if (skipLines > 0) {
				for (int i = 0; i < (skipLines - 1); i++) {
					scan.nextLine();
				}
			}
			
			int lineCounter = 1;
			String linePrev = null;
			if (cryptKey == null) {
				linePrev = CryptoUtil.encryptAndCompressString(scan.nextLine(), CryptoUtil.ENCRYPT_CAESAR, null);
			} else {
				linePrev = CryptoUtil.encryptAndCompressString(scan.nextLine(), CryptoUtil.ENCRYPT_KEYWORD, cryptKey);
			}
			
			while (scan.hasNext()) {
				String lineNew = null;
				if (cryptKey == null) {
					lineNew = CryptoUtil.encryptAndCompressString(scan.nextLine(), CryptoUtil.ENCRYPT_CAESAR, null);
				} else {
					lineNew = CryptoUtil.encryptAndCompressString(scan.nextLine(), CryptoUtil.ENCRYPT_KEYWORD, cryptKey);
				}
				
				if (linePrev.equals(lineNew)) {
					lineCounter++;
					continue;
				} else {
					if (lineCounter > 1) {
						encrpyted.add(String.format("%d%s%s", lineCounter, COUNTER_FLAG, linePrev));
					} else {
						encrpyted.add(String.format("%s", linePrev, System.lineSeparator()));
					}
					lineCounter = 1;					
					linePrev = lineNew;
				}
			}
			
			if (lineCounter > 1) {
				encrpyted.add(String.format("%d%s%s", lineCounter, COUNTER_FLAG, linePrev));
			} else {
				encrpyted.add(String.format("%s", linePrev));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return encrpyted;
	}
	
}
