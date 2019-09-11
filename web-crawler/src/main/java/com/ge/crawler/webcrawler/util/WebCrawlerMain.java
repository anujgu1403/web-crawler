package com.ge.crawler.webcrawler.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebCrawlerMain {

	public static void main(String[] args) {
		ExecutorService es = Executors.newFixedThreadPool(2);
		long startTime= System.currentTimeMillis();
		WebCrawlerThread webCThread = new WebCrawlerThread();
		es.execute(webCThread); 
		es.shutdownNow();
		//if(!es.isTerminated()) {
		System.out.println(es.isTerminated());
		System.out.println("visitedPagesList: "+webCThread.visitedPagesList);
		System.out.println("errorPagesList: "+webCThread.errorPagesList);
		System.out.println("skippedPagesList: "+webCThread.skippedPagesList);
		System.out.println("Process time: "+(System.currentTimeMillis()-startTime));
		//}
		
	}

}
