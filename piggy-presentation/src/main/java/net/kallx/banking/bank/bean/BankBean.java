package net.kallx.banking.bank.bean;

import net.kallx.architecture.control.GenericBean;
import net.kallx.architecture.repository.GenericFactory;
import net.kallx.banking.bank.model.Bank;

/**
 * 
 * 
 * @author Marcos Vinicius
 * 
 */
public class BankBean implements GenericBean<Bank> {
	
	private Bank model;
	
	private long id;
	private String code;
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public Bank getModel() {
		return model;
	}

	@Override
	public void setModel(Bank model) {
		this.model = model;
	}

	@Override
	public Bank build(GenericFactory<Bank> factory) {
		if (model == null)
			model = new Bank();
		
		model.setId(getId());
		model.setCode(getCode());
		model.setName(getName());
		
		return model;
	}

	@Override
	public BankBean load(Bank model) {
		if (model == null)
			throw new IllegalStateException();
		
		setId(model.getId());
		setCode(model.getCode());
		setName(model.getName());

		return this;
	}
}
