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
import net.kallx.banking.collection.bean.DiscountTypeBean;
import net.kallx.banking.commons.model.DiscountType;
import net.kallx.banking.facade.BankingModuleFacade;
import net.kallx.portalManager.control.ConversationManager;

@ConversationScoped
@Named("discountTypeController")
public class DiscountTypeController extends AbstractModelController<DiscountType> {

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

	/**
	 * '1' = Valor Fixo Até a Data Informada<br>
	 * '2' = Percentual Até a Data Informada<br>
	 * '3' = Valor por Antecipação Dia Corrido<br>
	 * '4' = Valor por Antecipação Dia Útil<br>
	 * '5' = Percentual Sobre o Valor Nominal Dia Corrido<br>
	 * '6' = Percentual Sobre o Valor Nominal Dia Útil<br>
	 * '7' = Cancelamento de Desconto
	 */
	private void createBeans() {
		if (beans == null || beans.isEmpty()) {
			persistNewBeanInstance("1", "Valor Fixo Até a Data Informada");
			persistNewBeanInstance("2", "Percentual Até a Data Informada");
			persistNewBeanInstance("3", "Valor por Antecipação Dia Corrido");
			persistNewBeanInstance("4", "Valor por Antecipação Dia Útil");
			persistNewBeanInstance("5", "Percentual Sobre o Valor Nominal Dia Corrido");
			persistNewBeanInstance("6", "Percentual Sobre o Valor Nominal Dia Útil");
			persistNewBeanInstance("7", "Cancelamento de Desconto");
		}
	}

	private void persistNewBeanInstance(String code, String description) {
		DiscountTypeBean discountTypeBean = new DiscountTypeBean();
		discountTypeBean.setCode(code);
		discountTypeBean.setDescription(description);

		editing = discountTypeBean;
		save();
	}

	@Override
	public Class<DiscountType> getModelClazz() {
		return DiscountType.class;
	}

	@Override
	public GenericBean<DiscountType> createBean() {
		return new DiscountTypeBean();
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
