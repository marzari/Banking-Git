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
import net.kallx.banking.collection.bean.DocumentTypeERPBean;
import net.kallx.banking.commons.model.DocumentTypeERP;
import net.kallx.banking.facade.BankingModuleFacade;
import net.kallx.portalManager.control.ConversationManager;

@ConversationScoped
@Named("doctyperpController")
public class DocumentTypeERPController extends AbstractModelController<DocumentTypeERP> {

	@EJB
	private BankingModuleFacade facade;

	@Override
	public ModuleFacade getFacade() {
		return facade;
	}
	
	@PostConstruct
	void init(){
		loadBeans();
		createBeansData();
	}
	
	/**
	 * Must be loaded only the first time the controller is executed.<br>
	 * FIXME find another way of creating data at app initialization, outside controller layer.
	 */
	private void createBeansData() {
		if (beans == null || beans.size() == 0) {
			DocumentTypeERPBean pb = new DocumentTypeERPBean();
			pb.setCode("T1");
			pb.setDescription("Document Type 1");
			editing = pb;
			save();

			pb = new DocumentTypeERPBean();
			pb.setCode("T2");
			pb.setDescription("Document Type 2");
			editing = pb;
			save();
		}
	}

	@Override
	public Class<DocumentTypeERP> getModelClazz() {
		return DocumentTypeERP.class;
	}

	@Override
	public GenericBean<DocumentTypeERP> createBean() {
		return new DocumentTypeERPBean();
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
