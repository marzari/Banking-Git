package net.kallx.banking.account.model;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="CashAccount", schema="banking")
public class CashAccount extends OperationAccount {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The date in which that account started its operation <b>in the system</b>,
	 * to contrast with the date of the account opening in the Bank.
	 */
	protected Calendar start;

	
	/**
	 * The start balance for this account.
	 */
	protected BigDecimal startBalance;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="cStart")
	public Calendar getStart() {
		return start;
	}
	public void setStart(Calendar start) {
		this.start = start;
	}
	
	
	@Column(name="cStartBalance")
	public BigDecimal getStartBalance() {
		return startBalance;
	}
	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}
}
