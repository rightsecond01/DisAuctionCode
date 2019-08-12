package com.discodeaucion_ver01;

import javax.swing.JPanel;

public class TalkPanel {
	final public static String AuctionRoom = "au";
	final public static String VoiceChatRoom = "vc";
	
	
	public JPanel textPanel(String roomType) {
		JPanel jp_text = null;
		if(roomType.equals(AuctionRoom)) {
			
		}
		else if(roomType.equals(VoiceChatRoom)) {
			
		}
		return jp_text;
	}
	public JPanel imagePanel() {
		JPanel jp_image = null;
		return jp_image;
	}
	public JPanel tradePanel() {
		JPanel jp_trade = null;
		return jp_trade;
	}

}
