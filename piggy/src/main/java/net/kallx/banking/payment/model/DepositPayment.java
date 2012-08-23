package net.kallx.banking.payment.model;

import java.util.Calendar;
import java.util.List;

import net.kallx.banking.account.model.BankAccount;
import net.kallx.banking.commons.model.Deduction;
import net.kallx.banking.commons.model.Fine;
import net.kallx.banking.commons.model.Interest;
import net.kallx.banking.commons.model.Payee;
import net.kallx.banking.commons.model.Payer;

public class DepositPayment extends Payment {
	
	private Payee payee;
	private Payer payer;

	private String number;
	private Calendar date;
	
	private String currencyType;
	private String currencyAmount;
	
	private double amount;
	private String bankNumber;
	
	private Calendar effectivityDate;
	private double effetivityAmount;
	
	private String info;
	
	private String goal;
	
	
	private Calendar documentMaturity;
	private double documentAmount;
	
	private Interest interest;
	private Fine fine;
	private Deduction deduction;
	
	private String payeeCode;
	private String message;
	
	private List<Deduction> otherDeductions;
	
	private BankAccount substitution;
	
}
