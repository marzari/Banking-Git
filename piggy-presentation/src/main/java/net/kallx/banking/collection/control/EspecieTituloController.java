package net.kallx.banking.collection.control;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.kallx.architecture.control.AbstractModelController;
import net.kallx.architecture.control.ConversationSubject;
import net.kallx.architecture.control.GenericBean;
import net.kallx.architecture.facade.ModuleFacade;
import net.kallx.banking.collection.bean.EspecieTituloBean;
import net.kallx.banking.collection.model.EspecieTitulo;
import net.kallx.banking.facade.BankingModuleFacade;
import net.kallx.portalManager.control.ConversationManager;

@ConversationScoped
@Named("especieTituloController")
public class EspecieTituloController extends AbstractModelController<EspecieTitulo> {

	@EJB
	private BankingModuleFacade facade;

	@Override
	public ModuleFacade getFacade() {
		return facade;
	}
	
	@PostConstruct
	void init(){
		loadBeans();
	}

	@Override
	public Class<EspecieTitulo> getModelClazz() {
		return EspecieTitulo.class;
	}

	@Override
	public GenericBean<EspecieTitulo> createBean() {
		return new EspecieTituloBean();
	}
	
	@Inject
	private ConversationManager conversationManager;
	
	@Inject
	private Conversation conversation;

	@Override
	public Conversation getConversation() {
		return conversation;
	}

	@Override
	public ConversationSubject getConversationSubject() {
		return conversationManager;
	}


}
