package com.lgcns.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
}
