package p2p.server.p2pserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import p2p.server.peers.Peer;
import p2p.server.peers.PeerManager;

public class Listener extends Thread {
	private ServerSocket mServerSocket;
	private boolean mRunning = false;
	private int mPort;

	public boolean isRunning() {
		return mRunning;
	}

	public void setRunning(boolean mRunning) {
		this.mRunning = mRunning;
	}

	public int getPort() {
		return mPort;
	}

	public void close() {
		try {
			mServerSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Listener(int port) {
		Console.write("리스너스레드 생성.");

		try {
			this.mPort = port;
			mServerSocket = new ServerSocket(this.mPort);
			this.mRunning = true;
			this.start();
			Console.write(port + "번 포트에서 연결 대기중...\n");
		} catch (Exception e) {
			Console.error("리스너 생성 실패. 사용중인 포트 번호 입니다.");
			Main.endWork();
		}
	}

	@Override
	public void run() {
		try {
			while (mRunning) {
				Socket c = mServerSocket.accept();

				if (c != null) {
					Peer p = new Peer(PeerManager.popIndex(), c);
					PeerManager.add(p);

					Console.write(p.getRemoteAddress() + ":" + p.getRemotePort() + "로부터의 접속 수락");
				}

				Thread.sleep(10);
			}

			mServerSocket.close();
		} catch (Exception e) {
			//e.printStackTrace();
		}
		Console.write("리스너스레드 종료.");
	}

}
