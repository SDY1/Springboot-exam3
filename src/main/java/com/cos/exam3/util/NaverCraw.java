package com.cos.exam3.util;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cos.exam3.domain.News;

@Component  
public class NaverCraw {
//	public static String[] _id =  new String[200];
	int aidNum = 1;
	
	public List<News> collect5() {
		RestTemplate rt = new RestTemplate();
		List<News> newsList = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			try{
				String aid = String.format("%010d", aidNum);
				String url = "https://news.naver.com/main/read.naver?mode=LSD&mid=shm&sid1=102&oid=022&aid=" + aid;
				String html = rt.getForObject(url, String.class);
	
				Document doc = Jsoup.parse(html); 
	
				Element companyElement = doc.selectFirst(".press_logo img");
				Element titleElement = doc.selectFirst("#articleTitle");
				Element createdAtElement = doc.selectFirst(".t11");
				
				String company = companyElement.attr("alt");
				String title = titleElement.text();
				String createdAt = createdAtElement.text();
	
				News news = News.builder().company(company).title(title).createdAt(createdAt).build();
				newsList.add(news); // try-catch문(널값처리)
//				_id[i] = new String(news.get_id());
				aidNum++;
			} catch (Exception e) {
//				newsList.add(null);
				System.out.println("널 값");
			}
		}
		return newsList;
	}
}
