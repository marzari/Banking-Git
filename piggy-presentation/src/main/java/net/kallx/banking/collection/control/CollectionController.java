package net.kallx.banking.collection.control;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import net.kallx.architecture.control.AbstractModelController;
import net.kallx.architecture.control.ConversationSubject;
import net.kallx.architecture.control.GenericBean;
import net.kallx.architecture.facade.ModuleFacade;
import net.kallx.architecture.repository.Repository;
import net.kallx.banking.collection.bean.CollectionBean;
import net.kallx.banking.collection.bean.CollectionFilterBean;
import net.kallx.banking.collection.bean.DiscountTypeBean;
import net.kallx.banking.collection.bean.FilterTypeBean;
import net.kallx.banking.collection.bean.SelectionBean;
import net.kallx.banking.collection.model.Collection;
import net.kallx.banking.collection.model.CollectionSituation;
import net.kallx.banking.collection.model.state.CollectionState;
import net.kallx.banking.collection.model.state.CollectionStateFactory;
import net.kallx.banking.collection.model.state.StateChangeReport;
import net.kallx.banking.commons.model.Fine.FineCodeEnum;
import net.kallx.banking.commons.model.Interest.InterestCodeEnum;
import net.kallx.banking.commons.model.Payee;
import net.kallx.banking.commons.model.Protest.ProtestTypeEnum;
import net.kallx.banking.facade.BankingModuleFacade;
import net.kallx.banking.service.CollectionChangeRequest;
import net.kallx.banking.service.CollectionChangeRequestProperty;
import net.kallx.modules.register.model.LegalRegister;
import net.kallx.portalManager.control.ConversationManager;
import net.kallx.security.control.SecureSessionController;
import net.kallx.utils.date.DateUtils;
import net.kallx.utils.validation.CNPJValidation;

import org.primefaces.component.inputmask.InputMask;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named("collectionController")
@ConversationScoped
public class CollectionController extends AbstractModelController<Collection> {

	@Inject
	private SecureSessionController user;

	@EJB
	private Repository<LegalRegister> companyRepository;

	@EJB
	private BankingModuleFacade facade;

	private CollectionBean[] selectedCollections;
	private CollectionBean selectedBean;
	private SelectionDataModel dataModel;
	private CollectionFilterBean filter;
	private FilterTypeBean select;
	private String especie;
	private StreamedContent pdfFile;
	private Calendar newDate;
	private double multa;
	private double juros;
	private boolean calculated = false;
	private List<String> reportMessage = new ArrayList<>();
	private long collectionAccountId;
	private long collectionAccountId2;
	

	private SelectionBean selectionBean;

	private boolean selectedInterestCode = true;
	private boolean selectedDiscountType = true;
	
	public CollectionController() {
		selectionBean = new SelectionBean();
		filter = new CollectionFilterBean();
		editing = new CollectionBean();
	}

	public CollectionFilterBean getFilter() {
		return filter;
	}

	public void setFilter(CollectionFilterBean filter) {
		this.filter = filter;
	}

	public FilterTypeBean getSelect() {
		return select;
	}

	public void setSelect(FilterTypeBean select) {
		this.select = select;
	}

	@Override
	public Class<Collection> getModelClazz() {
		return Collection.class;
	}

	@Override
	public ModuleFacade getFacade() {
		return facade;
	}

	@Override
	public GenericBean<Collection> createBean() {
		selectedInterestCode = false;
		return new CollectionBean();
	}

	public void add() {
		GenericBean<Collection> createBean = createBean();
		beans.add(createBean);
	}

	@SuppressWarnings("unchecked")
	public void saveEditRow(RowEditEvent event) {
		editing = (GenericBean<Collection>) event.getObject();
		save();
	}

	@PostConstruct
	void init() {
		loadBeans();
		_init();
	}

	void _init() {

		List<CollectionBean> list = new ArrayList<>();
		for (GenericBean<Collection> genericBean : beans) {
			list.add((CollectionBean) genericBean);
		}
		dataModel = new SelectionDataModel(list);

	}

	public CollectionBean[] getSelectedCollections() {
		return selectedCollections;
	}

