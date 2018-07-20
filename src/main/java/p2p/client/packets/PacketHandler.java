package p2p.client.packets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;

import p2p.client.p2ppeer.Console;
import p2p.client.p2ppeer.Main;
import p2p.client.peers.Peer;
import p2p.client.peers.PeerManager;
import p2p.client.sharing.FileManager;
import p2p.client.sharing.SharedFile;

public class PacketHandler {
	public static final byte TYPE_CHECK_PEER				= 0;
	public static final byte TYPE_HEART_BEAT				= 2;
	public static final byte TYPE_REGISTER_PEER			= 3;
	public static final byte TYPE_REGISTER_FILE			= 4;
	public static final byte TYPE_SEARCH_FILE				= 5;
	public static final byte TYPE_SEARCH_PEER			= 6;
	public static final byte TYPE_REQUEST_FILE			= 7;
	public static final byte TYPE_TRANSFER_FILE			= 8;
	public static final byte TYPE_LIST_PEERS				= 9;
	public static final byte TYPE_RELAY_PEERS				= 10;

	public static final byte TYPE_GENERAL_MESSAGE	= 100;

	public static final byte GMSG_INVALID_REQUEST				= 0;
	public static final byte GMSG_NOT_REGISTERED				= 1;
	public static final byte GMSG_HOLE_PUNCHING_FAILED		= 2;
	public static final byte GMSG_NAME_IN_USE						= 3;
	public static final byte GMSG_INVALID_NAME						= 4;
	public static final byte GMSG_FILE_REGISTER_FAILED		= 5;
	public static final byte GMSG_SEARCH_FILE_FAILED			= 6;
	public static final byte GMSG_SEARCH_PEER_FAILED			= 7;
	public static final byte GMSG_FILE_NOT_FOUND					= 8;

	private static SharedFile fIncomming;
	private static BufferedWriter wIncomming;

