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
 * Tabela correspondente a FIN_TP_COB_DOCUMENTO
 * 
 *
 */
@Entity
@Table(name="Document", schema="banking")
public class Document {
	
	private long id;
	/**
	 * id do banco
	 */
	private Bank bank;
	private String code;
	private String description;
	
	@Id @GeneratedValue
	@Column(name="cId")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	@Column(name="cId_Bank")
	public Bank getBank() {
		return bank;
	}
	public void setBank(Bank banco) {
		this.bank = banco;
	}
	
	
	@Column(name="cCode")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	@Column(name="cDescription")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
	
}
