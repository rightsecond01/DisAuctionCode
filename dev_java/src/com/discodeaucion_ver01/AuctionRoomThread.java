package com.discodeaucion_ver01;

import java.util.StringTokenizer;

public class AuctionRoomThread extends Thread {
//클라이언트쪽에서 접속한 옥션룸에게 서버가 보내는 명령을 처리하는 쓰레드입니다.
	AuctionRoom ar = null;

	public AuctionRoomThread(AuctionRoom ar) {
		this.ar = ar;
	}

	public void run() {
		String msg = null;
		boolean isStop = false;
		while (!isStop) {
			try {
				msg = (String) ar.room_ois.readObject();
				StringTokenizer st = null;
				int protocol = 0;
				if (msg != null) {
					st = new StringTokenizer(msg, Dprotocol.seperator);
					protocol = Integer.parseInt(st.nextToken());
				}
				switch (protocol) {
				case Dprotocol.TEST: {
					String t = st.nextToken();
					System.out.println("테스트입니다!" + t);
				}
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
