package net.kallx.banking.account.model;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="InvestmentAccount", schema="banking")
public class InvestmentAccount extends BankAccount {

	private static final long serialVersionUID = 1L;

}