	public void setSelectedCollections(CollectionBean[] selectedCollections) {
		this.selectedCollections = selectedCollections;
	}

	public SelectionDataModel getDataModel() {
		return dataModel;
	}

	public void setDataModel(SelectionDataModel dataModel) {
		this.dataModel = dataModel;
	}

	public CollectionBean getSelectedBean() {
		return selectedBean;
	}

	public void setSelectedBean(CollectionBean selectedBean) {
		this.selectedBean = selectedBean;
	}

	public void liquidatedCollection() {
		List<Collection> collections = new ArrayList<>();
		for (CollectionBean cb : selectedCollections) {
			collections.add(cb.getModel());
		}

		this.reportMessage = facade.applyStatus(collections, CollectionSituation.LIQUIDATED);
		init();

	}

	public void protestCollection() {
		List<Collection> collections = new ArrayList<>();
		for (CollectionBean cb : selectedCollections) {
			collections.add(cb.getModel());
		}

		this.reportMessage = facade.applyStatus(collections, CollectionSituation.PROTESTED);
		init();

	}

	public void openCollection() {
		List<Collection> collections = new ArrayList<>();
		for (CollectionBean cb : selectedCollections) {
			collections.add(cb.getModel());
		}

		this.reportMessage = facade.applyStatus(collections, CollectionSituation.OPEN);
		init();

	}

	public void cancelCollection() {
		List<Collection> collections = new ArrayList<>();
		for (CollectionBean cb : selectedCollections) {
			collections.add(cb.getModel());
		}

		this.reportMessage = facade.applyStatus(collections,
				CollectionSituation.QUITED);
		init();

	}

	public List<String> getReportMessage() {
		return reportMessage;
	}

	public void setReportMessage(List<String> reportMessage) {
		this.reportMessage = reportMessage;
	}

	public void printCollection() {

		if (selectedCollections == null)
			return;

		List<Collection> list = new ArrayList<>();
		for (CollectionBean collection : selectedCollections) {
			list.add(collection.getModel());
		}

		byte[] arquivo = facade.printCollections(list);
		InputStream stream = new ByteArrayInputStream(arquivo);
		pdfFile = new DefaultStreamedContent(stream, "application/pdf",
				"boletos.pdf");
		init();
	}

	public void generateFileCollection() {
		// service.generateFile(getIds());
	}

