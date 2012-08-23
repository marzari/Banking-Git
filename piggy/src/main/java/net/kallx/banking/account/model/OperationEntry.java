package net.kallx.banking.account.model;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.kallx.architecture.model.GenericModel;

@Entity
@Table(name="Entry", schema="banking")
public class OperationEntry implements Entry, GenericModel {
	
	private static final long serialVersionUID = 1L;
	
	private long id;

	private BigDecimal amount;
	private boolean credit;
	private Calendar ocurrence;
	private String description;
	private Account account;
	
	
	@Column(name="cAmount")
	@Override
	public BigDecimal getAmount() {
		return amount;
	}
	@Override
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	
	@Override
	public boolean isDebit() {
		return (!credit);
	}
	@Override
	public void setDebit(boolean debit){
		credit = false;
	}
	
	
	@Column(name="cCredit")
	@Override
	public boolean isCredit() {
		return credit;
	}
	@Override
	public void setCredit(boolean credit){
		this.credit = credit;
	}

	
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=OperationAccount.class)
	@JoinColumn(name="cOperationAccount")
	@Override
	public Account getAccount() {
		return account;
	}
	@Override
	public void setAccount(Account account) {
		this.account = account;
	}

	
	@Column(name="cDescription")
	@Override
	public String getDescription() {
		return description;
	}
	@Override
	public void setDescription(String description) {
		this.description = description;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="cOcurrence")
	@Override
	public Calendar getOcurrence() {
		return ocurrence;
	}
	@Override
	public void setOcurrence(Calendar when) {
		ocurrence = when;
	}

	
	@Id @GeneratedValue(strategy=GenerationType.TABLE, generator="BankingSequence")
	@TableGenerator(name="BankingSequence", table="BankingSequence", schema="banking", 
			pkColumnName="cTable", pkColumnValue="EntrySequence", valueColumnName="cNext", initialValue=1, allocationSize=1)
	@Column(name="cId")
	@Override
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
}
