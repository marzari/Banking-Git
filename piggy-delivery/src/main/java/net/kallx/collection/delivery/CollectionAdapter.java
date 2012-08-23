package net.kallx.collection.delivery;

import java.util.Calendar;
import java.util.GregorianCalendar;

import net.kallx.kangaroo.delivery.annotations.RField;
import net.kallx.kangaroo.delivery.annotations.RSegment;

/**
 * @author Tiago Felipe
 * 
 */
public class CollectionAdapter {

	// Declaration of variables following order MQL, segment and their
	// respective fields.

	@RField("banco_cedente")
	private String codeBank;

	@RField("tipo_inscricao_cedente")
	private String registrationType;

	@RField("nome_cedente")
	private String companyName;

	@RSegment("banco_cedente")
	private long control;

	@RField("convenio")
	private String agreementCodeInTheBank;

	@RField("numero_inscricao_cedente")
	private String registration;

	@RField("agencia_cedente")
	private String bankUnit;

	@RField("digito_agencia_cedente")
	private String bankUnitDigit;

	@RField("conta_cedente")
	private String account;

	@RField("digito_conta_cedente")
	private String accountDigit;

	@RField("digito_agencia_conta_cedente")
	private String accountAndBankUnitDigit;

	private Calendar generationDate;
	private int registryNumber;
	
	
	public CollectionAdapter() {
		generationDate = new GregorianCalendar();
	}
	
	
	public String getCodeBank() {
		return codeBank;
	}

	public void setCodeBank(String codeBank) {
		this.codeBank = codeBank;
	}

	public String getRegistrationType() {
		return registrationType;
	}

	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public String getAgreementCodeInTheBank() {
		return agreementCodeInTheBank;
	}

	public void setAgreementCodeInTheBank(String agreementCodeInTheBank) {
		this.agreementCodeInTheBank = agreementCodeInTheBank;
	}

	public String getBankUnit() {
		return bankUnit;
	}

	public void setBankUnit(String bankUnit) {
		this.bankUnit = bankUnit;
	}

	public String getBankUnitDigit() {
		return bankUnitDigit;
	}

	public void setBankUnitDigit(String bankUnitDigit) {
		this.bankUnitDigit = bankUnitDigit;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountDigit() {
		return accountDigit;
	}

	public void setAccountDigit(String accountDigit) {
		this.accountDigit = accountDigit;
	}

	public String getAccountAndBankUnitDigit() {
		return accountAndBankUnitDigit;
	}

	public void setAccountAndBankUnitDigit(String accountAndBankUnitDigit) {
		this.accountAndBankUnitDigit = accountAndBankUnitDigit;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public long getControl() {
		return control;
	}

	public void setControl(long control) {
		this.control = control;
	}
	
	
	public int getRegistryNumber() {
		return registryNumber;
	}
	public void setRegistryNumber(int registryNumber) {
		this.registryNumber = registryNumber;
	}
	

	public Calendar getGenerationDate() {
		return generationDate;
	}
	public void setGenerationDate(Calendar generationDate) {
		this.generationDate = generationDate;
	}

}
