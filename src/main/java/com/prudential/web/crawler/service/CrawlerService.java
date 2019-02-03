package com.prudential.web.crawler.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * This class will create one thread class which will get crawled URLs
 *
 */
public class CrawlerService {
	
	/**
	 * List of unique URLs to be crawled
	 */
	private Set<URL> crawledURLs = Collections.synchronizedSet(new HashSet<>());
    /**
     * Thread which will parse and extracts result from domain
     */
    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    /**
     * Final output after execution 
     */
    private final List<Future<Set<URL>>> output = new ArrayList<>();
    
    /**
     * This method submit tasks to executor service
     * 
     * @param urlToBeCrawled URL to be crawled
     */
    public void crawl(final String urlToBeCrawled) throws IllegalArgumentException{
    	
    	if(urlToBeCrawled==null) {
    		throw new IllegalArgumentException();
    	}
    	try {
			addAndSubmitURL(new URL(urlToBeCrawled));
			executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
		} catch (MalformedURLException e) {
			System.out.println("URL is malformed"+e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("An error ouccred while crawling URL"+e.getMessage());
			e.printStackTrace();
		}
    
    }
    
    /**
     * This method adds URLs in collection and
     * sends the URL to be crawled to the executor service.
     *
     * @param url URL to be crawled
     */
    private void addAndSubmitURL(URL url) {

    	crawledURLs.add(url);

    	output.add(executorService.submit(new WebCrawlerExecutorService(url)));
    } 
    
    /**
     * @return Crawled URLs
     */
    public Set<URL> getCrawledURLs() {
        return new HashSet<>(this.crawledURLs);
    }
}
