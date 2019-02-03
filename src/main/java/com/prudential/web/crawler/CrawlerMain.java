/**
 * 
 */
package com.prudential.web.crawler;

import java.net.URL;
import java.util.Set;

import com.prudential.web.crawler.service.CrawlerService;

/**
 * This main class is responsible for crawling the World Wide Web, typically for
 * the purpose of Web indexing.
 *
 */
public class CrawlerMain {

	private static final String prudentialDomainURL = "http://www.prudential.co.uk/";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CrawlerService crawler = new CrawlerService();

			crawler.crawl(prudentialDomainURL);

			final Set<URL> urlSet = crawler.getCrawledURLs();
			for (URL url : urlSet) {
				System.out.println("" + url);
			}
		} catch (IllegalArgumentException ie) {
			System.err.println("Please send URL to which you want to be crawled");
		}
	}

}
