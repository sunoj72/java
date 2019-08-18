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
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Scanner;

public class RunManager {
	private static final String DATA_PATH="./BIGFILE";

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String filename = scan.next();
		scan.close();

		try {
			filename = PathUtil.matchFirst("glob:**" + File.separator + filename, FileSystems.getDefault().getPath(DATA_PATH, "").toString());			

			File inFile = new File(filename);
			Compressor comp = new Compressor();
			InputStream in = new BufferedInputStream(new FileInputStream(inFile));

//			File outFile = FileSystems.getDefault().getPath("", filename).toFile();		
//			OutputStream out = new BufferedOutputStream(new FileOutputStream(outFile));
			OutputStream out = new BufferedOutputStream(System.out);
			comp.doLine3Compress(in, out);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
