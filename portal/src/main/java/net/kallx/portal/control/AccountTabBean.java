package net.kallx.portal.control;

import java.io.Serializable;

public class AccountTabBean implements Serializable {

	private String name;
	private String url;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
