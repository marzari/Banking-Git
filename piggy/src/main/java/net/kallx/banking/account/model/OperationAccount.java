package net.kallx.banking.account.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import net.kallx.architecture.model.GenericModel;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class OperationAccount implements Account, GenericModel {

	private static final long serialVersionUID = 1L;
	
	protected long id;

	protected String name;
	protected Set<Entry> entries;
	
	
	public OperationAccount() {
		entries = new HashSet<>();
	}
	
	
	@Override
	@Id @GeneratedValue(strategy=GenerationType.TABLE, generator="BankingSequence")
	@TableGenerator(name="BankingSequence", table="BankingSequence", schema="banking", 
			pkColumnName="cTable", pkColumnValue="OperationSequence", valueColumnName="cNext", initialValue=1, allocationSize=1)
	@Column(name="cId")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	

	@Override
	@Transient
	public BigDecimal balance() {
		
		BigDecimal ret = new BigDecimal(0);
		
		for (Entry entry : entries) {
			if (entry.isCredit())
				ret = ret.add(entry.getAmount());
			else if (entry.isCredit())
				ret = ret.subtract(entry.getAmount());
		}
		
		return ret;
	}
	
	
	@Override
	@OneToMany(targetEntity=OperationEntry.class, fetch=FetchType.EAGER, mappedBy="account")
	public Set<Entry> getEntries() {
		return entries;
	}
	@Override
	public void setEntries(Set<Entry> entries) {
		this.entries = entries;
	}
	@Override
	public void addEntry(Entry entry) {
		entries.add(entry);
	}
	
	
	@Override
	@Column(name="cName")
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
}


