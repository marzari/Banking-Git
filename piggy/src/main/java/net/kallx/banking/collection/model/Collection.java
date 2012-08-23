package net.kallx.banking.collection.model;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import net.kallx.architecture.model.GenericModel;
import net.kallx.banking.commons.model.Discount;
import net.kallx.banking.commons.model.DocumentTypeERP;
import net.kallx.banking.commons.model.Fine;
import net.kallx.banking.commons.model.Interest;
import net.kallx.banking.commons.model.Payee;
import net.kallx.banking.commons.model.Payer;
import net.kallx.banking.commons.model.Protest;
import net.kallx.security.model.User;

/**
 * @author Tiago Felipe
 * 
 */

@Entity
@Table(name = "Collection", schema = "banking", uniqueConstraints = { @UniqueConstraint( columnNames = { "cDocNumber",
		"cOurNumber" }, name = "UK_collection") })
@NamedQueries({
		@NamedQuery(name = "Colletion_findByIssue", query = "select x from Collection x where x.issue >=:issueInit and x.issue <=:issueFinal"),
		@NamedQuery(name = "Collection_findByMaturity", query = "select x from Collection x where x.maturity >=:maturityInit and x.maturity <=:maturityFinal"),
		@NamedQuery(name = "Collection_findByIssueAndMaturity", query = "select x from Collection x where x.maturity >=:maturityInit and x.maturity <=:maturityFinal and x.issue >=:issueInit and x.issue <=:issueFinal"),
		@NamedQuery(name = "Collection_findByIssueAndEspecie", query = "select x from Collection x  where x.issue >=:issueInit and x.issue <=:issueFinal and :especie in elements(x.registries)"),
		@NamedQuery(name = "Collection_findByMaturityAndEspecie", query = "select x from Collection x  where x.maturity >=:maturityInit and x.maturity <=:maturityFinal and :especie in elements(x.registries)"),
		@NamedQuery(name = "Colletion_findByEspecie", query = "select x from Collection x where x.registries['especie'] =:especie "),
		@NamedQuery(name = "Collection_All", query = "select x from Collection x"),
		@NamedQuery(name = "Collection_findByIssueMaturityAndEspecie", query = "select x from Collection x where x.maturity >=:maturityInit and x.maturity <=:maturityFinal "
				+ "and x.issue >=:issueInit and x.issue <=:issueFinal and  :especie in elements(x.registries)"),
		@NamedQuery(name = "Collection_findBy_DocNumber_OR_OurNumber", query = "select x from Collection x where x.docNumber = :docNumber or x.ourNumber = :ourNumber"),
		@NamedQuery(name = "Collection_findBy_DocNumber", query = "select x from Collection x where x.docNumber = :docNumber"),
		@NamedQuery(name = "Collection_findBy_OurNumber", query = "select x from Collection x where x.ourNumber = :ourNumber")})
		
public class Collection implements GenericModel {

	public static String filterAll = "Collection_All";
	public static String filterByIssue = "Colletion_findByIssue";
	public static String filterByEspecie = "Colletion_findByEspecie";
	public static String filterByMaturity = "Collection_findByMaturity";
	public static String filterByIssueAndEspecie = "Collection_findByIssueAndEspecie";
	public static String filterByMaturityAndIssue = "Collection_findByIssueAndMaturity";
	public static String filterByMaturityAndEspecie = "Collection_findByMaturityAndEspecie";
	public static String filterByIssueMaturityAndEspecie = "Collection_findByIssueMaturityAndEspecie";
	public static String filterByDocNumberOrOurNumber = "Collection_findBy_DocNumber_OR_OurNumber";
	public static String filterByDocNumber = "Collection_findBy_DocNumber";
	public static String filterByOurNumber = "Collection_findBy_OurNumber";

	private Payee payee;
	private Payer payeer;
	private Interest interest;
	private Fine fine;
	private Discount discount;
	private Protest protest;
	private Quit quit; //baixa
	private CollectionAccount account;

	private Map<String, Registry> registries;

	private double amount;
	private long id;
	private Calendar maturity;
	private Calendar issue;
	private Calendar docDate;
	private Calendar dateCredit;
	private CollectionSituation situation;
	private boolean confirmed;
	private String docNumber; //seu numero
	private double rebate;
	private long erpNumber;
	private EspecieTitulo especieTitulo;
	private Portfolio portfolio;
	private DocumentTypeERP documentType;
	private String ourNumber;
	private Calendar accrual; // Referência
	private ReceiptInstruction receiptInstruction;
	private String details;

	private Set<CollectionHistory> historic;

	public Collection() {
		registries = new HashMap<>();
		historic = new HashSet<>();
	}

	@ManyToOne
	@JoinColumn(name = "cPayee")
	public Payee getPayee() {
		return payee;
	}

	public void setPayee(Payee payee) {
		this.payee = payee;
	}

	@Column(name = "cAmount")
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@ManyToOne
	@JoinColumn(name = "cPayeer")
	public Payer getPayeer() {
		return payeer;
	}

	public void setPayeer(Payer payeer) {
		this.payeer = payeer;
	}

	@Embedded
	public Interest getInterest() {
		return interest;
	}

	public void setInterest(Interest interest) {
		this.interest = interest;
	}

	@Embedded
	public Fine getFine() {
		return fine;
	}

	public void setFine(Fine fine) {
		this.fine = fine;
	}

	@Embedded
	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

	@Embedded
	public Protest getProtesto() {
		return protest;
	}

