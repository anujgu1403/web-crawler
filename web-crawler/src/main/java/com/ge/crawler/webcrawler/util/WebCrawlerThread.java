package com.ge.crawler.webcrawler.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.text.AbstractDocument.BranchElement;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebCrawlerThread extends Thread{
	
	Set<String> visitedPagesList= new HashSet<String>();
	Set<String> errorPagesList=new HashSet<String>();
	Set<String> skippedPagesList=new HashSet<String>();
	Set<String> addressNodeList=new HashSet<String>();
	String address= "page-05";
	String fileName = "C:\\web\\internet.json";
	//String fileName = "static/internet.json";
	byte[] byteArray = new byte[4024];
	InputStream inputStream = null;
	JsonNode rootNode = null;
	JsonNode pagesNode= null;

	public void makeConnection() {

		try {
			
			//inputStream=new ClassPathResource(fileName).getInputStream();
			byte[] jsonData = Files.readAllBytes(Paths.get(fileName));
			
			 // inputStream.read(byteArray); 
				ObjectMapper objectMapper = new ObjectMapper();
			//	Pages pages=objectMapper.readValue(jsonData, Pages.class);
			  rootNode = objectMapper.readTree(jsonData); 
			  pagesNode = rootNode.get("pages");
		
			//Thread.currentThread();
		/*
			 * ObjectMapper objectMapper = new ObjectMapper(); Pages crawlerPages =
			 * objectMapper.readValue(new File(fileName), Pages.class); List<PageLinks>
			 * pageLinksList= crawlerPages.getPages(); for(int
			 * i=0;i<pageLinksList.size();i++) { PageLinks pageLinks=pageLinksList.get(i);
			 * System.out.println(pageLinks.getAddress()); }
			 */
		//Thread.sleep(100);
		
	
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			/*
			 * try { inputStream.close(); } catch (IOException e) { // TODO Auto-generated
			 * catch block e.printStackTrace(); }
			 */
		}
	}
	@Override
	public void run() {
		//try {		
			//Thread.sleep(100);
			
			mainMethod();
			System.out.println("Executing run method..");
		/*}catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
	}
	
	public void mainMethod() {
		makeConnection();
		fillAllAddressNodes(pagesNode);
		search(pagesNode, address);				
		displayList();
	}
	public void search(JsonNode jsonNode, String searchKeyword) {
		
		if(!addressNodeList.contains(searchKeyword)) {
			errorPagesList.add(searchKeyword);
		}
		
		Iterator<JsonNode> pagesNodeItr = jsonNode.elements();
		pagesNodeItr.forEachRemaining(node->{
			pagesNodeItr.next();
			if(node.get("address").asText().equals(searchKeyword)) {
				if(!visitedPagesList.contains(node.get("address").asText())) {
					visitedPagesList.add(node.get("address").asText());
					JsonNode linksNode = node.get("links");
					Iterator<JsonNode> links = linksNode.elements();
					links.forEachRemaining(linkNode ->{
						links.next();
						search(jsonNode,linkNode.asText());
					});
				}else {
					skippedPagesList.add(node.get("address").asText());
				}
			}		
				
		});
	}

 	 public synchronized void  fillAllAddressNodes(JsonNode jsonNode) {
		  Iterator<JsonNode> pagesNodeItr = jsonNode.elements();
		  pagesNodeItr.forEachRemaining(node->{
				pagesNodeItr.next();
				addressNodeList.add(node.get("address").asText());
		  });
	}
 	 
 	public void displayList() {
 		//synchronized(this) {
 			//if(visitedPagesList.size()!=0) {
 				System.out.println("visitedPagesList: "+visitedPagesList);
 	 			  System.out.println("errorPagesList: "+errorPagesList);
 	 			  System.out.println("skippedPagesList: "+skippedPagesList);	
 	 			System.out.println("****************************************");
 	 			  
 			//}
 			
 		//}
 		
	}
}
