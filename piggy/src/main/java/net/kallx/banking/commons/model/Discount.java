package net.kallx.banking.commons.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Correspondente a tabela FIN_TP_DESCONTOS
 * 
 * @author Tiago Felipe
 * @author Rodrigo H. Almeida
 */

@Embeddable
public class Discount implements Serializable {

	private DiscountType discountType;
	private Calendar dateOff;
	private double amount;

	@ManyToOne
	@JoinColumn(name = "cDiscountType")
	public DiscountType getDiscountType() {
		return discountType;
	}

	public void setDiscountType(DiscountType discountType) {
		this.discountType = discountType;
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
}
