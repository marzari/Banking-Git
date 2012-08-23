package net.kallx.banking.payment.model;

import java.util.Calendar;

import net.kallx.banking.commons.model.Addition;
import net.kallx.banking.commons.model.Discount;
import net.kallx.banking.commons.model.Payee;
import net.kallx.banking.commons.model.Payer;

public class CollectionPayment extends Payment {

	private Payer payer;
	private Payee payee;

	private String barCode;
	private Calendar documentMaturity;
	private double documentAmount;

	private Discount discount;
	private Addition addition;
	
	private Calendar date;
	private double amount;
	private double currencyAmount;
	
	private String payeeNumber;
	private String documentNumber;
	
	private String currencyCode;
	
	
}
