package net.kallx.banking.account.control;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import net.kallx.architecture.control.AbstractModelController;
import net.kallx.architecture.control.ConversationSubject;
import net.kallx.architecture.control.GenericBean;
import net.kallx.architecture.facade.ModuleFacade;
import net.kallx.architecture.repository.Repository;
import net.kallx.banking.account.bean.CheckingAccountBean;
import net.kallx.banking.account.model.CheckingAccount;
import net.kallx.banking.bank.bean.BankUnitBean;
import net.kallx.banking.bank.model.BankUnit;
import net.kallx.banking.collection.bean.CollectionAccountBean;
import net.kallx.banking.collection.control.BankUnitSelectionDataModel;
import net.kallx.banking.collection.model.CollectionAccount;
import net.kallx.banking.facade.BankingModuleFacade;
import net.kallx.portalManager.control.ConversationManager;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

/**
 * 
 * @author Marcos Vinicius
 * 
 */
@Named("checkingAccountController")
@ConversationScoped
public class CheckingAccountController extends AbstractModelController<CheckingAccount> {

	@EJB
	private BankingModuleFacade facade;

	@EJB
	private Repository<CheckingAccount> accountRepository;

	private BankUnitSelectionDataModel bankUnitSelect;

	@Override
	public Class<CheckingAccount> getModelClazz() {
		return CheckingAccount.class;
	}

	@Override
	public ModuleFacade getFacade() {
		return facade;
	}

	@Override
	public GenericBean<CheckingAccount> createBean() {
		return new CheckingAccountBean();
	}

	@Inject
	private Conversation conversation;

	@Inject
	private ConversationManager conversationManager;

	private boolean showDisableDialog;

	@Override
	public Conversation getConversation() {
		return conversation;
	}

	@Override
	public ConversationSubject getConversationSubject() {
		return conversationManager;
	}

	public CheckingAccountController() {
		editing = new CheckingAccountBean();
	}
	
	@PostConstruct
	public void init() {
		loadBeans();
	}

	@Override
	protected void saveCore() {
		setActionOnSave();
		facade.saveBankAccount(editing.build(null));
		if (!getBeans().contains(editing))
			getBeans().add(editing);
	}

	@Override
	protected void doPostSave() {
		super.doPostSave();
		editing = createBean();
		loadBeans();
	}
	
	public void deactivateAccount(ActionEvent evt) {
		setShowDisableDialog(false); // desabilita a flag de qualquer jeito
		
		String id = evt.getComponent().getId();			
		if (id != null && "btnSim".equals(id)) {
			CheckingAccountBean bean = (CheckingAccountBean) editing;
			bean.setActive(false);

			editing = bean;
			setAction(ControllerActionEnum.EDIT);
			setEditMessage("Conta inativada com sucesso!");
			save();
		}
	}

	@Override
	public void delete() {
		setAction(ControllerActionEnum.DELETE);
		try {
			deleteCore(); 
			showSuccessMessage();
			create();
		} catch (Exception e) {
			// setFailMessage("Há registros relacionados à essa conta. Não será possível excluir.");
			setShowDisableDialog(hasConstraintViolationException(e));
		}
	}
	
	private boolean hasConstraintViolationException(Throwable t) {
		boolean has = false;
		if (t.getCause() instanceof ConstraintViolationException) {
			has = true;
		} else {
			has = hasConstraintViolationException(t.getCause());
		}
		
		return has;
	}
	
	public String getAccountByNumber(AjaxBehaviorEvent evt) {
		InputText input = (InputText) evt.getComponent();
		String value = (String) input.getValue();

		if (value != null && !"".equals(value)) {
			try {
				CheckingAccount account = accountRepository.namedQuery(CheckingAccount.CheckingAccount_findByNumber,
						CheckingAccount.class, "number", value);
				if (account != null)
					editing = new CheckingAccountBean().load(account);
			} catch (NoResultException e) {
				// e.printStackTrace();
			}

			return "";
		}
		return null;
	}

	public void add() {
		CheckingAccountBean chkAccBean = (CheckingAccountBean) editing;

		List<CollectionAccountBean> collBeans = null;
		if (chkAccBean.getCollectionAccounts() == null || chkAccBean.getCollectionAccounts().isEmpty()) {
			collBeans = new ArrayList<CollectionAccountBean>();
			collBeans.add(new CollectionAccountBean());
			chkAccBean.setCollectionAccounts(collBeans);
		} else {
			chkAccBean.getCollectionAccounts().add(new CollectionAccountBean());
		}

		editing = chkAccBean;
	}

	public void onRowSelect(SelectEvent event) {
		BankUnitBean bean = (BankUnitBean) event.getObject();

		CheckingAccountBean checkingBean = (CheckingAccountBean) editing;
		checkingBean.setBankUnit(bean);

		editing = checkingBean;
	}

	public void saveEditRow(RowEditEvent event) {
		CollectionAccountBean collAccBean = (CollectionAccountBean) event.getObject();

		CheckingAccountBean bean = (CheckingAccountBean) editing;
		bean.getCollectionAccounts().add(collAccBean);

		editing = bean;
	}

	public void removeCollectionAccount(CollectionAccountBean event) {
		try {
			facade.getRepository(CollectionAccount.class).load(event.getId()).remove();
			
			CheckingAccountBean bean = (CheckingAccountBean) editing;
			bean.getCollectionAccounts().remove(event);

			editing = bean;
		} catch (Exception e) {
			setAction(ControllerActionEnum.DELETE);
			setFailMessage("Convênio relacionado a Título(s). Não é possível excluir.");
			showFailMessage(e);
		}
	}

	public BankUnitSelectionDataModel getBankUnitSelect() {
		List<BankUnit> list = facade.getRepository(BankUnit.class).list();

		List<BankUnitBean> bankUnitBean = new ArrayList<BankUnitBean>();
		for (BankUnit bean : list) {
			bankUnitBean.add(new BankUnitBean().load(bean));
		}
		bankUnitSelect = new BankUnitSelectionDataModel(bankUnitBean);
		return bankUnitSelect;
	}

	public void setBankUnitSelect(BankUnitSelectionDataModel bankUnitSelect) {
		this.bankUnitSelect = bankUnitSelect;
	}

	public boolean isShowDisableDialog() {
		return showDisableDialog;
	}

	public void setShowDisableDialog(boolean showDisableDialog) {
		this.showDisableDialog = showDisableDialog;
	}
}