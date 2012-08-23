/**
 * 
 */
package net.kallx.banking.commons.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * Embeddable class for interest. <br>
 * Must be used with classes that needs interests values and defaults codes.
 * 
 * @author Tiago
 * 
 */

@Embeddable
public class Interest implements Serializable {

	public enum InterestCodeEnum {
		INTEREST_DAY(1 , "Valor Dia"),
		INTEREST_MONTH(2, "Tx Mensal"),
		INTEREST_EXEMPT(3, "Isento");

		private int code;
		private String description;

		private InterestCodeEnum(int code, String description) {
			this.code = code;
			this.description = description;
		}

		public int getCode() {
			return code;
		}
		
		public String getDescription() {
			return description;
		}

		public static InterestCodeEnum parse(int code) {
			InterestCodeEnum protestTypeCode = null;
			for (InterestCodeEnum item : InterestCodeEnum.values()) {
				if (item.getCode() == code) {
					protestTypeCode = item;
					break;
				}
			}
			return protestTypeCode;
		}
	}

	private int code;
	// Valor do juros
	private double amount;
	private Calendar date;

	@Column(name = "cInterestCode")
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Column(name = "cInterestAmount")
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Column(name = "dInterestDate")
	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	@Transient
	public InterestCodeEnum getInterestCode() {
		return InterestCodeEnum.parse(this.code);
	}
}
