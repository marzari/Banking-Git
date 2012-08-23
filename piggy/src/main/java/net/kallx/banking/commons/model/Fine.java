/**
 * 
 */
package net.kallx.banking.commons.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * @author Tiago
 * 
 */

@Embeddable
public class Fine implements Serializable {
	
	public enum FineCodeEnum {
		FINE_VALUE(1, "Multa por Valor"),
		FINE_PERCENTAGE(2, "Multa por Juros"),
		FINE_PRO_RATA(3, " Multa Juros Pro-Rata");
		
		private int code;
		private String description;

		private FineCodeEnum(int code, String description) {
			this.setCode(code);
			setDescription(description);
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}
		
		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		
		public static FineCodeEnum parse(int code) {
			FineCodeEnum fineCode = null;
			for (FineCodeEnum item : FineCodeEnum.values()) {
				if (item.getCode() == code) {
					fineCode = item;
					break;
				}
			}
			return fineCode;
		}
	}

	// Valor da multa
	private double amount;
	private Calendar date;
	private int code;

	// private long id;

	@Column(name = "cFineAmount")
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Column(name = "cFineDate")
	@Temporal(TemporalType.DATE)
	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	@Column(name = "cFineCode")
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	@Transient
	public FineCodeEnum getFineCode() {
		return FineCodeEnum.parse(this.code);
	}
}