	public void executeFilter() {
		String queryName = filter.getQueryName();
		List<Collection> namedQuery = null;

		// não informado nada no filtro, retorna todos os registros do banco
		if ((filter.getSelectEspecie() == null || filter.getSelectEspecie().length() < 1)
				&& (filter.getInitialDateMaturity() == null && filter.getFinalDateMaturity() == null)
				&& (filter.getInitialDateGenerate() == null && filter.getFinalDateGenerate() == null))

			namedQuery = facade.getRepository(Collection.class).namedQuery(queryName);

		// informado somente data de geraçao
		else if ((filter.getSelectEspecie() == null || filter.getSelectEspecie().length() < 1)
				&& (filter.getInitialDateMaturity() == null && filter.getFinalDateMaturity() == null)
				&& (filter.getInitialDateGenerate() != null && filter.getFinalDateGenerate() != null))

			namedQuery = facade.getRepository(Collection.class).namedQuery(queryName, "issueInit",
					filter.getCalendarInitialDateGenerate(), "issueFinal", filter.getCalendarFinalDateGenerate());

		// informado somente a data de vencimento
		else if ((filter.getSelectEspecie() == null || filter.getSelectEspecie().length() < 1)
				&& (filter.getInitialDateGenerate() == null && filter.getFinalDateGenerate() == null)
				&& (filter.getInitialDateMaturity() != null && filter.getFinalDateMaturity() != null))

			namedQuery = facade.getRepository(Collection.class).namedQuery(queryName, "maturityInit",
					filter.getCalendarInitialDateMaturity(), "maturityFinal", filter.getCalendarFinalDateMaturity());

		// informado somente especie
		else if ((filter.getSelectEspecie() == null || filter.getSelectEspecie().length() > 1)
				&& (filter.getInitialDateGenerate() == null && filter.getFinalDateGenerate() == null)
				&& (filter.getInitialDateMaturity() == null && filter.getFinalDateMaturity() == null))

			namedQuery = facade.getRepository(Collection.class).namedQuery(queryName, "especie",
					filter.getObjectEspecie());

		// informado data de vencimento e data de geração
		else if ((filter.getSelectEspecie() == null || filter.getSelectEspecie().length() < 1)
				&& (filter.getInitialDateGenerate() != null && filter.getFinalDateGenerate() != null)
				&& (filter.getInitialDateMaturity() != null && filter.getFinalDateMaturity() != null))

			namedQuery = facade.getRepository(Collection.class).namedQuery(queryName, "maturityInit",
					filter.getCalendarInitialDateMaturity(), "maturityFinal", filter.getCalendarFinalDateMaturity(),
					"issueInit", filter.getCalendarInitialDateGenerate(), "issueFinal",
					filter.getCalendarFinalDateGenerate());

		// informado especie e data vencimento
		else if ((filter.getSelectEspecie() == null || filter.getSelectEspecie().length() > 1)
				&& (filter.getInitialDateMaturity() != null && filter.getFinalDateMaturity() != null)
				&& (filter.getInitialDateGenerate() == null && filter.getFinalDateGenerate() == null))

			namedQuery = facade.getRepository(Collection.class).namedQuery(queryName, "maturityInit",
					filter.getCalendarInitialDateMaturity(), "maturityFinal", filter.getCalendarFinalDateMaturity(),
					"especie", filter.getObjectEspecie());

		// informado especie e data de geracao
		else if ((filter.getSelectEspecie() == null || filter.getSelectEspecie().length() > 1)
				&& (filter.getInitialDateGenerate() != null && filter.getFinalDateGenerate() != null)
				&& (filter.getInitialDateMaturity() == null && filter.getFinalDateMaturity() == null))

			namedQuery = facade.getRepository(Collection.class).namedQuery(queryName, "especie",
					filter.getSelectEspecie(), "issueInit", filter.getCalendarInitialDateGenerate(), "issueFinal",
					filter.getCalendarFinalDateGenerate());

		// informado todos os campos do filtro
		else if ((filter.getSelectEspecie() == null || filter.getSelectEspecie().length() > 1)
				&& (filter.getInitialDateMaturity() != null && filter.getFinalDateMaturity() != null)
				&& (filter.getInitialDateGenerate() != null && filter.getFinalDateGenerate() != null))

			namedQuery = facade.getRepository(Collection.class).namedQuery(queryName, "maturityInit",
					filter.getCalendarInitialDateMaturity(), "maturityFinal", filter.getCalendarFinalDateMaturity(),
					"especie", filter.getSelectEspecie(), "issueInit", filter.getCalendarInitialDateGenerate(),
					"issueFinal", filter.getCalendarFinalDateGenerate());

		System.out.println("\n\n\n\n\n\n\n\n\n\n" + queryName + "\n\n\n\n\n\n\n\n\n\n");

		// não informado nada no filtro, retorna todos os registros do banco
		if ((filter.getSelectEspecie() == null || filter.getSelectEspecie().length() < 1)
				&& (filter.getInitialDateMaturity() == null && filter.getFinalDateMaturity() == null)
				&& (filter.getInitialDateGenerate() == null && filter.getFinalDateGenerate() == null))

			namedQuery = facade.getRepository(Collection.class).namedQuery(queryName);

		// informado somente data de geraçao
		else if ((filter.getSelectEspecie() == null || filter.getSelectEspecie().length() < 1)
				&& (filter.getInitialDateMaturity() == null && filter.getFinalDateMaturity() == null)
				&& (filter.getInitialDateGenerate() != null && filter.getFinalDateGenerate() != null))

			namedQuery = facade.getRepository(Collection.class).namedQuery(queryName, "issueInit",
					filter.getCalendarInitialDateGenerate(), "issueFinal", filter.getCalendarFinalDateGenerate());

		// informado somente a data de vencimento
		else if ((filter.getSelectEspecie() == null || filter.getSelectEspecie().length() < 1)
				&& (filter.getInitialDateGenerate() == null && filter.getFinalDateGenerate() == null)
				&& (filter.getInitialDateMaturity() != null && filter.getFinalDateMaturity() != null))

			namedQuery = facade.getRepository(Collection.class).namedQuery(queryName, "maturityInit",
					filter.getCalendarInitialDateMaturity(), "maturityFinal", filter.getCalendarFinalDateMaturity());

		// informado somente especie
		else if ((filter.getSelectEspecie() == null || filter.getSelectEspecie().length() > 1)
				&& (filter.getInitialDateGenerate() == null && filter.getFinalDateGenerate() == null)
				&& (filter.getInitialDateMaturity() == null && filter.getFinalDateMaturity() == null))

			namedQuery = facade.getRepository(Collection.class).namedQuery(queryName, "especie",
					filter.getObjectEspecie());

		// informado data de vencimento e data de geração
		else if ((filter.getSelectEspecie() == null || filter.getSelectEspecie().length() < 1)
				&& (filter.getInitialDateGenerate() != null && filter.getFinalDateGenerate() != null)
				&& (filter.getInitialDateMaturity() != null && filter.getFinalDateMaturity() != null))

			namedQuery = facade.getRepository(Collection.class).namedQuery(queryName, "maturityInit",
					filter.getCalendarInitialDateMaturity(), "maturityFinal", filter.getCalendarFinalDateMaturity(),
					"issueInit", filter.getCalendarInitialDateGenerate(), "issueFinal",
					filter.getCalendarFinalDateGenerate());

		// informado especie e data vencimento
		else if ((filter.getSelectEspecie() == null || filter.getSelectEspecie().length() > 1)
				&& (filter.getInitialDateMaturity() != null && filter.getFinalDateMaturity() != null)
				&& (filter.getInitialDateGenerate() == null && filter.getFinalDateGenerate() == null))

			namedQuery = facade.getRepository(Collection.class).namedQuery(queryName, "maturityInit",
					filter.getCalendarInitialDateMaturity(), "maturityFinal", filter.getCalendarFinalDateMaturity(),
					"especie", filter.getObjectEspecie());

		// informado especie e data de geracao
		else if ((filter.getSelectEspecie() == null || filter.getSelectEspecie().length() > 1)
				&& (filter.getInitialDateGenerate() != null && filter.getFinalDateGenerate() != null)
				&& (filter.getInitialDateMaturity() == null && filter.getFinalDateMaturity() == null))

			namedQuery = facade.getRepository(Collection.class).namedQuery(queryName, "especie",
					filter.getSelectEspecie(), "issueInit", filter.getCalendarInitialDateGenerate(), "issueFinal",
					filter.getCalendarFinalDateGenerate());

		// informado todos os campos do filtro
		else if ((filter.getSelectEspecie() == null || filter.getSelectEspecie().length() > 1)
				&& (filter.getInitialDateMaturity() != null && filter.getFinalDateMaturity() != null)
				&& (filter.getInitialDateGenerate() != null && filter.getFinalDateGenerate() != null))

			namedQuery = facade.getRepository(Collection.class).namedQuery(queryName, "maturityInit",
					filter.getCalendarInitialDateMaturity(), "maturityFinal", filter.getCalendarFinalDateMaturity(),
					"especie", filter.getSelectEspecie(), "issueInit", filter.getCalendarInitialDateGenerate(),
					"issueFinal", filter.getCalendarFinalDateGenerate());

		beans = new ArrayList<>();

		if (namedQuery == null) {
			return;
		}

		for (Collection collection : namedQuery) {
			beans.add(new CollectionBean().load(collection));
		}
		_init();

	}

