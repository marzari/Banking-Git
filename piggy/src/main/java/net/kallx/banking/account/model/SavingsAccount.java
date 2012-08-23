package net.kallx.banking.account.model;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="SavingsAccount", schema="banking")
public class SavingsAccount extends BankAccount {

	private static final long serialVersionUID = 1L;

}
