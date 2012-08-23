package net.kallx.banking.collection.control;

import java.util.List;

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
import net.kallx.banking.collection.bean.CollectionAccountBean;
import net.kallx.banking.collection.model.CollectionAccount;
import net.kallx.banking.facade.BankingModuleFacade;
import net.kallx.portalManager.control.ConversationManager;

import org.primefaces.event.RowEditEvent;

@Named("collectionAccountController")
@ConversationScoped
public class CollectionAccountController extends AbstractModelController<CollectionAccount> {

	@EJB
	private BankingModuleFacade facade;

	@Override
	public Class<CollectionAccount> getModelClazz() {
		return CollectionAccount.class;
	}
	
	@Override
	public ModuleFacade getFacade() {
		return facade;
	}
	
	@Override
	public GenericBean<CollectionAccount> createBean() {
		return new CollectionAccountBean();
	}

	public void add(){
		GenericBean<CollectionAccount> createBean = createBean();
		beans.add(createBean);
	}
	
	@SuppressWarnings("unchecked")
	public void saveEditRow(RowEditEvent event){
		editing = (GenericBean<CollectionAccount>) event.getObject();
		save();
	}
	
	@PostConstruct
	void init(){
		loadBeans();
	}

	@Override
	public void loadBeans() {
		resetBeans();
		List<CollectionAccount> list = getFacade().getRepository(getModelClazz()).list();
		for (CollectionAccount m : list) {
			if (m.getBankAccount() != null && m.getBankAccount().isActive()) {
				GenericBean<CollectionAccount> bean = createBean();
				bean.load(m);
				getBeans().add(bean);
			}
		}
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
