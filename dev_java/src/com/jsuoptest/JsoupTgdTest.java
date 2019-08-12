package com.jsuoptest;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupTgdTest {
	
	public static void main(String[] args) throws IOException {
		String url = "https://tgd.kr/";
		Connection.Response response = Jsoup.connect(url).method(Connection.Method.GET).execute();
		Document doc = response.parse();
		Element dropdown = doc.select("div#header-menu-right").first();
		Elements login = dropdown.select("div.dropdown");
		String login_check = login.text();		
		System.out.println(login_check);	
		
	}
}