	public List<Long> getIdsSelected() {
		return this.getIds();
	}

	private List<Long> getIds() {

		List<Long> ret = new ArrayList<>();
		if (selectedCollections != null)
			for (CollectionBean cb : selectedCollections) {
				ret.add(cb.getId());
			}

		return ret;

	}

	public void sendCollectionsToBank() {

		if (selectedCollections == null || selectedCollections.length == 0) {
			showMessage(FacesMessage.SEVERITY_INFO,
					"Não há Títulos selecionados! Selecione pelo menos um título para enviar a remessa.");
			return;
		}

		List<Collection> list = new ArrayList<>();
		List<CollectionChangeRequest> requests = new ArrayList<>();
		for (CollectionBean collection : selectedCollections) {
			list.add(collection.getModel());
		}
		
		if (list != null && !list.isEmpty()) {
			if (collectionAccountId2 > 0) {
				CollectionChangeRequest req = new CollectionChangeRequest(
						CollectionChangeRequestProperty.Account,
						collectionAccountId2);
				requests.add(req);
			}

			facade.alterProperty(list, requests);

			init();
			String message = this.facade.exportToEMapping(list);
			showMessage(FacesMessage.SEVERITY_INFO, message);
		}
	}

	public SelectionBean getSelectionBean() {
		return selectionBean;
	}

