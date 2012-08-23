package net.kallx.banking.service;

import java.io.Serializable;

public class CollectionChangeRequest implements Serializable{

	private CollectionChangeRequestProperty property;
	private Object value;
	
	public CollectionChangeRequest(CollectionChangeRequestProperty property, Object value) {
		this.property = property;
		this.value = value;
	}
	
	
	public CollectionChangeRequestProperty getProperty() {
		return property;
	}
	public void setProperty(CollectionChangeRequestProperty property) {
		this.property = property;
	}
	
	
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
}
