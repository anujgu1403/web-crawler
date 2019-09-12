package com.ge.crawler.webcrawler.util;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebCrawlerMain {



	public static void main(String[] args) throws InterruptedException {
		
		// ExecutorService es = Executors.newFixedThreadPool(5); 
		//System.out.println(Thread.currentThread()+"***********");
		
		 long startTime=System.currentTimeMillis();
		 
		for(int i=0;i<5;i++) {
			
			//webCThread.makeConnection();	
			/*
			 * synchronized (webCThread) { webCThread.start();
			 * 
			 * }
			 */
			WebCrawlerThread webCThread = new WebCrawlerThread();
			webCThread.start();
			webCThread.join();
			
			//webCThread.join();
			//es.execute(webCThread);
			//webCThread.run();
		}
		
	
		
		
		
		//WebCrawlerThread webCThread1 = new WebCrawlerThread();
		//webCThread1.start();
		
		/*
		 * Thread t1= new Thread(webCThread); Thread t2= new Thread(webCThread); //
		 * t1.run(); t1.start(); // t2.run(); t2.start();
		 */
		
		 // es.execute(webCThread); 
		 
		 
		//if(!es.isTerminated()) {
		//System.out.println(es.isTerminated());
		
		  System.out.println("Process time: "+(System.currentTimeMillis()-startTime));
		 
		//}
		
		//es.shutdownNow();
		
	}

}
