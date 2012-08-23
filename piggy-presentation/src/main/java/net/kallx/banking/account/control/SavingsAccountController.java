package net.kallx.banking.account.control;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.kallx.architecture.control.AbstractModelController;
import net.kallx.architecture.control.ConversationSubject;
import net.kallx.architecture.control.GenericBean;
import net.kallx.architecture.facade.ModuleFacade;
import net.kallx.banking.account.bean.SavingsAccountBean;
import net.kallx.banking.account.model.SavingsAccount;
import net.kallx.banking.facade.BankingModuleFacade;
import net.kallx.portalManager.control.ConversationManager;

/**
 * 
 * @author Marcos Vinicius
 *
 */
@Named("savingsAccountController")
@ConversationScoped
public class SavingsAccountController extends AbstractModelController<SavingsAccount> {

	@EJB
	private BankingModuleFacade facade;
	
	@Override
	public Class<SavingsAccount> getModelClazz() {
		return SavingsAccount.class;
	}
	
	@Override
	public ModuleFacade getFacade() {
		return facade;
	}
	
	@Override
	public GenericBean<SavingsAccount> createBean() {
		return new SavingsAccountBean();
	}

	
	@Inject
	private Conversation conversation;
	
	@Inject
	private ConversationManager conversationManager;
	
	@Override
	public Conversation getConversation() {
		return conversation;
	}
	@Override
	public ConversationSubject getConversationSubject() {
		return conversationManager;
	}
	
}
