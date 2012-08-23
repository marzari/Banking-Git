package net.kallx.collection.delivery;

import java.util.Calendar;

import net.kallx.kangaroo.delivery.annotations.RField;
import net.kallx.kangaroo.delivery.annotations.RSegment;

/**
 * @author Tiago Felipe
 * @param <EspecieTitulo>
 * 
 */

public class CollectionAdapterItem<EspecieTitulo> {

	// Declaration of variables following order MQL, segment and their
	// respective fields.

	@RSegment("titulo")
	private String bankCollection;

	@RField("nosso_numero")
	private String docNumberBank;

	@RField("codigo_carteira")
	private String codeCollectionPortfolio;

	@RField("forma_cadastro_titulo")
	private String kindRegistrationBank;

	@RField("tipo_documento")
	private String kindDocument;

	@RField("emissao_boleto")
	private String identificationIssueCollection;

	@RField("distribuicao_boleto")
	private String identificationDistributionCollection;

	@RField("numero_documento")
	private String numberDocumentCollection;

	@RField("data_vencimento")
	private Calendar maturity;

	@RField("valor_titulo")
	private double amount;

	@RField("agencia_cobradora")
	private String collectorAgencyCode;

	@RField("digito_agencia_cobradora")
	private String DVCollectorAgencyCode;

	@RField("especie")
	private EspecieTitulo collectionType;

	@RField("aceite")
	private String accept;

	@RField("emissao")
	private Calendar dateIssue;

	@RField("codigo_juros")
	private String codeInterest;

	@RField("data_juros")
	private Calendar dateInterest;

	@RField("valor_juros")
	private double dailyValueOfInterest;

	@RField("codigo_desconto")
	private String discountCode;

	@RField("data_desconto")
	private Calendar offDateFor;

	@RField("valor_desconto")
	private double discountPercentage;

	@RField("valor_iof")
	private String IOFValue;

	@RField("valor_abatimento")
	private String offValue;

	@RField("seu_numero")
	private String docNumberCompany;

	@RField("codigo_protesto")
	private String codeProtest;

	@RField("prazo_protesto")
	private String deadlineProtest;

	@RField("codigo_baixa")
	private String cancelCode;

	@RField("prazo_baixa")
	private int deadlineCancel;

	@RField("codigo_moeda")
	private String codeCurrency;

	@RField("numero_contrato")
	private String contractNumberOfCreditOperation;

	@RField("tipo_inscricao_sacado")
	private String kindSubscription;

	@RField("numero_inscricao_sacado")
	private String registrationNumberCostumer;

	@RField("nome_sacado")
	private String nameOfDrawee;

	@RField("endereco_sacado")
	private String address;

	@RField("bairro_sacado")
	private String neighborhood;

	@RField("cep_sacado")
	private String zipCode;

	@RField("sufixo_cep_sacado")
	private String zipCodeSuffix;

	@RField("cidade_sacado")
	private String city;

	@RField("uf_sacado")
	private String UF;

	@RField("tipo_inscricao_sacador")
	private String kindRegistration;

	@RField("numero_inscricao_sacador")
	private String codeRegistration;

	@RField("nome_sacador")
	private String nameOfGuarantor;

	public String getRegistrationNumberCostumer() {
		return registrationNumberCostumer;
	}

	public void setRegistrationNumberCostumer(String registrationNumberCostumer) {
		this.registrationNumberCostumer = registrationNumberCostumer;
	}

	public String getBankCollection() {
		return bankCollection;
	}

	public void setBankCollection(String bankCollection) {
		this.bankCollection = bankCollection;
	}

	public String getDocNumberBank() {
		return docNumberBank;
	}

	public void setDocNumberBank(String docNumberBank) {
		this.docNumberBank = docNumberBank;
	}

	public String getCodeCollectionPortfolio() {
		return codeCollectionPortfolio;
	}

	public void setCodeCollectionPortfolio(String codeCollectionPortfolio) {
		this.codeCollectionPortfolio = codeCollectionPortfolio;
	}

	public String getKindRegistrationBank() {
		return kindRegistrationBank;
	}

	public void setKindRegistrationBank(String kindRegistrationBank) {
		this.kindRegistrationBank = kindRegistrationBank;
	}

	public String getKindDocument() {
		return kindDocument;
	}

	public void setKindDocument(String kindDocument) {
		this.kindDocument = kindDocument;
	}

	public String getIdentificationIssueCollection() {
		return identificationIssueCollection;
	}

	public void setIdentificationIssueCollection(
			String identificationIssueCollection) {
		this.identificationIssueCollection = identificationIssueCollection;
	}

	public String getIdentificationDistributionCollection() {
		return identificationDistributionCollection;
	}

	public void setIdentificationDistributionCollection(
			String identificationDistributionCollection) {
		this.identificationDistributionCollection = identificationDistributionCollection;
	}

