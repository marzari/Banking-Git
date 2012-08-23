/**
 * 
 */
package net.kallx.banking.collection.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import net.kallx.banking.bank.model.Bank;

/**
 * @author Tiago
 * Correspondente a tabela FIN_TP_COB_DISTRIBUICAO
 *
 */
@Entity
@Table(name = "Destribuition", schema = "banking")
public class Distribution {

	/**
	 * id do banco
	 */
	private Bank bank;
	private long id;
	private String code;
	private String description;


	@Column(name = "cId_Bank")
	public Bank getBanco() {
		return bank;
	}
	public void setBanco(Bank banco) {
		this.bank = banco;
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
