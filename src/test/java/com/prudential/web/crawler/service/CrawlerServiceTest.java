package com.prudential.web.crawler.service;

import static org.junit.Assert.*;

import org.junit.Test;

public class CrawlerServiceTest {

	@Test(expected = IllegalArgumentException.class)
	public void testCrawl() {
		CrawlerService service = new CrawlerService();
		service.crawl(null);
	}

}
