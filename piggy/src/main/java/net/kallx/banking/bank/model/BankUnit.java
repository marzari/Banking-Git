package net.kallx.banking.bank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import net.kallx.architecture.model.GenericModel;

/**
 * 
 * @author Marcos Vinicius
 * 
 */
@Entity
@Table(name = "BankUnit", schema = "banking")
public class BankUnit implements GenericModel {

	private long id;
	private String number;
	private String digit;
	private String name;
	private Bank bank;

	@Column(name = "cName")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "cDigit")
	public String getDigit() {
		return digit;
	}

	public void setDigit(String digit) {
		this.digit = digit;
	}

	@Column(name = "cNumber")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "BankingSequence")
	@TableGenerator(name = "BankingSequence", table = "BankingSequence", schema = "banking", pkColumnName = "cTable", pkColumnValue = "BankUnitSequence", valueColumnName = "cNext", initialValue = 1, allocationSize = 1)
	@Column(name = "cId")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "cBank")
	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

}
