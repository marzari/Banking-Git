package net.kallx.banking.bank.control;

import java.util.ArrayList;
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
import net.kallx.banking.bank.bean.BankBean;
import net.kallx.banking.bank.model.Bank;
import net.kallx.banking.facade.BankingModuleFacade;
import net.kallx.portalManager.control.ConversationManager;

import org.primefaces.event.RowEditEvent;

/**
 * 
 * @author Marcos Vinicius
 *
 */
@Named("bankController")
@ConversationScoped
public class BankController extends AbstractModelController<Bank> {

	@EJB
	private BankingModuleFacade facade;
	
	@Override
	public Class<Bank> getModelClazz() {
		return Bank.class;
	}
	
	@Override
	public ModuleFacade getFacade() {
		return facade;
	}

	@Override
	public GenericBean<Bank> createBean() {
		return new BankBean();
	}

	public List<BankBean> getBanksAC(String query){
		List<BankBean> suggestions = new ArrayList<BankBean>();  
		
		query = query.toLowerCase().trim();
		for (GenericBean<Bank> bb : beans) {
			BankBean b = (BankBean) bb;
			String searchString = b.getCode() + " " + b.getName();
			if (searchString.toLowerCase().contains(query)) 
				suggestions.add(b);
		}
          
        return suggestions;  
	}

	public void addBank(){
		beans.add(new BankBean());
	}

	public void saveBank(RowEditEvent event){
		BankBean obj = (BankBean) event.getObject();
		editing = obj;
		saveCore();
	}
	
	@PostConstruct
	public void init() {
		loadBeans();
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