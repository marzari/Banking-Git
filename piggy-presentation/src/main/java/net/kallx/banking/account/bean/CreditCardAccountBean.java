package net.kallx.banking.account.bean;

import java.util.Calendar;

import net.kallx.architecture.repository.GenericFactory;
import net.kallx.banking.account.model.CreditCardAccount;
import net.kallx.banking.account.model.CreditCardFlag;

/**
 * 
 * @author Marcos Vinicius
 *
 */
public class CreditCardAccountBean extends OperationAccountBean<CreditCardAccount>{

	private CreditCardAccount model;
	
	private String cardNumber;
	private String flag;
	private Calendar start;
	private int maturityDay;
	private int calculationTime;
	private CheckingAccountBean linkedAccount;
	
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	

	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	

	public Calendar getStart() {
		return start;
	}
	public void setStart(Calendar start) {
		this.start = start;
	}
	

	public int getMaturityDay() {
		return maturityDay;
	}
	public void setMaturityDay(int maturityDay) {
		this.maturityDay = maturityDay;
	}

	
	public int getCalculationTime() {
		return calculationTime;
	}
	public void setCalculationTime(int calculationTime) {
		this.calculationTime = calculationTime;
	}
	

	public CheckingAccountBean getLinkedAccount() {
		return linkedAccount;
	}
	public void setLinkedAccount(CheckingAccountBean linkedAccount) {
		this.linkedAccount = linkedAccount;
	}
	

	
	@Override
	public CreditCardAccount build(GenericFactory<CreditCardAccount> factory) {
		
		if (this.model == null)
			this.model = new CreditCardAccount();
		
		model.setCalculationTime(calculationTime);
		model.setCardNumber(cardNumber);
		model.setName(name);
		try {
			model.setFlag(CreditCardFlag.valueOf(flag));
		} catch (Exception e) {
			// do nothing
		}
		
		model.setMaturityDay(maturityDay);
		model.setStart(start);
		
		
		return model;
	}
	
	
	@Override
	public CreditCardAccount getModel() {
		return model;
	}
	@Override
	public void setModel(CreditCardAccount model) {
		this.model = model;
	}
	
	
	@Override
	public CreditCardAccountBean load(CreditCardAccount model) {
		
		if (model == null)
			throw new IllegalStateException();
		
		load(model, true);
		loadEntries(model);
		
		return this;
	}
	
	public CreditCardAccountBean load(CreditCardAccount model, boolean onlyLazy) {
		
		if (model == null)
			throw new IllegalStateException();
		
		this.model = model;
		this.id = model.getId();
		this.calculationTime = model.getCalculationTime();
		this.cardNumber = model.getCardNumber();
		this.name = model.getName();
		this.flag = (model.getFlag() != null ? model.getFlag().name() : "");
		this.maturityDay = model.getMaturityDay();
		this.start = model.getStart();
		
		return this;
	}
	
	
}
