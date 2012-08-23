package net.kallx.banking.account.control;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.kallx.architecture.control.AbstractModelController;
import net.kallx.architecture.control.ConversationSubject;
import net.kallx.architecture.control.GenericBean;
import net.kallx.architecture.facade.ModuleFacade;
import net.kallx.banking.account.bean.CreditCardAccountBean;
import net.kallx.banking.account.model.CreditCardAccount;
import net.kallx.banking.facade.BankingModuleFacade;
import net.kallx.portalManager.control.ConversationManager;

/**
 * 
 * @author Marcos Vinicius
 *
 */
@Named("creditCardAccountController")
@ConversationScoped
public class CreditCardAccountController extends AbstractModelController<CreditCardAccount> {

	private BankingModuleFacade facade;
	
	@Override
	public Class<CreditCardAccount> getModelClazz() {
		return CreditCardAccount.class;
	}
	
	@Override
	public ModuleFacade getFacade() {
		return facade;
	}
	

	@Override
	public GenericBean<CreditCardAccount> createBean() {
		return new CreditCardAccountBean();
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
