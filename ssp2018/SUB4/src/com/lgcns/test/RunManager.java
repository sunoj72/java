package com.lgcns.test;

import java.io.IOException;

import com.lgcns.test.net.server.NetworkServer;

public class RunManager {
	public static final String DATA_PATH="./BIGFILE";

	public static void main(String[] args) {
		NetworkServer server = new NetworkServer();
		
		try {
			server.startServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
//		Scanner scan = new Scanner(System.in);
//		String filename = scan.next();
//		scan.close();

//		try {
//			String filepath = PathUtil.matchFirst("glob:**" + File.separator + filename, FileSystems.getDefault().getPath(DATA_PATH, "").toString());
//
//			File inFile = new File(filepath);
//			Compressor comp = new Compressor();
//			InputStream in = new BufferedInputStream(new FileInputStream(inFile));
//
//			File outFile = FileSystems.getDefault().getPath("", filename).toFile();		
//			OutputStream out = new BufferedOutputStream(new FileOutputStream(outFile));
////			OutputStream out = new BufferedOutputStream(System.out);
//			comp.doLine3Compress(in, out);
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
