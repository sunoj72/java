package com.lgcns.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.util.Scanner;

import com.lgcns.suno.util.PathUtil;

public class RunManager {
	private static final String DATA_PATH="./BIGFILE";

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String filename = scan.next();
		scan.close();

		try {
			String filepath = PathUtil.matchFirst("glob:**" + File.separator + filename, FileSystems.getDefault().getPath(DATA_PATH, "").toString());
			if (filepath.length() == 0) {
				System.out.println("File does not found.");
			} else {
				System.out.println("File found at \"" + filepath + "\"");
			}
			
			File inFile = new File(filepath);
			Compressor comp = new Compressor();
			InputStream in = new BufferedInputStream(new FileInputStream(inFile));

			File outFile = FileSystems.getDefault().getPath("", filename).toFile();		
			OutputStream out = new BufferedOutputStream(new FileOutputStream(outFile));
//			OutputStream out = new BufferedOutputStream(System.out);
			comp.doLine3Compress(in, out);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
