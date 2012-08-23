package net.kallx.banking.account.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Represents a credit card account.
 * @author MarcosVinicius
 *
 */
@Entity
@Table(name="CreditCardAccount", schema="banking")
public class CreditCardAccount extends OperationAccount {
	
	private static final long serialVersionUID = 1L;

	private String cardNumber;
	private CreditCardFlag flag;
	private Calendar start;
	private int maturityDay;
	private int calculationTime;
	private CheckingAccount linkedAccount;
	
	
	@Column(name="cCardNumber")
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	

	@Enumerated
	@Column(name="cFlag")
	public CreditCardFlag getFlag() {
		return flag;
	}
	public void setFlag(CreditCardFlag flag) {
		this.flag = flag;
	}
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="cStart")
	public Calendar getStart() {
		return start;
	}
	public void setStart(Calendar start) {
		this.start = start;
	}
	

	@Column(name="cMaturityDay")
	public int getMaturityDay() {
		return maturityDay;
	}
	public void setMaturityDay(int maturityDay) {
		this.maturityDay = maturityDay;
	}

	
	@Column(name="cCalculationTime")
	public int getCalculationTime() {
		return calculationTime;
	}
	public void setCalculationTime(int calculationTime) {
		this.calculationTime = calculationTime;
	}

	
	@ManyToOne
	@JoinColumn(name="cLinkedAccount")
	public CheckingAccount getLinkedAccount() {
		return linkedAccount;
	}
	public void setLinkedAccount(CheckingAccount linkedAccount) {
		this.linkedAccount = linkedAccount;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof CreditCardAccount)) return false;
		CreditCardAccount that = (CreditCardAccount) obj;
		if (this.cardNumber == null ? that.cardNumber == null : this.cardNumber.equals(that.cardNumber) && 
				this.flag == null ? that.flag == null : this.flag == that.flag)
			return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		int result = 13;
		result = result * 31 + (cardNumber != null ? cardNumber.hashCode() : 0);
		result = result * 31 + (flag != null ? flag.hashCode() : 0);
		return result;
	}

}
