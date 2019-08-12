package com.network1;

import java.util.StringTokenizer;

public class StringTokenizerTest {
	public static void main(String[] args) {
	
		String msg = "200|앞에|뒤에|뭐하냐 놀자";
		StringTokenizer st = new StringTokenizer(msg,Protocol.seperator);
		int protocol = 0;
		String tmp = st.nextToken();
		String Sender = st.nextToken();
		String receiver = st.nextToken();
		String msg1 = st.nextToken();
		if(tmp!=null) {
			protocol = Integer.parseInt(tmp);
		}
		System.out.println(tmp);
		System.out.println(Sender);
		System.out.println(receiver);
		System.out.println(msg1);
		switch(protocol) {
		case 110:
		case 200:
		case 300:	
		}
		
	}	

}
