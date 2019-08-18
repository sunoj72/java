package com.lgcns.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;


public class RunManager {
	private static final String DATA_PATH="./BIGFILE";

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String filename = scan.next();
		scan.close();

		try {
			Path cur = FileSystems.getDefault().getPath(DATA_PATH, filename);
			File inFile = cur.toFile();
			Compressor comp = new Compressor();
			InputStream in = new BufferedInputStream(new FileInputStream(inFile));

			File outFile = FileSystems.getDefault().getPath("", filename).toFile();
			OutputStream out = new BufferedOutputStream(new FileOutputStream(outFile));
//			OutputStream out = new BufferedOutputStream(System.out);
			comp.doLineCompress(in, out);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
