package net.kallx.banking.collection.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import net.kallx.architecture.model.GenericModel;
import net.kallx.banking.account.model.BankAccount;

@Entity
@Table(name="CollectionAccount", schema="banking")
@NamedQueries({
	@NamedQuery(name="CollectionAccount_FindByName", query="select c from CollectionAccount c where c.name=:name")
})
public class CollectionAccount implements GenericModel {

	public static final String findByName = "CollectionAccount_FindByName";
	
	private long id;
	private String name;
	private String description;
	private String line; //carteira
	private String operation;
	private String agreement; // convênio
	private BankAccount bankAccount;
	private boolean registered;
	
	@Override
	@Id @GeneratedValue(strategy=GenerationType.TABLE, generator="BankingSequence")
	@TableGenerator(name="BankingSequence", table="BankingSequence", schema="banking", 
			pkColumnName="cTable", pkColumnValue="CollectionSequence", valueColumnName="cNext", initialValue=1, allocationSize=1)
	@Column(name="cId")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	@Column(name="cLine")
	public String getLine(){
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	
	
	@Column(name="cOperation")
	public String getOperation(){
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	

	@ManyToOne
	@JoinColumn(name="cBankAccount")
	public BankAccount getBankAccount(){
		return bankAccount;
	}
	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}
	

	@Column(name="cAgreement")
	public String getAgreement() {
		return agreement;
	}
	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}
	
	
	@Column(name="cDescription")
	public String getDescription(){
		return description;
	}
	public void setDescription(String description){
		this.description = description;
	}
	
	
	@Column(name="cName")
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	// informa se a cobrança é com ou sem registro
	@Column(name="cRegistered")
	public boolean isRegistered(){
		return registered;
	}
	public void setRegistered(boolean registered) {
		this.registered = registered;
	}
	
}