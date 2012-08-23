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
import net.kallx.banking.collection.bean.CollectionFileBean;
import net.kallx.banking.collection.model.CollectionFile;
import net.kallx.banking.facade.BankingModuleFacade;
import net.kallx.portalManager.control.ConversationManager;

@ConversationScoped
@Named("collectionFileController")
public class CollectionFileController extends AbstractModelController<CollectionFile> {

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
	public Class<CollectionFile> getModelClazz() {
		return CollectionFile.class;
	}

	@Override
	public GenericBean<CollectionFile> createBean() {
		return new CollectionFileBean();
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
