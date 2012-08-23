package net.kallx.banking.collection.model.state;

import java.util.ArrayList;
import java.util.List;

import net.kallx.banking.collection.model.Collection;

public class StatusChangeContext {

	
	private List<Collection> toRegisterList;
	
	
	public StatusChangeContext() {
		toRegisterList = new ArrayList<>();
	}
	
	
	public List<Collection> getToRegisterList() {
		return toRegisterList;
	}
	public void setToRegisterList(List<Collection> toRegisterList) {
		this.toRegisterList = toRegisterList;
	}
	
}
