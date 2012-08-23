package net.kallx.banking.account.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.kallx.architecture.control.GenericBean;
import net.kallx.banking.account.model.CashAccount;
import net.kallx.banking.account.model.CheckingAccount;
import net.kallx.banking.account.model.CreditCardAccount;
import net.kallx.banking.account.model.Entry;
import net.kallx.banking.account.model.OperationAccount;
import net.kallx.banking.account.model.OperationEntry;
import net.kallx.banking.account.model.SavingsAccount;

/**
 * 
 * @author Marcos Vinicius
 *
 * @param <T>
 */
public abstract class OperationAccountBean<T extends OperationAccount> implements GenericBean<T> {

	protected long id;
	protected String name;
	protected List<OperationEntryBean> entries;
	
	public OperationAccountBean() {
		entries = new ArrayList<OperationEntryBean>();
	}
	
	
	@Override
	public long getId(){
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getDescription(){
		return name;
	}
	public void setDescription(String description){
		this.name = description;
	}
	
	
	public List<OperationEntryBean> getEntries(){
		return entries;
	}
	public void setEntries(List<OperationEntryBean> entries){
		this.entries = entries;
	}
	
	
	public void loadEntries(OperationAccount model){
		
		Set<Entry> en = model.getEntries();
		for (Entry entry : en) {
			entries.add(new OperationEntryBean().load((OperationEntry) entry));
		}
		
	}
	
	
	public static OperationAccountBean<?> create(OperationAccount model){

		if (model instanceof CheckingAccount)
			return new CheckingAccountBean().load((CheckingAccount) model, true);
		else if (model instanceof CreditCardAccount)
			return new CreditCardAccountBean().load((CreditCardAccount) model, true);
		else if (model instanceof CashAccount)
			return new CashAccountBean().load((CashAccount) model, true);
		else if (model instanceof SavingsAccount)
			return new SavingsAccountBean().load((SavingsAccount) model, true);
		
		return null;
		
	}
	
	
}
