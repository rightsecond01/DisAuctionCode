package com.jsuoptest;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupTest {

	public static void main(String[] args) throws IOException {
		String url = "https://weather.naver.com";
		Connection.Response response = Jsoup.connect(url).method(Connection.Method.GET).execute();
		Document doc = response.parse();
		Element weather = doc.select("div.m_zone1").first();
		Elements now = weather.select("tr.now");
		String info = now.text();		
		
		System.out.println(info);
	}

}
