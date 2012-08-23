package net.kallx.banking.account.bean;

import java.math.BigDecimal;
import java.util.Calendar;

import net.kallx.architecture.repository.GenericFactory;
import net.kallx.banking.account.model.CashAccount;

/**
 * 
 * @author Marcos Vinicius
 *
 */
public class CashAccountBean extends OperationAccountBean<CashAccount>{

	protected CashAccount model;
	protected Calendar start;
	protected BigDecimal startBalance;
	
	public Calendar getStart() {
		return start;
	}
	public void setStart(Calendar start) {
		this.start = start;
	}
	
	
	public BigDecimal getStartBalance() {
		return startBalance;
	}
	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}
	
	
	@Override
	public CashAccount getModel() {
		return model;
	}
	@Override
	public void setModel(CashAccount model) {
		this.model = model;
	}
	
	
	@Override
	public CashAccount build(GenericFactory<CashAccount> factory) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public CashAccountBean load(CashAccount model) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public CashAccountBean load(CashAccount model, boolean onlyLazy) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
