package com.ge.crawler.webcrawler.util;

import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.springframework.core.io.ClassPathResource;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class WebCrawlerUtil {
	Set<String> visitedPagesList= new HashSet<String>();
	Set<String> errorPagesList=new HashSet<String>();
	Set<String> skippedPagesList=new HashSet<String>();
	Set<String> addressNodeList=new HashSet<String>();

	public static void main(String[] args) {
	
		
		String address= "page-51";
		String fileName = "static/internet.json";
		//String fileName = "C:\\Users\\mayaggar\\Desktop\\project\\internet.json";
		
		byte[] byteArray = new byte[4024];
		try {
			long startTime= System.currentTimeMillis();
			  InputStream inputStream = new ClassPathResource(fileName).getInputStream();
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
		
		WebCrawlerUtil util= new WebCrawlerUtil();
		util.fillAllAddressNodes(pagesNode);
		util.search(pagesNode, address);
		System.out.println("visitedPagesList: "+util.visitedPagesList);
		System.out.println("errorPagesList: "+util.errorPagesList);
		System.out.println("skippedPagesList: "+util.skippedPagesList);
		System.out.println("Process time: "+(System.currentTimeMillis()-startTime));
		}catch (Exception e) {
			e.printStackTrace();
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
