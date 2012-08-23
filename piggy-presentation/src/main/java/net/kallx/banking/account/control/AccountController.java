package net.kallx.banking.account.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.kallx.architecture.control.GenericBean;
import net.kallx.banking.account.bean.OperationAccountBean;
import net.kallx.banking.account.model.CheckingAccount;
import net.kallx.banking.account.model.CreditCardAccount;

/**
 * 
 * @author Marcos Vinicius
 *
 */
@Named("accountController")
@ConversationScoped
public class AccountController implements Serializable {

	@Inject
	private CheckingAccountController caController;
	
	@Inject
	private CreditCardAccountController ccControler;

	private List<OperationAccountBean<?>> accounts;
	
	private void loadAccounts(){
		accounts = new ArrayList<OperationAccountBean<?>>();
		List<GenericBean<CheckingAccount>> beans = caController.getBeans();
		for (GenericBean<CheckingAccount> cab : beans) {
			accounts.add((OperationAccountBean<?>) cab);
		}
		List<GenericBean<CreditCardAccount>> beans2 = ccControler.getBeans();
		for (GenericBean<CreditCardAccount> ccb : beans2) {
			accounts.add((OperationAccountBean<?>) ccb);
		}
	}
	
	public List<OperationAccountBean<?>> getAccounts() {
		if (accounts == null)
			loadAccounts();
		return accounts;
	}
	
	
	public List<OperationAccountBean<?>> getAccountsAC(String query){
		
		if (accounts == null)
			loadAccounts();
		
		List<OperationAccountBean<?>> suggestions = new ArrayList<OperationAccountBean<?>>();  
        for(OperationAccountBean<?> b : accounts) {  
            if(b.getDescription().startsWith(query))  
                suggestions.add(b);  
        }  
          
        return suggestions;  
		
	}
	
	
	
}
