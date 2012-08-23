package net.kallx.banking.statements.control;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import net.kallx.banking.account.bean.OperationAccountBean;
import net.kallx.banking.account.bean.OperationEntryBean;

@Named("statementController")
@ConversationScoped
public class StatementController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private OperationAccountBean<?> account;

	private List<OperationEntryBean> beans;
	
	
	public OperationAccountBean<?> getAccount() {
		return account;
	}
	public void setAccount(OperationAccountBean<?> account) {
		this.account = account;
		if (this.account != null)
			beans = this.account.getEntries();
	}
	
	
	public List<OperationEntryBean> getBeans() {
		return beans;
	}
	public void setBeans(List<OperationEntryBean> beans) {
		this.beans = beans;
	}
	
	
	public boolean hasAccount(){
		return (account != null);
	}
	
}
