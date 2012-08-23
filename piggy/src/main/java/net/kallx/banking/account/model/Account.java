package net.kallx.banking.account.model;

import java.math.BigDecimal;
import java.util.Set;

public interface Account {

	BigDecimal balance();
	
	void addEntry(Entry entry);
	Set<Entry> getEntries();
	void setEntries(Set<Entry> entries);
	
	String getName();
	void setName(String name);
	
}
