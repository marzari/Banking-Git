package net.kallx.banking.account.bean;

import java.math.BigDecimal;
import java.util.Calendar;

import net.kallx.banking.account.model.BankAccount;
import net.kallx.banking.bank.bean.BankUnitBean;

/**
 * 
 * @author Marcos Vinicius
 *
 * @param <T>
 */
public abstract class BankAccountBean<T extends BankAccount> extends OperationAccountBean<T> {

	protected BankUnitBean bankUnit;
	protected Calendar start;
	protected Calendar opening;
	protected BigDecimal startBalance;
	protected String number;
	protected String digit;
	protected boolean active;
	
	public BankUnitBean getBankUnit() {
		return bankUnit;
	}
	public void setBankUnit(BankUnitBean bankUnit) {
		this.bankUnit = bankUnit;
	}
	
	public Calendar getStart() {
		return start;
	}
	public void setStart(Calendar start) {
		this.start = start;
	}
	
	public Calendar getOpening() {
		return opening;
	}
	public void setOpening(Calendar opening) {
		this.opening = opening;
	}
	
	
	public BigDecimal getStartBalance() {
		return startBalance;
	}
	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
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
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
}
