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
 * @author Tiago Tabela correspondente a FIN_TP_PROTESTOS
 */

@Embeddable
public class Protest implements Serializable {

	/**
	 * C026 - Código para Protesto<br>
	 * '1' = Protestar Dias Corridos<br>
	 * '2' = Protestar Dias Úteis<br>
	 * '3' = Não Protestar<br>
	 * '4' = Protestar Fim Falimentar - Dias Úteis<br>
	 * '5' = Protestar Fim Falimentar - Dias Corridos<br>
	 * '8' = Negativação sem Protesto<br>
	 * '9' = Cancelamento Protesto Automático (somente válido p/ CódigoMovimento
	 * Remessa = '31' - Descrição C004)
	 */
	public enum ProtestTypeEnum {
		PROTEST_RUNNING_DAYS(1, "Protestar Dias Corridos"),
		PROTEST_WORKING_DAYS(2, "Protestar Dias Úteis"),
		PROTEST_DONT(3, "Não Protestar"),
		PROTEST_FAL_WORKING_DAYS(4, "Protestar Fim Falimentar - Dias Úteis"),
		PROTEST_FAL_RUNNING_DAYS(5, "Protestar Fim Falimentar - Dias Corridos"),
		PROTEST_NEGATIVATION(8, "Negativação sem Protesto"),
		PROTEST_CANCEL_PROTEST_AUTO(9, "Cancelamento Protesto Automático");

		private String description;
		private int code;

		private ProtestTypeEnum(int code, String description) {
			this.code = code;
			this.description = description;
		}

		public int getCode() {
			return code;
		}

		public String getDescription() {
			return description;
		}

		public static ProtestTypeEnum parse(int code) {
			ProtestTypeEnum protestTypeCode = null;
			for (ProtestTypeEnum item : ProtestTypeEnum.values()) {
				if (item.getCode() == code) {
					protestTypeCode = item;
					break;
				}
			}
			return protestTypeCode;
		}
	}

	private Calendar dateEmission;
	private int protestCode;
	private int daysForProtest;


	@Column(name = "cProtestCode")
	public int getProtestCode() {
		return protestCode;
	}

	public void setProtestCode(int code) {
		this.protestCode = code;
	}

	@Column(name = "cProtestDateEmission")
	public Calendar getDateEmission() {
		return dateEmission;
	}

	public void setDateEmission(Calendar dateEmission) {
		this.dateEmission = dateEmission;
	}

	@Transient
	public ProtestTypeEnum getProtestType() {
		return ProtestTypeEnum.parse(protestCode);
	}
	
	@Column(name = "cDaysForProtest")
	public int getDaysForProtest() {
		return daysForProtest;
	}

	public void setDaysForProtest(int daysForProtest) {
		this.daysForProtest = daysForProtest;
	}
}
