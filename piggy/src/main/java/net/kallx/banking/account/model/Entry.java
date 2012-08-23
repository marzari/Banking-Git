package net.kallx.banking.account.model;

import java.math.BigDecimal;
import java.util.Calendar;

public interface Entry {

	Account getAccount();
	void setAccount(Account account);
	
	String getDescription();
	void setDescription(String description);
	
	Calendar getOcurrence();
	void setOcurrence(Calendar when);
	
	BigDecimal getAmount();
	void setAmount(BigDecimal amount);
	
	boolean isDebit();
	void setDebit(boolean debit);
	
	boolean isCredit();
	void setCredit(boolean credit);
	
}
