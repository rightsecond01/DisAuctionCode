package com.util;


import java.util.Date;



public class Token {

	public String hash;	

	private String data; //our data will be a simple message.

	private long timeStamp; //as number of milliseconds since 1/1/1970.



	//Block Constructor.

	public Token(String data) {
		this.data = data;		
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash(); //Making sure we do this after we set the other values.
	}

	public String calculateHash() {
		String calculatedhash = StringUtil.applySha256( 				
				Long.toString(timeStamp) +
				data 
				);
		return calculatedhash;
	}
}
