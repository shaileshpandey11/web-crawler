package com.prudential.web.crawler.service;

import java.net.URL;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * This class will be responsible for parsing URL and extracting
 * results
 *
 */
public class WebCrawlerExecutorService implements Callable<Set<URL>>{
	
	private final URL url;
	
	public WebCrawlerExecutorService(final URL url) {
        this.url = url;
    }

	@Override
	public Set<URL> call() throws Exception {
		return null;
	}

}
