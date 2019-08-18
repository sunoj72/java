package com.lgcns.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

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
			String linePrev = compreassString(scan.nextLine());
			while (scan.hasNext()) {
				String lineNew = compreassString(scan.nextLine());
				
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
			String linePrev = encryptAndCompressString(scan.nextLine());
			while (scan.hasNext()) {
				String lineNew = encryptAndCompressString(scan.nextLine());
				
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

	public String compreassString(String buff) {
		try {
			char[] buffC = buff.toCharArray();
			StringBuilder sb = new StringBuilder();

			int charCounter = 0;
			char charPrev = buffC[0];
			for(char charNew : buffC) {
				if (charPrev == charNew) {
					charCounter++;
					continue;
				} else {
					if (charCounter > 2) {
						sb.append(String.format("%d%s", charCounter, charPrev));
					} else if (charCounter == 2) {
						sb.append(String.format("%s%s", charPrev, charPrev));
					} else {
						sb.append(charPrev);
					}
					charCounter = 1;					
					charPrev = charNew;
				}
			}
			
			if (charCounter > 2) {
				sb.append(String.format("%d%s", charCounter, charPrev));
			} else if (charCounter == 2) {
				sb.append(String.format("%s%s", charPrev, charPrev));
			} else {
				sb.append(charPrev);
			}
			
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String encryptString1(String buff) {
		
		return null;
	}

	public String encryptString2(String buff) {
		return null;
	}
	
	public String encryptAndCompressString(String buff, int mode) {
		if (mode == 0) {
			return encryptString1(compreassString(buff));
		} else {
			return encryptString2(compreassString(buff));
		}
		
	}

}