	public void setSelectionBean(SelectionBean selectionBean) {
		this.selectionBean = selectionBean;
	}

	@Override
	protected void saveCore() {
		setActionOnSave();
		facade.saveCollection(editing.build(null));
		if (!getBeans().contains(editing))
			getBeans().add(editing);
	}

	@Override
	protected void setActionOnSave() {
		super.setActionOnSave();
	}

	@Override
	protected void doPostSave() {
		super.doPostSave();
		editing = createBean();// cuidado, esta linha é necessária à tela de administração de título
	}

	@Override
	protected void deleteCore() throws Exception {
		Collection collection = editing.build(null);

		CollectionState state = CollectionStateFactory.getInstance(collection);
		StateChangeReport status = state.applyStatus(CollectionSituation.CANCELED);
		if (!status.isSuccess()) {
			setFailMessage(status.getMessage());
			throw new Exception(); // FIXME deveria ter uma business exception
		}
	}

	// public void returnSelectedInterestType(ValueChangeEvent evt) {
	// InterestCodeEnum newValue = (InterestCodeEnum) evt.getNewValue();
	// setSelectedInterestRadio(newValue);
	// }

	public void returnSelectedDiscountType(AjaxBehaviorEvent evt) {
		CollectionBean bean = (CollectionBean) editing;
		if (bean != null) {
			DiscountTypeBean discountType = bean.getDiscountType();
			selectedDiscountType = discountType.getDescription().toLowerCase().contains("perc");// FIXME encontrar uma melhor forma de fazer isso
		}
	}

	/**
	 * @param newValue
	 */
	private void setSelectedInterestRadio(InterestCodeEnum newValue) {
		if (InterestCodeEnum.INTEREST_MONTH.equals(newValue)) {
			selectedInterestCode = true;
		} else {
			selectedInterestCode = false;
		}
	}

	public void doAlterations() {
		if (selectedCollections == null)
			return;

		CollectionBean bean = (CollectionBean) editing;
		
			List<CollectionChangeRequest> requests = new ArrayList<>();
		 	List<Collection> list = new ArrayList<>();

		 	for (CollectionBean collection : selectedCollections) {
		 	 list.add(collection.getModel());
		 	}
		 	
		 	
		 	
		 	if (bean.getEspecieTitulo() != null){
		 		CollectionChangeRequest req = new CollectionChangeRequest(CollectionChangeRequestProperty.Especie, bean.getEspecieTitulo().build(null));
		 		requests.add(req);
		 	}
		 	
		 	if (collectionAccountId > 0){
		 	 CollectionChangeRequest req = new CollectionChangeRequest(CollectionChangeRequestProperty.Account, collectionAccountId);
		 	 requests.add(req);
		 	}
		 	
		 	facade.alterProperty(list, requests);
		 	  
		 	init();

	}

	public void loadCollectionByDocNumberOrErpNumber() {
		Collection filter = editing.build(null);
		Collection coll = facade.loadCollectionByDocNumberOrErpNumber(filter);
		verifySentToBank(coll);

		CollectionBean loaded = (CollectionBean) editing.load(coll);
		setSelectedInterestRadio(loaded.getInterestCode());

		editing = loaded;
	}

	private void verifySentToBank(Collection coll) {
		if (coll.getAccount() != null && coll.getAccount().isRegistered() && isValidState(coll.getSituation())) {
			showMessage(FacesMessage.SEVERITY_INFO, "Título já Enviado para o Banco");
		}
	}