	public void setProtesto(Protest protesto) {
		this.protest = protesto;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cQuit")
	public Quit getQuit() {
		return quit;
	}

	public void setQuit(Quit quit) {
		this.quit = quit;
	}

	@ManyToOne
	@JoinColumn(name = "cAccount")
	public CollectionAccount getAccount() {
		return account;
	}

	public void setAccount(CollectionAccount account) {
		this.account = account;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "cMaturity")
	public Calendar getMaturity() {
		return maturity;
	}

	public void setMaturity(Calendar maturity) {
		this.maturity = maturity;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "cCreditDate")
	public Calendar getDateCredit() {
		return dateCredit;
	}

	public void setDateCredit(Calendar dateCredit) {
		this.dateCredit = dateCredit;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "cIssue")
	public Calendar getIssue() {
		return issue;
	}

	public void setIssue(Calendar issue) {
		this.issue = issue;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "cDocDate")
	public Calendar getDocDate() {
		return docDate;
	}

	public void setDocDate(Calendar docDate) {
		this.docDate = docDate;
	}

	@Column(name = "cSituation")
	@Enumerated(EnumType.STRING)
	public CollectionSituation getSituation() {
		return situation;
	}

	public void setSituation(CollectionSituation situation) {
		this.situation = situation;
	}

	@Id
	@Column(name = "cId")
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "Collection_Registry", schema = "banking", joinColumns = @JoinColumn(name = "cCollection"))
	@MapKeyColumn(name = "cType")
	@Column(name = "cRegistry")
	public Map<String, Registry> getRegistries() {
		return registries;
	}

	public void setRegistries(Map<String, Registry> registries) {
		this.registries = registries;
	}

	public String getRegistry(String name) {
		if (registries.containsKey(name))
			return registries.get(name).getValue();
		return "";
	}

	public void addRegistration(String name, String code) {
		Registry r = new Registry(name, code);
		registries.put(r.getName(), r);
	}

	public void addRegistration(Registry registry) {
		registries.put(registry.getName(), registry);
	}

	@Column(name = "cDocNumber")
	public String getDocNumber() {
		return docNumber;
	}

	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}

	@ManyToOne
	@JoinColumn(name = "cEspecieTitulo")
	public EspecieTitulo getEspecieTitulo() {
		return especieTitulo;
	}

	public void setEspecieTitulo(EspecieTitulo especieTitulo) {
		this.especieTitulo = especieTitulo;
	}

	@ManyToOne
	@JoinColumn(name = "cPorfolio")
	public Portfolio getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	@ManyToOne
	@JoinColumn(name = "cDoctyperp")
	public DocumentTypeERP getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentTypeERP documentType) {
		this.documentType = documentType;
	}

	@Column(name = "cRebate")
	public double getRebate() {
		return rebate;
	}

	public void setRebate(double rebate) {
		this.rebate = rebate;
	}

	@Column(name = "cErpNumber")
	public long getErpNumber() {
		return erpNumber;
	}

	public void setErpNumber(long erpNumber) {
		this.erpNumber = erpNumber;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Collection))
			return false;
		Collection that = (Collection) obj;
		if (this.id > 0)
			return (this.id == that.id);
		else if (this.docNumber == null ? that.docNumber == null
				: this.docNumber.equals(that.docNumber))
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		int result = 13;
		if (id > 0)
			result = result * 31 + (int) (id ^ (id >>> 32));
		else
			result = result * 31
					+ (docNumber != null ? docNumber.hashCode() : 0);
		return result;
	}

	@Column(name = "cOurNumber")
	public String getOurNumber() {
		return ourNumber;
	}

	public void setOurNumber(String ourNumber) {
		this.ourNumber = ourNumber;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cAccrual")
	public Calendar getAccrual() {
		return accrual;
	}

	public void setAccrual(Calendar accrual) {
		this.accrual = accrual;
	}

	@ManyToOne
	@JoinColumn(name = "cReceiptInstruction")
	public ReceiptInstruction getReceiptInstruction() {
		return receiptInstruction;
	}

	public void setReceiptInstruction(ReceiptInstruction receiptInstruction) {
		this.receiptInstruction = receiptInstruction;
	}

	@Column(name = "cDetails")
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "cCollection")
	public Set<CollectionHistory> getHistoric() {
		return historic;
	}

	public void setHistoric(Set<CollectionHistory> historic) {
		this.historic = historic;
	}

	// Verifica se a confirmação do banco veio
	@Column(name = "cConfirmed")
	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public void addHistoryEntry(User user, Calendar when,
			CollectionSituation situation) {

		CollectionHistory h = new CollectionHistory();
		h.setCollection(this);
		h.setSituation(situation);
		h.setUser(user);
		h.setWhen(when);

		historic.add(h);
	}

	public Collection clone() {
		Collection col = new Collection();

		col.setPayee(payee);
		col.setPayeer(payeer);
		col.setInterest(interest);
		col.setFine(fine);
		col.setDiscount(discount);
		col.setProtesto(protest);
		col.setQuit(quit);
		col.setAccount(account);
		col.setSituation(situation);
		col.setEspecieTitulo(especieTitulo);
		col.setPortfolio(portfolio);
		col.setDocumentType(documentType);
		col.setReceiptInstruction(receiptInstruction);
		col.setRegistries(registries);
		col.setMaturity(maturity);
		col.setIssue(issue);
		col.setDocDate(docDate);
		col.setDateCredit(dateCredit);
		col.setAccrual(accrual);

		col.setAmount(amount);
		col.setDocNumber(docNumber);
		col.setRebate(rebate);
		col.setErpNumber(erpNumber);
		col.setOurNumber(ourNumber);
		col.setDetails(details);

		return col;
	}

}
