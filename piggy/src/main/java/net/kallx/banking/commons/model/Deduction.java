package net.kallx.banking.commons.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import net.kallx.banking.bank.model.Bank;

public class Deduction {

	/**
	 * id do banco
	 */
	private Bank bank;
	private long id;
	private String code;
	private String description;
	private Calendar dateOff;
	private double amount;


	@Column(name = "cId_Bank")
	public Bank getBanco() {
		return bank;
	}
	public void setBanco(Bank banco) {
		this.bank = banco;
	}
	
	
	@Column(name = "cAmountOff")
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
	@Column(name = "cDateOff")
	public Calendar getDateOff() {
		return dateOff;
	}
	public void setDateOff(Calendar dateOff) {
		this.dateOff = dateOff;
	}

	
	@Id
	@GeneratedValue
	@Column(name = "cId")
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	
	@Column(name = "cCode")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	
	@Column(name = "cDescription")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
