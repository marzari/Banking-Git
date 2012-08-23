package net.kallx.banking.account.bean;

import net.kallx.architecture.control.GenericBean;
import net.kallx.architecture.repository.GenericFactory;
import net.kallx.banking.account.model.InvestmentAccount;

/**
 * 
 * @author Marcos Vinicius
 *
 */
public class InvestmentAccountBean extends BankAccountBean<InvestmentAccount> {

	private InvestmentAccount model;
	

	@Override
	public InvestmentAccount build(GenericFactory<InvestmentAccount> model) {
		return null;
	}

	@Override
	public InvestmentAccount getModel() {
		return model;
	}
	@Override
	public void setModel(InvestmentAccount model) {
		this.model = model;
	}

	@Override
	public GenericBean<InvestmentAccount> load(InvestmentAccount factory) {
		// TODO Auto-generated method stub
		return null;
	}

}
