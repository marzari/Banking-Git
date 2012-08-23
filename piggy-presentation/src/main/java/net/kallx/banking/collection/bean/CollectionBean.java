package net.kallx.banking.collection.bean;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.kallx.architecture.control.GenericBean;
import net.kallx.architecture.repository.GenericFactory;
import net.kallx.banking.collection.model.Collection;
import net.kallx.banking.collection.model.CollectionHistory;
import net.kallx.banking.collection.model.ReceiptInstruction;
import net.kallx.banking.collection.model.Registry;
import net.kallx.banking.commons.model.Discount;
import net.kallx.banking.commons.model.Fine;
import net.kallx.banking.commons.model.Fine.FineCodeEnum;
import net.kallx.banking.commons.model.Interest;
import net.kallx.banking.commons.model.Interest.InterestCodeEnum;
import net.kallx.banking.commons.model.Payee;
import net.kallx.banking.commons.model.Payer;
import net.kallx.banking.commons.model.Protest;
import net.kallx.banking.commons.model.Protest.ProtestTypeEnum;
import net.kallx.modules.register.model.LegalRegister;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollectionBean implements GenericBean<Collection> {

	private static final Logger LOG = LoggerFactory.getLogger("net.kallx");

	private long id;

	private Date maturity;
	private Calendar issue;
	private Calendar docDate;
	private String formatdocDate;
	private String formatIssue;
	private String formatMaturity;
	private String nameBank;
	private String nomeSacado;
	private double amount;
	private String nomeCedente;
	private String cnpjSacado; // usado para sacado
	private EspecieTituloBean especieTitulo;
	private PortfolioBean portfolio;
	private DocumentTypeERPBean documentType;
	private String docNumber;

	// Não há bean para Interest, pois a classe é embeedable
	private InterestCodeEnum interestCode;
	private double interestAmount;

	// Não há bean para Protest, pois a classe é embeedable
	private ProtestTypeEnum protestType;
	private int dayForProtest;

	private FineCodeEnum fineCode;
	private double fineAmount;

	private DiscountTypeBean discountType;
	private double discountAmount;
	private double rebate;
	private String ourNumber;
	private long erpNumber;
	private Calendar accrual; // Referência
	private ReceiptInstructionBean receiptInstruction;
	private String details;

	private Collection model;
	private CollectionAccountBean account;
	private Map<String, RegistryBean> registries;
	private String situation;
	private Payee payee;
	private Payer payer;
	private boolean confirmed;

	private List<CollectionHistory> historic;

	public CollectionBean() {
		registries = new HashMap<>();
	}

	/**
	 * Variável que informa a data de geração da nota fiscal
	 */
	public Calendar getDocDate() {
		return docDate;
	}

	public void setDocDate(Calendar docDate) {
		this.docDate = docDate;
		if (this.docDate != null)
			this.setFormatdocDate(new SimpleDateFormat("dd/MM/yyyy").format(docDate.getTime()));
	}

	public String getFormatdocDate() {
		return formatdocDate;
	}

	public void setFormatdocDate(String formatdocDate) {
		this.formatdocDate = formatdocDate;

	}

	/**
	 * Variável que informa quando o titulo foi lançado
	 */
	public Calendar getIssue() {
		return issue;
	}

	public void setIssue(Calendar issue) {
		this.issue = issue;
		if (this.issue != null)
			this.setFormatIssue(new SimpleDateFormat("dd/MM/yyyy").format(issue.getTime()));
	}

	public String getFormatIssue() {
		return formatIssue;
	}

	public void setFormatIssue(String formatIssue) {
		this.formatIssue = formatIssue;
	}

	public String getNomeCedente() {
		return nomeCedente;
	}

	public void setNomeCedente(String nomeCedente) {
		this.nomeCedente = nomeCedente;
	}

	public String getFormatMaturity() {
		return formatMaturity;
	}

	public void setFormatMaturity(String formatMaturity) {
		this.formatMaturity = formatMaturity;
	}

	public String getNameBank() {
		return nameBank;
	}

	public void setNameBank(String nameBank) {
		this.nameBank = nameBank;
	}

	@Override
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getMaturity() {
		return maturity;
	}

	/**
	 * Váriavel que informa a data de vencimento do titulo
	 * 
	 * @param maturity
	 */
	public void setMaturity(Date maturity) {
		this.maturity = maturity;
		if (this.maturity != null)
			this.setFormatMaturity(new SimpleDateFormat("dd/MM/yyyy").format(maturity.getTime()));
	}

	/**
	 * Valor do titulo
	 * 
	 * @param valueSituation
	 */
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getNomeSacado() {
		return nomeSacado;
	}

	public void setNomeSacado(String nomeSacado) {
		this.nomeSacado = nomeSacado;
	}

	@Override
	public void setModel(Collection model) {
		this.model = model;
	}

	@Override
	public Collection getModel() {
		return model;
	}

	@Override
	public GenericBean<Collection> load(Collection model) {
		if (model == null)
			throw new IllegalStateException();

		this.model = model;
		this.id = model.getId();
		this.confirmed = model.isConfirmed();

		// this.account = new CollectionAccountBean().load(model.getAccount());
		this.nomeSacado = model.getPayeer() != null ? ((LegalRegister) model.getPayeer().getRegister())
				.getCompanyName() : "oo";

		for (Registry r : model.getRegistries().values()) {
			registries.put(r.getName(), new RegistryBean(r.getName(), r.getValue()));
		}
		this.amount = model.getAmount();
		setMaturity(model.getMaturity().getTime());
		setIssue(model.getIssue());
		setDocDate(model.getDocDate());
		this.situation = (model.getSituation() != null ? model.getSituation().name() : "");
		this.nomeCedente = model.getPayee() != null ? ((LegalRegister) model.getPayee().getRegister()).getCompanyName()
				: "o";
		setCnpjSacado(model.getPayeer() != null ? model.getPayeer().getRegister().getRegistrations().get("cnpj")
				.getValue() : "");
		if (model.getAccount() != null)
			this.account = new CollectionAccountBean().load(model.getAccount());

		setOurNumber(model.getOurNumber());
		setRebate(model.getRebate());
		setDocNumber(model.getDocNumber());

		if (model.getEspecieTitulo() != null)
			setEspecieTitulo(new EspecieTituloBean().load(model.getEspecieTitulo()));

		if (model.getPortfolio() != null)
			setPortfolio(new PortfolioBean().load(model.getPortfolio()));

		if (model.getDocumentType() != null)
			setDocumentType(new DocumentTypeERPBean().load(model.getDocumentType()));

		setErpNumber(model.getErpNumber());
		setDetails(model.getDetails());
		setAccrual(model.getAccrual());

		setDiscountData(model);
		setInterestData(model);
		setFineData(model);
		setPayee(model.getPayee());
		setPayer(model.getPayeer());

		Protest protest = model.getProtesto();
		if (protest != null) {
			setProtestType(protest.getProtestType());
			setDayForProtest(protest.getDaysForProtest());
		}
		
		ReceiptInstruction recpt = model.getReceiptInstruction();
		if (recpt != null) {
			setReceiptInstruction((ReceiptInstructionBean) new ReceiptInstructionBean().load(recpt));
		}
		
		setHistoric(new ArrayList<CollectionHistory>(model.getHistoric())); // necessário, pois o jsf não consegue ler um Set para uma dataTable

		return this;
	}

	/**
	 * @param model
	 */
	private void setDiscountData(Collection model) {
		Discount discount = model.getDiscount();
		if (discount != null) {
			DiscountTypeBean discBean = new DiscountTypeBean();

			setDiscountAmount(discount.getAmount());
			setDiscountType((DiscountTypeBean) discBean.load(discount.getDiscountType()));
		}
	}

	/**
	 * @param model
	 */
	private void setInterestData(Collection model) {
		Interest interest = model.getInterest();
		if (interest != null) {
			setInterestAmount(interest.getAmount());
			setInterestCode(interest.getInterestCode());
		}
	}

	/**
	 * @param model
	 */
	private void setFineData(Collection model) {
		Fine fine = model.getFine();
		if (fine != null) {
			setFineAmount(fine.getAmount());
			setFineCode(fine.getFineCode());
		}
	}

	@Override
	public Collection build(GenericFactory<Collection> factory) {
		if (model == null)
			model = new Collection();
		
		if (model.getRegistries().get("especie") != null && registries.get("especie") != null) {
			model.getRegistries().get("especie").setValue(registries.get("especie").getValue());
		}
		
		model.setId(getId());
		if (getMaturity() != null) {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			gregorianCalendar.setTime(getMaturity());
			model.setMaturity(gregorianCalendar);
		}
		model.setOurNumber(getOurNumber());
		model.setRebate(getRebate());
		model.setDocNumber(getDocNumber());
		model.setErpNumber(getErpNumber());
		model.setAmount(getAmount());
		model.setAccrual(getAccrual());
		model.setDetails(getDetails());
		model.setIssue(getIssue());
		
		
		model.setPayeer(getPayer());

		if (getEspecieTitulo() != null)
			model.setEspecieTitulo(getEspecieTitulo().build(null));

		if (getPortfolio() != null)
			model.setPortfolio(getPortfolio().build(null));

		if (getDocumentType() != null)
			model.setDocumentType(getDocumentType().build(null));

		if (getDiscountType() != null) {
			Discount discount = new Discount();
			discount.setDiscountType(getDiscountType().build(null));
			discount.setAmount(getDiscountAmount());
			model.setDiscount(discount);
		}

		if (getInterestCode() != null) {
			Interest interest = new Interest();
			interest.setCode(getInterestCode().getCode());
			interest.setAmount(getInterestAmount());
			model.setInterest(interest);
		}

		if (getProtestType() != null) {
			Protest protest = new Protest();
			protest.setProtestCode(getProtestType().getCode());
			protest.setDateEmission(null);
			protest.setDaysForProtest(getDayForProtest());
			model.setProtesto(protest);
		}

		if (getFineCode() != null) {
			Fine fine = new Fine();
			fine.setAmount(getFineAmount());
			fine.setCode(getFineCode().getCode());
			model.setFine(fine);
		}
		
		if (getAccount() != null)
			model.setAccount(account.getModel());

		if (getReceiptInstruction() != null) {
			model.setReceiptInstruction(getReceiptInstruction().build(null));
		}

		model.setPayee(getPayee());
		return model;
	}

	public CollectionAccountBean getAccount() {
		return account;
	}

	public void setAccount(CollectionAccountBean account) {
		this.account = account;
	}

	public Map<String, RegistryBean> getRegistries() {
		return registries;
	}

	public void setRegistries(Map<String, RegistryBean> registries) {
		this.registries = registries;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public EspecieTituloBean getEspecieTitulo() {
		return especieTitulo;
	}

	public void setEspecieTitulo(EspecieTituloBean especieTitulo) {
		this.especieTitulo = especieTitulo;
	}

	public PortfolioBean getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(PortfolioBean portfolio) {
		this.portfolio = portfolio;
	}

	public DocumentTypeERPBean getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentTypeERPBean documentType) {
		this.documentType = documentType;
	}

	public double getRebate() {
		return rebate;
	}

	public void setRebate(double rebate) {
		this.rebate = rebate;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public DiscountTypeBean getDiscountType() {
		return discountType;
	}

	public void setDiscountType(DiscountTypeBean discountType) {
		this.discountType = discountType;
	}

	public String getDocNumber() {
		return docNumber;
	}

	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}

	public String getOurNumber() {
		return ourNumber;
	}

	public void setOurNumber(String ourNumber) {
		this.ourNumber = ourNumber;
	}

	public long getErpNumber() {
		return erpNumber;
	}

	public void setErpNumber(long erpNumber) {
		this.erpNumber = erpNumber;
	}

	public InterestCodeEnum getInterestCode() {
		return interestCode;
	}

	public void setInterestCode(InterestCodeEnum interestCode) {
		this.interestCode = interestCode;
	}

	public double getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(double interestAmount) {
		this.interestAmount = interestAmount;
	}

	public ProtestTypeEnum getProtestType() {
		return protestType;
	}

	public void setProtestType(ProtestTypeEnum protestType) {
		this.protestType = protestType;
	}

	public FineCodeEnum getFineCode() {
		return fineCode;
	}

	public void setFineCode(FineCodeEnum fineCode) {
		this.fineCode = fineCode;
	}

	public double getFineAmount() {
		return fineAmount;
	}

	public void setFineAmount(double fineAmount) {
		this.fineAmount = fineAmount;
	}

	public Calendar getAccrual() {
		return accrual;
	}

	public void setAccrual(Calendar accrual) {
		this.accrual = accrual;
	}

	public ReceiptInstructionBean getReceiptInstruction() {
		return receiptInstruction;
	}

	public void setReceiptInstruction(ReceiptInstructionBean receiptInstruction) {
		this.receiptInstruction = receiptInstruction;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getCnpjSacado() {
		return cnpjSacado;
	}

	public void setCnpjSacado(String cnpjCedente) {
		this.cnpjSacado = cnpjCedente;
	}

	public List<CollectionHistory> getHistoric() {
		return historic == null ? new ArrayList<CollectionHistory>() : this.historic;
	}

	public void setHistoric(List<CollectionHistory> historic) {
		this.historic = historic;
	}

	public Payee getPayee() {
		return payee;
	}

	public void setPayee(Payee payee) {
		this.payee = payee;
	}

	@Override
	public String toString() {
		if (LOG.isTraceEnabled()) {
			String toString = ToStringBuilder.reflectionToString(this);
			LOG.trace("Showing " + this.getClass().getName() + " object attributes: ", toString);
			return toString;
		}
		return super.toString();
	}
	
	public String getAmountFormat() {
		return NumberFormat.getCurrencyInstance().format(amount);
	}
	
	public boolean isConfirmed() {
		return confirmed;
	}
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Payer getPayer() {
		return payer;
	}

	public void setPayer(Payer payer) {
		this.payer = payer;
	}

	public int getDayForProtest() {
		return dayForProtest;
	}

	public void setDayForProtest(int dayForProtest) {
		this.dayForProtest = dayForProtest;
	}
}
