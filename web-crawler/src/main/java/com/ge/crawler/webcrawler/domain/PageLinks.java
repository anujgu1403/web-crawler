package com.ge.crawler.webcrawler.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

@JsonIgnoreType
public class PageLinks {
private String address;
private List<String> links;
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public List<String> getLinks() {
	return links;
}
public void setLinks(List<String> links) {
	this.links = links;
}

}
