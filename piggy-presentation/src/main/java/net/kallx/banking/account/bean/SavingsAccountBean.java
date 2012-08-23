package net.kallx.banking.account.bean;

import net.kallx.architecture.repository.GenericFactory;
import net.kallx.banking.account.model.SavingsAccount;
import net.kallx.banking.bank.bean.BankUnitBean;

/**
 * 
 * @author Marcos Vinicius
 *
 */
public class SavingsAccountBean extends BankAccountBean<SavingsAccount> {

	private SavingsAccount model;
	
	@Override
	public SavingsAccount build(GenericFactory<SavingsAccount> factory) {
		
		if (model == null)
			model = new SavingsAccount();
			
		model.setBankUnit(getBankUnit().build(null));
		model.setName(name);
		model.setDigit(digit);
		model.setNumber(number);
		model.setOpening(opening);
		model.setStart(start);
		model.setStartBalance(startBalance);
		
		return model;
	}

	@Override
	public SavingsAccount getModel() {
		return model;
	}
	@Override
	public void setModel(SavingsAccount model) {
		this.model = model;
	}

	
	@Override
	public SavingsAccountBean load(SavingsAccount model) {
		
		if (model == null)
			throw new IllegalStateException();
		
		load(model, true);
		loadEntries(model);
		
		return this;
		
	}
	
	
	public SavingsAccountBean load(SavingsAccount model, boolean onlyLazy) {
		
		if (model == null)
			throw new IllegalStateException();
		
		this.model = model;
		this.id = model.getId();
		this.bankUnit = new BankUnitBean().load(model.getBankUnit());
		this.name = model.getName();
		this.digit = model.getDigit();
		this.number = model.getNumber();
		this.opening = model.getOpening();
		this.start = model.getStart();
		this.startBalance = model.getStartBalance();
		
		return this;
	}

}
