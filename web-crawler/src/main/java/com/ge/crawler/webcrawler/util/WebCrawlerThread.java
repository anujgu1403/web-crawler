package com.ge.crawler.webcrawler.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebCrawlerThread implements Runnable{
	
	Set<String> visitedPagesList= new HashSet<String>();
	Set<String> errorPagesList=new HashSet<String>();
	Set<String> skippedPagesList=new HashSet<String>();
	Set<String> addressNodeList=new HashSet<String>();

	@Override
	public void run() {
		String address= "page-51";
		String fileName = "static/internet.json";
		byte[] byteArray = new byte[4024];
		InputStream inputStream = null;
		try {
			
			inputStream=new ClassPathResource(fileName).getInputStream();
			  inputStream.read(byteArray); ObjectMapper objectMapper = new ObjectMapper();
			  JsonNode rootNode = objectMapper.readTree(byteArray); 
			  JsonNode pagesNode = rootNode.get("pages");
		
			/*
			 * ObjectMapper objectMapper = new ObjectMapper(); Pages crawlerPages =
			 * objectMapper.readValue(new File(fileName), Pages.class); List<PageLinks>
			 * pageLinksList= crawlerPages.getPages(); for(int
			 * i=0;i<pageLinksList.size();i++) { PageLinks pageLinks=pageLinksList.get(i);
			 * System.out.println(pageLinks.getAddress()); }
			 */
		
		fillAllAddressNodes(pagesNode);
		search(pagesNode, address);
	
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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

	public void fillAllAddressNodes(JsonNode jsonNode) {
		  Iterator<JsonNode> pagesNodeItr = jsonNode.elements();
		  pagesNodeItr.forEachRemaining(node->{
				pagesNodeItr.next();
				addressNodeList.add(node.get("address").asText());
		  });
	}
}
