package net.kallx.banking.collection.control;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.kallx.architecture.control.AbstractModelController;
import net.kallx.architecture.control.ConversationSubject;
import net.kallx.architecture.control.GenericBean;
import net.kallx.architecture.facade.ModuleFacade;
import net.kallx.banking.collection.bean.ReceiptInstructionBean;
import net.kallx.banking.collection.model.ReceiptInstruction;
import net.kallx.banking.facade.BankingModuleFacade;
import net.kallx.portalManager.control.ConversationManager;

@SessionScoped
@Named("receiptInstructionController")
public class ReceiptInstructionController extends AbstractModelController<ReceiptInstruction> {

	@EJB
	private BankingModuleFacade facade;

	@Override
	public ModuleFacade getFacade() {
		return facade;
	}

	@PostConstruct
	void init() {
		loadBeans();
		createBeans();
	}

	private void createBeans() {
		if (beans == null || beans.isEmpty()) {
			persistNewBeanInstance("1", "DOC");
			persistNewBeanInstance("2", "TED");
			persistNewBeanInstance("3", "Boleto");
			persistNewBeanInstance("4", "Débito em Conta");
		}
	}

	private void persistNewBeanInstance(String code, String description) {
		ReceiptInstructionBean recInstruction = new ReceiptInstructionBean();
		recInstruction.setCode(code);
		recInstruction.setDescription(description);

		editing = recInstruction;
		save();
	}

	@Override
	public Class<ReceiptInstruction> getModelClazz() {
		return ReceiptInstruction.class;
	}

	@Override
	public GenericBean<ReceiptInstruction> createBean() {
		return new ReceiptInstructionBean();
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
