package net.kallx.banking.account.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.kallx.architecture.repository.GenericFactory;
import net.kallx.banking.account.model.CheckingAccount;
import net.kallx.banking.bank.bean.BankUnitBean;
import net.kallx.banking.collection.bean.CollectionAccountBean;
import net.kallx.banking.collection.model.CollectionAccount;

/**
 * 
 * @author Marcos Vinicius
 *
 */
public class CheckingAccountBean extends BankAccountBean<CheckingAccount> {
	
	private CheckingAccount model;

	private List<CollectionAccountBean> collectionAccounts;

	public CheckingAccountBean() {
		setBankUnit(new BankUnitBean());
	}

	@Override
	public CheckingAccount build(GenericFactory<CheckingAccount> factory) {
		if (model == null)
			model = new CheckingAccount();
			
		model.setBankUnit(getBankUnit().build(null));
		model.setName(name);
		model.setNumber(number);
		model.setOpening(opening);
		model.setStart(start);
		model.setStartBalance(startBalance);
		model.setDigit(getDigit());
		model.setActive(isActive());
		
		Set<CollectionAccount> collAcclist = new HashSet<CollectionAccount>();
		for (CollectionAccountBean coll : getCollectionAccounts()) {
			collAcclist.add(coll.build(null));
		}
		model.setCollectionAccounts(collAcclist);
		
		return model;
	}

	@Override
	public CheckingAccount getModel() {
		return model;
	}
	@Override
	public void setModel(CheckingAccount model) {
		this.model = model;
	}
	
	
	@Override
	public CheckingAccountBean load(CheckingAccount model) {
		
		if (model == null)
			throw new IllegalStateException();
		
		load(model, true);
		// loadEntries(model);
		
		return this;
		
	}

	public CheckingAccountBean load(CheckingAccount model, boolean onlyLazy) {
		
		if (model == null)
			throw new IllegalStateException();
		
		this.model = model;
		this.id = model.getId();
		this.bankUnit = new BankUnitBean().load(model.getBankUnit());
		this.name = model.getName();
		this.digit = model.getDigit();
		this.number = model.getNumber();
		this.opening = model.getOpening();
		this.start = model.getStart();
		this.startBalance = model.getStartBalance();
		this.active = model.isActive();
		
		List<CollectionAccountBean> collAcclist = new ArrayList<CollectionAccountBean>();
		for (CollectionAccount coll : model.getCollectionAccounts()) {
			collAcclist.add(new CollectionAccountBean().load(coll));
		}
		setCollectionAccounts(collAcclist);
		
		return this;
	}

	public List<CollectionAccountBean> getCollectionAccounts() {
		return collectionAccounts == null ? new ArrayList<CollectionAccountBean>() : collectionAccounts;
	}

	public void setCollectionAccounts(List<CollectionAccountBean> collectionAccounts) {
		this.collectionAccounts = collectionAccounts;
	}
}