	public static void doPacket(Peer peer, Packet packet) {
		String msg[];
		SharedFile tSharedFile;
		//System.out.println("Packet: (" + peer.getRemoteAddress() + ":" + peer.getRemotePort()+ ") (t: " + packet.getType() + "), (o: " + packet.getOption() + ") " + packet.getPayload());

		switch (packet.getType()) {
		case TYPE_CHECK_PEER:
			Console.write("실제 연결 대기중인 주소: " + packet.getPayload());
			if (packet.getOption() == 1) {
				peer.setLeaderPeer(true);
				Console.write("당신은 리더피어 입니다.");
			} else {
				peer.setLeaderPeer(false);
				Console.write("당신은 일반피어 입니다.");
			}
			Main.setConsolCanRead(true);
			break;
		case TYPE_HEART_BEAT:
			peer.setLastHeartbeat(Calendar.getInstance().getTimeInMillis());
			//peer.write(packet);
			//Console.write("Heartbeat 수신");
			break;
		case TYPE_REGISTER_PEER:
			Console.write("사용자 이름이 등록되었습니다.");
			Main.setConsolCanRead(true);
			break;
		case TYPE_REGISTER_FILE:
			msg = packet.getPayload().split("\t");
			tSharedFile = FileManager.getFile(msg[0]);
			if (tSharedFile != null) {
				tSharedFile.setFileId(Integer.parseInt(msg[1]));
				tSharedFile.setFileStatus(FileManager.STATUS_SHARED);
				Console.write(msg[0] + " 파일이 추가되었습니다.");
			}
			Main.setConsolCanRead(true);
			break;
		case TYPE_SEARCH_FILE:
			msg = packet.getPayload().split("\n");
			Console.write("=======================================================");
			Console.write(" 파일  ID | 파일 크기 |         파  일  이  름         ");
			Console.write("=======================================================");
			String form = " %8s | %9s | %s ";
			for (String sl: msg) {
				String[] ss = sl.split("\t");
				Console.write(String.format(form, ss[0], ss[2], ss[1]));
			}
			if (packet.getOption() == 1) Console.write("=======================================================");
			Main.setConsolCanRead(true);
			break;
		case TYPE_SEARCH_PEER:
			msg = packet.getPayload().split("\t");
			Console.write("사용 가능한 피어: " + msg[1] + ":" + msg[2]);
			Main.setConsolCanRead(true);
			break;
		case TYPE_REQUEST_FILE:
			SharedFile f = FileManager.getFile(Integer.parseInt(packet.getPayload()));
			if (f == null) {
				if (PeerManager.get(0).isLeaderPeer() || PeerManager.get(0).isHolePunched()) {
					String auth = RelayManager.addRelay(peer, packet.getPayload());
					packet.setType(TYPE_RELAY_PEERS);
					packet.setOption((byte) 0);
					packet.setPayload(auth + "\t" + packet.getPayload());
					PeerManager.get(0).write(packet);
				} else {
					packet.setType(TYPE_GENERAL_MESSAGE);
					packet.setOption(GMSG_FILE_NOT_FOUND);
					packet.setPayload("");
					peer.write(packet);
					peer.setRunning(false);
				}
			} else {
				packet.setPayload(f.getFileId() + "\t" + peer.getRemoteAddress() + "\t" + peer.getRemotePort());
				PeerManager.get(0).write(packet);

				packet.setType(TYPE_TRANSFER_FILE);
				String ts = f.getFileId() + "\t" + f.getFileName() + "\t";
				try {
					packet.setOption((byte) 0);

					@SuppressWarnings("resource")
					BufferedReader br = new BufferedReader(new FileReader(f.getFileName()));
					char fbuf[] = new char[Packet.MAX_PAYLOAD_SIZE];
					int tn = ts.getBytes().length;
					System.arraycopy(ts.toCharArray(), 0, fbuf, 0, tn);
					int nread = br.read(fbuf, tn, Packet.MAX_PAYLOAD_SIZE - tn);
					packet.setPayload(fbuf, tn + nread);
					peer.write(packet);

					packet.setOption((byte) 1);
					nread = br.read(fbuf);
					while (nread > 0) {
						packet.setPayload(fbuf, nread);
						peer.write(packet);
						nread = br.read(fbuf);
					}

					packet.setOption((byte) 2);
					packet.setPayload("");
					peer.write(packet);

					packet.setPayload(f.getFileId() + "\t" + peer.getRemoteAddress() + "\t" + peer.getRemotePort());
					PeerManager.get(0).write(packet);
					//peer.setRunning(false);
				} catch (Exception e) { e.printStackTrace(); }
			}
			break;
		case TYPE_TRANSFER_FILE:
			if (packet.getOption() == 0) {
				try {
					msg = packet.getPayload().split("\t");
					fIncomming = new SharedFile(msg[1]);
					fIncomming.setFileId(Integer.parseInt(msg[0]));
					FileManager.addFile(fIncomming);

					wIncomming = new BufferedWriter(new FileWriter(fIncomming.getFileName()));
					wIncomming.write(msg[2]);
				} catch (IOException e) { }
				Main.setConsolCanRead(true);
			} else if (packet.getOption() == 1) {
				if (wIncomming == null) return;
				try { wIncomming.write(packet.getPayloadArray()); } catch (Exception e) {}
			} else if (packet.getOption() == 2) {
				if (wIncomming == null) return;
				try { wIncomming.close(); } catch (Exception e) {}
				File file = new File(fIncomming.getFileName());
				fIncomming.setFileSize(file.length());
				fIncomming.setFileMD5(SharedFile.MakeMD5(fIncomming.getFileName()));
				fIncomming.setFileStatus(FileManager.STATUS_NOT_COMMIT);

				packet.setType(PacketHandler.TYPE_REGISTER_FILE);
				packet.setOption((byte) 0);
				packet.setPayload(fIncomming.getFileName() + "\t" + fIncomming.getFileSize() + "\t" + fIncomming.getFileMD5());
				PeerManager.get(0).write(packet);
				peer.setRunning(false);
				Main.setConsolCanRead(true);
			}
			break;
		case TYPE_LIST_PEERS:
			Console.write("========================================");
			Console.write("     이름    |    주    소    |  포트   ");
			Console.write("========================================");
			form = " %12s|%16s|%8s ";
			msg = packet.getPayload().split("\n");
			for (String s: msg) {
				String[] ss = s.split("\t");
				if (ss.length == 3)
					Console.write(String.format(form, ss[0], ss[1], ss[2]));
			}
			Console.write("========================================");
			Main.setConsolCanRead(true);
			break;
		case TYPE_RELAY_PEERS:
			if (packet.getOption() == 0) {
				msg = packet.getPayload().split("\t");
				Console.write("(릴레이 준비) 인증코드: " + msg[1]);
				RelayManager.replaceAuth(msg[0], msg[1]);
			} else if (packet.getOption() == 1) {
				msg = packet.getPayload().split("\t");
				Console.write("(릴레이 실패) 파일 ID: " + msg[1]);
				Peer p = RelayManager.getPeer(msg[0]);
				if (RelayManager.removeRelay(msg[0]) == RelayManager.TYPE_RELAY_TO) {
					packet.setType(TYPE_TRANSFER_FILE);
					packet.setOption((byte) 0);
					packet.setPayload("");
					p.write(packet);
				}
			} else if (packet.getOption() == 2) {
				msg = packet.getPayload().split("\t");
				Console.write("(릴레이 전송) 파일 ID: " + msg[1]);

				f = FileManager.getFile(Integer.parseInt(msg[1]));

				Socket c = new Socket();
				try {
					c.setTcpNoDelay(true);
					c.setSendBufferSize(1);
					c.connect(new InetSocketAddress(msg[2], Integer.parseInt(msg[3])));
					//c = new Socket(msg[2], Integer.parseInt(msg[3]));
				} catch (Exception e) {
				}
				if (!c.isConnected()) { return; }

				Peer p = new Peer(PeerManager.popIndex(), c);
				PeerManager.add(p);

				Packet toSrv = new Packet();
				toSrv.setType(TYPE_TRANSFER_FILE);
				toSrv.setOption((byte) 0);
				toSrv.setPayload(msg[1] + "\t" + msg[2] + "\t" + msg[3]);
				PeerManager.get(0).write(toSrv);

				String ts = msg[0] + "\t" + msg[1] + "\t" + f.getFileName() + "\t";
				try {
					packet.setOption((byte) 3);

					@SuppressWarnings("resource")
					BufferedReader br = new BufferedReader(new FileReader(f.getFileName()));
					char fbuf[] = new char[Packet.MAX_PAYLOAD_SIZE];
					int tn = ts.getBytes().length;
					System.arraycopy(ts.toCharArray(), 0, fbuf, 0, tn);
					int nread = br.read(fbuf, tn, Packet.MAX_PAYLOAD_SIZE - tn);
					packet.setPayload(fbuf, tn + nread);
					p.write(packet);

					packet.setOption((byte) 4);
					while (nread > 0) {
						ts = msg[0] + "\t";
						tn = ts.getBytes().length;
						System.arraycopy(ts.toCharArray(), 0, fbuf, 0, tn);
						nread = br.read(fbuf, tn, Packet.MAX_PAYLOAD_SIZE - tn);
						packet.setPayload(fbuf, tn + nread);
						if (nread < 1) break;
						p.write(packet);
					}

					packet.setOption((byte) 5);
					packet.setPayload(msg[0] + "\t" + f.getFileId());
					p.write(packet);
					//p.setRunning(false);
				} catch (Exception e) {}
			} else if (packet.getOption() == 3) {
				msg = packet.getPayload().split("\t");
				if (RelayManager.getType(msg[0]) == RelayManager.TYPE_RELAY_TO) {
					Peer p = RelayManager.getPeer(msg[0]);
					Packet toSrv = new Packet();
					toSrv.setType(TYPE_TRANSFER_FILE);
					toSrv.setOption((byte) 0);
					toSrv.setPayload(msg[1] + "\t" + p.getRemoteAddress() + "\t" + p.getRemotePort());
					PeerManager.get(0).write(toSrv);

					packet.setType(TYPE_TRANSFER_FILE);
					packet.setOption((byte) 0);
					packet.setPayload(msg[1] + "\t" + msg[2] + "\t" + msg[3]);
					p.write(packet);
				} else	if (RelayManager.getType(msg[0]) == RelayManager.TYPE_RELAY_SELF) {
					try {
						msg = packet.getPayload().split("\t");
						fIncomming = new SharedFile(msg[2]);
						fIncomming.setFileId(Integer.parseInt(msg[1]));
						FileManager.addFile(fIncomming);

						wIncomming = new BufferedWriter(new FileWriter(fIncomming.getFileName()));
						wIncomming.write(msg[3]);
					} catch (IOException e) { }
				}
			} else if (packet.getOption() == 4) {
				msg = packet.getPayload().split("\t");
				if (RelayManager.getType(msg[0]) == RelayManager.TYPE_RELAY_TO) {
					Peer p = RelayManager.getPeer(msg[0]);
					packet.setType(TYPE_TRANSFER_FILE);
					packet.setOption((byte) 1);
					packet.setPayload(msg[1]);
					p.write(packet);
				} else	if (RelayManager.getType(msg[0]) == RelayManager.TYPE_RELAY_SELF) {
					if (wIncomming == null) return;
					try { wIncomming.write(msg[1]); } catch (Exception e) {}
				}
			} else if (packet.getOption() == 5) {
				msg = packet.getPayload().split("\t");
				if (RelayManager.getType(msg[0]) == RelayManager.TYPE_RELAY_TO) {
					Peer p = RelayManager.getPeer(msg[0]);
					packet.setType(TYPE_TRANSFER_FILE);
					packet.setOption((byte) 2);
					packet.setPayload("");
					p.write(packet);

					packet.setOption((byte) 0);
					packet.setPayload(msg[1] + "\t" + p.getRemoteAddress() + "\t" + p.getRemotePort());
					PeerManager.get(0).write(packet);
					//p.setRunning(false);
				} else	if (RelayManager.getType(msg[0]) == RelayManager.TYPE_RELAY_SELF) {
					if (wIncomming == null) return;
					try { wIncomming.close(); } catch (Exception e) {}
					File file = new File(fIncomming.getFileName());
					fIncomming.setFileSize(file.length());
					fIncomming.setFileMD5(SharedFile.MakeMD5(fIncomming.getFileName()));
					fIncomming.setFileStatus(FileManager.STATUS_NOT_COMMIT);

					packet.setType(PacketHandler.TYPE_REGISTER_FILE);
					packet.setOption((byte) 0);
					packet.setPayload(fIncomming.getFileName() + "\t" + fIncomming.getFileSize() + "\t" + fIncomming.getFileMD5());
					PeerManager.get(0).write(packet);
				}
				Main.setConsolCanRead(true);
				peer.setRunning(false);
 			}
			break;
		case TYPE_GENERAL_MESSAGE:
			switch (packet.getOption()) {
			case GMSG_INVALID_REQUEST: Console.error("잘못된 요청입니다."); break;
			case GMSG_NOT_REGISTERED: Console.error("먼저 사용자 등록을 해야합니다."); break;
			case GMSG_HOLE_PUNCHING_FAILED: Console.error("홀펀칭에 실패하였습니다."); break;
			case GMSG_NAME_IN_USE: Console.error("이미 등록 된 사용자 이름입니다."); break;
			case GMSG_INVALID_NAME: Console.error("잘못된 사용자 이름입니다."); break;
			case GMSG_FILE_REGISTER_FAILED: Console.error("파일 등록에 실패했습니다."); break;
			case GMSG_SEARCH_FILE_FAILED: Console.error("검색 된 파일이 없습니다."); break;
			case GMSG_SEARCH_PEER_FAILED: Console.error("피어를 찾을 수 없습니다."); break;
			}
			Main.setConsolCanRead(true);
			break;
		default:
			Console.error("알 수 없는 패킷을 전달받았습니다.");
			Main.setConsolCanRead(true);
		}
	}

}
