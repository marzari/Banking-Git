package net.kallx.banking.account.model;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.kallx.banking.bank.model.BankUnit;

@Entity
public abstract class BankAccount extends OperationAccount {
	
	protected BankUnit bankUnit;
	protected Calendar start;
	protected Calendar opening;
	protected BigDecimal startBalance;
	protected String number;
	protected String digit;
	protected String digitAgencyAndCheckingAccount;
	protected boolean active;
	
	@ManyToOne
	@JoinColumn(name = "cBankUnit")
	public BankUnit getBankUnit() {
		return bankUnit;
	}

	public void setBankUnit(BankUnit bankUnit) {
		this.bankUnit = bankUnit;
	}
	
	@Column(name="digitAgencyAndCheckingAccount")
	public String getDigitAgencyAndCheckingAccount() {
		return digitAgencyAndCheckingAccount;
	}
	public void setDigitAgencyAndCheckingAccount(
			String digitAgencyAndCheckingAccount) {
		this.digitAgencyAndCheckingAccount = digitAgencyAndCheckingAccount;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="cStart")
	public Calendar getStart() {
		return start;
	}
	public void setStart(Calendar start) {
		this.start = start;
	}
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="cOpening")
	public Calendar getOpening() {
		return opening;
	}
	public void setOpening(Calendar opening) {
		this.opening = opening;
	}
	
	
	@Column(name="cStartBalance")
	public BigDecimal getStartBalance() {
		return startBalance;
	}
	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}
	
	
	@Column(name="cNumber")
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	
	@Column(name="cDigit")
	public String getDigit() {
		return digit;
	}
	public void setDigit(String digit) {
		this.digit = digit;
	}
	
	@Column (name = "bActive")
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof CheckingAccount)) return false;
		CheckingAccount that = (CheckingAccount) obj;
		if ((this.number == null ? that.number == null : this.number.equals(that.number)) &&
				(this.digit == null ? that.digit == null : this.digit.equals(that.digit))) return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		int result = 13;
		result = result * 31 + (number != null ? number.hashCode() : 0);
		result = result * 31 + (digit != null ? digit.hashCode() : 0);
		return result;
	}
	
}