	private boolean isValidState(CollectionSituation situation) {
		if (CollectionSituation.REGISTERED.equals(situation) || CollectionSituation.QUITED.equals(situation)
				|| CollectionSituation.LIQUIDATED.equals(situation) || CollectionSituation.CANCELED.equals(situation))
			return true;

		return false;
	}

	@SuppressWarnings("rawtypes")
	public void doSearchPayeeByCNPJ(AjaxBehaviorEvent evt) {
		InputMask input = (InputMask) evt.getComponent();
		String value = (String) input.getValue();
		value = CNPJValidation.cleanCNPJ(value);
		if (value != null && !"".equals(value)) {
			editing = createBean(); // zera o bean para o caso de haver algum "lixo"
			List registers = searchPayeeByCNPJ(value);

			if (registers == null || (registers != null && registers.isEmpty())) {
				showMessage(FacesMessage.SEVERITY_INFO, "Empresa Não Encontrada");
			} else {
				Payee payee = (Payee) registers.get(0);

				CollectionBean bean = new CollectionBean();
				bean.setPayee(payee);

				bean.setCnpjSacado(payee.getRegister().getRegistrations().get("cnpj").getValue());

				LegalRegister legal = (LegalRegister) payee.getRegister();
				bean.setNomeSacado(legal.getCompanyName());

				editing = bean;
			}
		}
	}

	private List<LegalRegister> searchPayeeByCNPJ(String value) {
		List<LegalRegister> registers = companyRepository.namedQuery(Payee.Payee_findByRegistration, "type", "cnpj", "value",
				value);
		return registers;
	}
	
	public String changedMaturity(AjaxBehaviorEvent evt) {
		org.primefaces.component.calendar.Calendar calendar = (org.primefaces.component.calendar.Calendar) evt
				.getComponent();
		validateIssueDate(calendar);
		
		return "";
	}
	
	public String changedMaturity(DateSelectEvent evt) {
		org.primefaces.component.calendar.Calendar calendar = (org.primefaces.component.calendar.Calendar) evt
				.getComponent();
		validateIssueDate(calendar);
		
		return "";
	}

	/**
	 * @param calendar
	 */
	private void validateIssueDate(org.primefaces.component.calendar.Calendar calendar) {
		Calendar dtVcto = new GregorianCalendar();
		dtVcto.setTime((Date) calendar.getValue());
		if (dtVcto.before(DateUtils.truncateDate(Calendar.getInstance()))) {
			FacesContext.getCurrentInstance().addMessage(calendar.getClientId(),
					new FacesMessage(FacesMessage.SEVERITY_WARN, null, "Data de vencimento menor que data atual"));
		}
	}

	/**
	 * Get ProtestTypes from <b>ProtestTypeEnum</b> Enum.
	 * 
	 * @return SelectItems used do populate selectOneItem component
	 */
	public List<SelectItem> getProtestTypeValues() {
		List<SelectItem> status = new ArrayList<SelectItem>();

		for (ProtestTypeEnum s : ProtestTypeEnum.values()) {
			status.add(new SelectItem(s, s.getDescription()));
		}

		return status;
	}

	/**
	 * Get FineCodes from <b>FineCodeEnum</b> Enum.
	 * 
	 * @return SelectItems used do populate selectOneItem component
	 */
	public List<SelectItem> getFineTypeValues() {
		List<SelectItem> status = new ArrayList<SelectItem>();

		for (FineCodeEnum s : FineCodeEnum.values()) {
			status.add(new SelectItem(s, s.getDescription()));
		}

		return status;
	}
	
