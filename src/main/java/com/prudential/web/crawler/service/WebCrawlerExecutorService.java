package com.prudential.web.crawler.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This class will be responsible for parsing URL and extracting results
 *
 */
public class WebCrawlerExecutorService implements Callable<Set<URL>> {

	private final URL url;
	private final Set<URL> resultList = new HashSet<>();
	private static final int TIME_OUT = 90000;
	private static final String HASH="#";
	private static final String HREF="href";

	public WebCrawlerExecutorService(final URL url) {
		this.url = url;
	}

	@Override
	public Set<URL> call() throws Exception {
		if (!resultList.contains(url)) {
			try {
				// If not add it to the index
				if (resultList.add(url)) {
					System.out.println(url);
				}

				// Fetch the HTML code
				Document document = Jsoup.parse(url, TIME_OUT);
				// Parse the HTML to extract links to other URLs
				Elements linksOnPage = document.select("a[href]");

				String referenceLinks;

				for (Element page : linksOnPage) {
					referenceLinks = page.attr(HREF);

					if (referenceLinks == null || referenceLinks.isEmpty() || referenceLinks.startsWith(HASH)) {
						continue;
					}

					try {

						URL tempURL;

						if (referenceLinks.contains(HASH)) {
							tempURL = new URL(url, referenceLinks.substring(0, referenceLinks.indexOf(HASH)));
						} else {
							tempURL = new URL(this.url, referenceLinks);
						}

						this.resultList.add(tempURL);

					} catch (MalformedURLException e) {
						System.err.println("Malformed URL "+referenceLinks);
					}
				}
			} catch (IOException e) {
				System.err.println("For '" + url + "': " + e.getMessage());
			}
		}
		return resultList;
	}

}
