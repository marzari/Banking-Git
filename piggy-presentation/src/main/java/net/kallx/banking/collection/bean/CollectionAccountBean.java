package net.kallx.banking.collection.bean;

import net.kallx.architecture.control.GenericBean;
import net.kallx.architecture.repository.GenericFactory;
import net.kallx.banking.account.bean.CheckingAccountBean;
import net.kallx.banking.collection.model.CollectionAccount;

/**
 * 
 * @author Marcos Vinicius
 *
 */
public class CollectionAccountBean implements GenericBean<CollectionAccount> {

	private long id;
	private CollectionAccount model;
	private CheckingAccountBean checkingAccount;
	private String name;
	private String description;
	private String agreement;
	private boolean registered;
	private String line; //variação
	private String operation;
	
	
	public CollectionAccountBean() {
		model = new CollectionAccount();
	}
	
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isRegistered() {
		return registered;
	}
	public void setRegistered(boolean registered) {
		this.registered = registered;
	}
	
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public CheckingAccountBean getCheckingAccount() {
		return checkingAccount;
	}
	public void setCheckingAccount(CheckingAccountBean checkingAccount) {
		this.checkingAccount = checkingAccount;
	}
	
	
	public String getAgreement() {
		return agreement;
	}
	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}
	
	
	@Override
	public long getId() {
		return id;
	}
	
	
	@Override
	public CollectionAccount getModel() {
		return model;
	}
	@Override
	public void setModel(CollectionAccount model) {
		this.model = model;
	}
	
	
	@Override
	public CollectionAccount build(GenericFactory<CollectionAccount> factory) {
		
		if (model == null)
			model = new CollectionAccount();
		
		model.setId(getId());
		model.setAgreement(agreement);
		model.setDescription(description);
		model.setOperation(operation);
		model.setRegistered(registered);
		model.setName(name);
		model.setLine(line);
		
		return model;
	}
	
	
	@Override
	public CollectionAccountBean load(CollectionAccount model) {
		
		if (model == null)
			throw new IllegalStateException();
		
		this.model = model;
		id = model.getId();
		
		agreement = model.getAgreement();
		description = model.getDescription();
		name = model.getName();
		registered = model.isRegistered();
		name = model.getName();
		operation = model.getOperation();
		line = model.getLine();
		
		return this;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof CollectionAccountBean)) return false;
		CollectionAccountBean that = (CollectionAccountBean) obj;
		if (id > 0)
			return this.id == that.getId();
		else 
			if (this.name == null ? that.getName() == null : this.name.equals(that.getName()))
			return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		if (this.id > 0)
			result = result * 31 + (int)(id^(id>>>32));
		else 
			result = result * 31 + (name != null ? name.hashCode() : 0);
		
		return result;
	}
	
}