	public List<SelectItem> getInterestTypeValues() {
		List<SelectItem> status = new ArrayList<SelectItem>();

		for (InterestCodeEnum s : InterestCodeEnum.values()) {
			if (InterestCodeEnum.INTEREST_EXEMPT.getCode() != s.getCode())
				status.add(new SelectItem(s, s.getDescription()));
		}

		return status;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public StreamedContent getPdfFile() {
		printCollection();

		return pdfFile;
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

	public void calculateClean() {
		newDate = null;
		juros = 0;
		multa = 0;
		calculated = false;
	}

	public void confirmCalculate() {
		if (calculated) {
			Collection aux = selectedBean.getModel().clone();
			aux.setAmount(selectedBean.getAmount() + juros + multa);
			aux.setMaturity(newDate);
			facade.getRepository(Collection.class).load(aux).save();

			facade.applyStatus(Arrays.asList(selectedBean.getModel()), CollectionSituation.CANCELED);

			init();

			showMessage(FacesMessage.SEVERITY_INFO, "Recalculo efetuado com sucesso!");
		} else
			showMessage(FacesMessage.SEVERITY_ERROR, "Favor efetuar o recalculo antes de salvar.");
	}

	public void calculate() {
		if (newDate != null) {
			Calendar maturity = new GregorianCalendar();
			maturity.setTime(selectedBean.getMaturity());
			long dias = newDate.getTimeInMillis() - maturity.getTimeInMillis();
			dias = dias / 1000 / 60 / 60 / 24;
			double valor = selectedBean.getAmount();
			calculated = true;

			if (dias > 0) {
				/* Multa */
				if (selectedBean.getFineCode() != null)
					switch (selectedBean.getFineCode()) {
					case FINE_VALUE: // valor diário
						multa = selectedBean.getFineAmount() * dias;
						break;
					case FINE_PERCENTAGE: // taxa fixa
						multa = selectedBean.getFineAmount() * valor;
						break;
					case FINE_PRO_RATA: // taxa mensal. Segundo Sager, para
										// obter a
										// taxa diária, divide-se a taxa mensal
										// por
										// 30
						multa = selectedBean.getFineAmount() / 30 * valor * dias;
						break;
					}
				else {
					showMessage(FacesMessage.SEVERITY_ERROR, "Multa nao definida!");
					calculated = false;
				}

				/* Juros */
				if (selectedBean.getInterestCode() != null)
					switch (selectedBean.getInterestCode()) {
					case INTEREST_DAY: // Valor diário
						juros = selectedBean.getInterestAmount() * dias;
						break;
					case INTEREST_MONTH: // Taxa mensal
						juros = valor * selectedBean.getInterestAmount() / 30 * dias;
						break;
					case INTEREST_EXEMPT: // Isento
						juros = 0;
						break;
					}
				else {
					showMessage(FacesMessage.SEVERITY_ERROR, "Taxa de juros nao definida!");
					calculated = false;
				}
			} else {
				showMessage(FacesMessage.SEVERITY_ERROR,
						"Nova data de vencimento deve ser posterior a data de vencimento atual!");
				calculated = false;
			}
		}
	}

	
	public long getCollectionAccountId() {
		return collectionAccountId;
	}
	public void setCollectionAccountId(long collectionAccountId) {
		this.collectionAccountId = collectionAccountId;
	}
	
	public long getCollectionAccountId2() {
		return collectionAccountId2;
	}
	public void setCollectionAccountId2(long collectionAccountId2) {
		this.collectionAccountId2 = collectionAccountId2;
	}
	
	

	private void showMessage(Severity severity, String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, ""));
	}

	public Calendar getNewDate() {
		return newDate;
	}

	public void setNewDate(Calendar newDate) {
		this.newDate = newDate;
	}

	public double getMulta() {
		return multa;
	}

	public String getMultaFormat() {
		return NumberFormat.getCurrencyInstance().format(multa);
	}

	public void setMulta(double multa) {
		this.multa = multa;
	}

	public double getJuros() {
		return juros;
	}

	public String getJurosFormat() {
		return NumberFormat.getCurrencyInstance().format(juros);
	}

	public void setJuros(double juros) {
		this.juros = juros;
	}

	public boolean isCalculated() {
		return calculated;
	}

	public void setCalculated(boolean calculated) {
		this.calculated = calculated;
	}

	public boolean isSelectedInterestCode() {
		return selectedInterestCode;
	}

	public void setSelectedInterestCode(boolean selectedInterestMonth) {
		this.selectedInterestCode = selectedInterestMonth;
	}

	public boolean isSelectedDiscountType() {
		return selectedDiscountType;
	}

	public void setSelectedDiscountType(boolean selectedDiscountType) {
		this.selectedDiscountType = selectedDiscountType;
	}

}
