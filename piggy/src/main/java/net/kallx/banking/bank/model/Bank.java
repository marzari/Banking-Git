package net.kallx.banking.bank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import net.kallx.architecture.model.GenericModel;

/**
 * 
 * @author MarcosVinicius
 *
 */
@Entity
@Table(name="Bank", schema="banking")
public class Bank implements GenericModel {

	private long id;
	private String code;
	private String name;
	
	
	@Id @GeneratedValue(strategy=GenerationType.TABLE, generator="BankingSequence")
	@TableGenerator(name="BankingSequence", table="BankingSequence", schema="banking", 
			pkColumnName="cTable", pkColumnValue="BankSequence", valueColumnName="cNext", initialValue=1, allocationSize=1)
	@Column(name="cId")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	@Column(name="cCode")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	

	@Column(name="cName")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof Bank)) return false;
		Bank that = (Bank) obj;
		if (this.code == null ? that.code == null : this.code.equals(that.code))
			return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		int result = 13;
		result = result * 31 + (code != null ? code.hashCode() : 0);
		return result;
	}
	
}

