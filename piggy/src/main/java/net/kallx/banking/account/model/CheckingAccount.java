package net.kallx.banking.account.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.kallx.banking.collection.model.CollectionAccount;

/**
 * Represents a current account (checking account).
 * 
 * @author MarcosVinicius
 * 
 */
@Entity
@Table(name = "CurrentAccount", schema = "banking")
@NamedQueries({ @NamedQuery(name = "CheckingAccount_findByNumber", query = "select e from CheckingAccount e where e.number = :number") })
public class CheckingAccount extends BankAccount {

	public static String CheckingAccount_findByNumber = "CheckingAccount_findByNumber";

	private Set<CollectionAccount> collectionAccounts;

	@OneToMany(mappedBy = "bankAccount", fetch = FetchType.EAGER)
	public Set<CollectionAccount> getCollectionAccounts() {
		return collectionAccounts;
	}

	public void setCollectionAccounts(Set<CollectionAccount> collectionAccounts) {
		this.collectionAccounts = collectionAccounts;
	}

}
