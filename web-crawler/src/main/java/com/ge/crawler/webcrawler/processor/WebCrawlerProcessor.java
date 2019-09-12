package com.ge.crawler.webcrawler.processor;

import com.ge.crawler.webcrawler.util.WebCrawlerConstants;

/**
 * @author: anuj kumar
 * 
 * This is the main thread processor class which used to start web crawler process
 * 
 */
public class WebCrawlerProcessor {

	/**
	 * main method to start web crawler thread
	 * 
	 * @param String[]
	 */
	public static void main(String[] args) throws InterruptedException {		
		long startTime = System.currentTimeMillis();
		int maxThreads = Integer.parseInt(WebCrawlerConstants.MAX_THREADS);
		String searchKeyword = "page-50";
		
		System.out.println("Starting web crawling");
		for (int i = 0; i < maxThreads; i++) {
			WebCrawlerThread webCThread = new WebCrawlerThread(searchKeyword);
			
			//To start the thread
			webCThread.start();
			
			//To let thread complete one by one completely 
			webCThread.join(); 
		}
		System.out.println("Process time: " + (System.currentTimeMillis() - startTime)+" ms.");
	}
}