	public String getNumberDocumentCollection() {
		return numberDocumentCollection;
	}

	public void setNumberDocumentCollection(String numberDocumentCollection) {
		this.numberDocumentCollection = numberDocumentCollection;
	}

	public Calendar getMaturity() {
		return maturity;
	}

	public void setMaturity(Calendar maturity) {
		this.maturity = maturity;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCollectorAgencyCode() {
		return collectorAgencyCode;
	}

	public void setCollectorAgencyCode(String collectorAgencyCode) {
		this.collectorAgencyCode = collectorAgencyCode;
	}

	public String getDVCollectorAgencyCode() {
		return DVCollectorAgencyCode;
	}

	public void setDVCollectorAgencyCode(String dVCollectorAgencyCode) {
		DVCollectorAgencyCode = dVCollectorAgencyCode;
	}

	public EspecieTitulo getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(EspecieTitulo collectionType) {
		this.collectionType = collectionType;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public Calendar getDateIssue() {
		return dateIssue;
	}

	public void setDateIssue(Calendar dateIssue) {
		this.dateIssue = dateIssue;
	}

	public String getCodeInterest() {
		return codeInterest;
	}

	public void setCodeInterest(String codeInterest) {
		this.codeInterest = codeInterest;
	}

	public Calendar getDateInterest() {
		return dateInterest;
	}

	public void setDateInterest(Calendar dateInterest) {
		this.dateInterest = dateInterest;
	}

	public double getDailyValueOfInterest() {
		return dailyValueOfInterest;
	}

	public void setDailyValueOfInterest(double dailyValueOfInterest) {
		this.dailyValueOfInterest = dailyValueOfInterest;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public Calendar getOffDateFor() {
		return offDateFor;
	}

	public void setOffDateFor(Calendar offDateFor) {
		this.offDateFor = offDateFor;
	}

	public double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public String getIOFValue() {
		return IOFValue;
	}

	public void setIOFValue(String iOFValue) {
		IOFValue = iOFValue;
	}

	public String getOffValue() {
		return offValue;
	}

	public void setOffValue(String offValue) {
		this.offValue = offValue;
	}

	public String getDocNumberCompany() {
		return docNumberCompany;
	}

	public void setDocNumberCompany(String docNumberCompany) {
		this.docNumberCompany = docNumberCompany;
	}

	public String getCodeProtest() {
		return codeProtest;
	}

	public void setCodeProtest(String codeProtest) {
		this.codeProtest = codeProtest;
	}

	public String getDeadlineProtest() {
		return deadlineProtest;
	}

	public void setDeadlineProtest(String deadlineProtest) {
		this.deadlineProtest = deadlineProtest;
	}

	public String getCancelCode() {
		return cancelCode;
	}

	public void setCancelCode(String cancelCode) {
		this.cancelCode = cancelCode;
	}

	public int getDeadlineCancel() {
		return deadlineCancel;
	}

	public void setDeadlineCancel(int deadlineCancel) {
		this.deadlineCancel = deadlineCancel;
	}

	public String getCodeCurrency() {
		return codeCurrency;
	}

	public void setCodeCurrency(String codeCurrency) {
		this.codeCurrency = codeCurrency;
	}

	public String getContractNumberOfCreditOperation() {
		return contractNumberOfCreditOperation;
	}

	public void setContractNumberOfCreditOperation(
			String contractNumberOfCreditOperation) {
		this.contractNumberOfCreditOperation = contractNumberOfCreditOperation;
	}

	public String getKindSubscription() {
		return kindSubscription;
	}

	public void setKindSubscription(String kindSubscription) {
		this.kindSubscription = kindSubscription;
	}

	public String getNumberSubscription() {
		return registrationNumberCostumer;
	}

	public void setNumberSubscription(String numberSubscription) {
		this.registrationNumberCostumer = numberSubscription;
	}

	public String getNameOfDrawee() {
		return nameOfDrawee;
	}

	public void setNameOfDrawee(String nameOfDrawee) {
		this.nameOfDrawee = nameOfDrawee;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getZipCodeSuffix() {
		return zipCodeSuffix;
	}

	public void setZipCodeSuffix(String zipCodeSuffix) {
		this.zipCodeSuffix = zipCodeSuffix;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUF() {
		return UF;
	}

	public void setUF(String uF) {
		UF = uF;
	}

	public String getKindRegistration() {
		return kindRegistration;
	}

	public void setKindRegistration(String kindRegistration) {
		this.kindRegistration = kindRegistration;
	}

	public String getCodeRegistration() {
		return codeRegistration;
	}

	public void setCodeRegistration(String codeRegistration) {
		this.codeRegistration = codeRegistration;
	}

	public String getNameOfGuarantor() {
		return nameOfGuarantor;
	}

	public void setNameOfGuarantor(String nameOfGuarantor) {
		this.nameOfGuarantor = nameOfGuarantor;
	}

}
