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
import net.kallx.banking.collection.bean.PortfolioBean;
import net.kallx.banking.collection.model.Portfolio;
import net.kallx.banking.facade.BankingModuleFacade;
import net.kallx.portalManager.control.ConversationManager;

@ConversationScoped
@Named("portfolioController")
public class PortfolioController extends AbstractModelController<Portfolio> {

	@EJB
	private BankingModuleFacade facade;

	@Override
	public ModuleFacade getFacade() {
		return facade;
	}

	@PostConstruct
	void init() {
		loadBeans();
		createBeansData();
	}

	/**
	 * Must be loaded only the first time the controller is executed.<br>
	 * FIXME find another way of creating data at app initialization, outside controller layer.
	 */
	private void createBeansData() {
		if (beans.size() == 0) {
			PortfolioBean pb = new PortfolioBean();
			pb.setCode("CS");
			pb.setDescription("Cobrança Simples");
			editing = pb;
			save();

			pb = new PortfolioBean();
			pb.setCode("CVin");
			pb.setDescription("Cobrança Vinculada");
			editing = pb;
			save();

			pb = new PortfolioBean();
			pb.setCode("CC");
			pb.setDescription("Cobrança Caucionada");
			editing = pb;
			save();

			pb = new PortfolioBean();
			pb.setCode("CD");
			pb.setDescription("Cobrança Descontada");
			editing = pb;
			save();

			pb = new PortfolioBean();
			pb.setCode("CVdr");
			pb.setDescription("Cobrança Vendor");
			editing = pb;
			save();
		}
	}

	@Override
	public Class<Portfolio> getModelClazz() {
		return Portfolio.class;
	}

	@Override
	public GenericBean<Portfolio> createBean() {
		return new PortfolioBean();
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
