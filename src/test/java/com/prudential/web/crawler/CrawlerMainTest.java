package com.prudential.web.crawler;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.prudential.web.crawler.service.CrawlerService;

public class CrawlerMainTest {
	
	private static final CrawlerService crawlerService = new CrawlerService();

	@Test
    public void testForInvalidURL() {
        String invalidURL = "http://www.invalid1234.com/";
        crawlerService.crawl(invalidURL);
        final Set<URL> urls = crawlerService.getCrawledURLs();
        Assert.assertEquals(0, urls.size());
    }

}
