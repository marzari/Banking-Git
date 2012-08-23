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
import net.kallx.banking.account.bean.CashAccountBean;
import net.kallx.banking.account.model.CashAccount;
import net.kallx.banking.facade.BankingModuleFacade;
import net.kallx.portalManager.control.ConversationManager;

/**
 * 
 * @author Marcos Vinicius
 *
 */
@Named("cashAccountController")
@ConversationScoped
public class CashAccountController extends AbstractModelController<CashAccount> {

	@EJB
	private BankingModuleFacade facade;

	
	@Override
	public Class<CashAccount> getModelClazz() {
		return CashAccount.class;
	}
	
	@Override
	public ModuleFacade getFacade() {
		return facade;
	}
	

	@Override
	public GenericBean<CashAccount> createBean() {
		return new CashAccountBean();
	}

//	public void listApplicationLoading(@Observes PortalApplication application){
//		if (application.getName().equals("zebra") || application.getName().equals("budget"))
//			beans = updateBeans();
//	}
	
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
