package net.kallx.banking.bank.bean;

import net.kallx.architecture.control.GenericBean;
import net.kallx.architecture.repository.GenericFactory;
import net.kallx.banking.bank.model.BankUnit;

/**
 * 
 * @author Marcos Vinicius
 * 
 */
public class BankUnitBean implements GenericBean<BankUnit> {

	private long id;
	private String number;
	private String digit;
	private String name;
	private String description;
	private BankBean bank;

	private BankUnit model;

	@Override
	public void setModel(BankUnit model) {
		this.model = model;
	}

	@Override
	public BankUnit getModel() {
		return model;
	}

	@Override
	public BankUnitBean load(BankUnit model) {
		if (model == null)
			throw new IllegalStateException();
		
		setId(model.getId());
		setBank(new BankBean().load(model.getBank()));
		setName(model.getName());
		setDigit(model.getDigit());
		setNumber(model.getNumber());
		
		return this;
	}

	@Override
	public BankUnit build(GenericFactory<BankUnit> factory) {
		if (model == null)
			model = new BankUnit();
		
		model.setId(getId());
		model.setBank(getBank().build(null));
		model.setName(getName());
		model.setDigit(getDigit());
		model.setNumber(getNumber());
		
		return model;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDigit() {
		return digit;
	}

	public void setDigit(String digit) {
		this.digit = digit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BankBean getBank() {
		return bank == null ? new BankBean() : bank;
	}

	public void setBank(BankBean bank) {
		this.bank = bank;
	}

	@Override
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
