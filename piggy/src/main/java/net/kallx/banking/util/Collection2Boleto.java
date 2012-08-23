package net.kallx.banking.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import net.kallx.banking.account.model.BankAccount;
import net.kallx.banking.collection.model.Collection;
import net.kallx.banking.collection.model.CollectionAccount;
import net.kallx.modules.register.model.Address;
import net.kallx.modules.register.model.LegalRegister;

public class Collection2Boleto {
	private Collection titulo = null;
	private LegalRegister sacado = null; 
	private LegalRegister cedente = null; 
	private Address sacadoEndereco = null;
	private BankAccount conta = null;
	private CollectionAccount contaCol = null;
	private String bank = "";
	
	private static final String LOCAL_PAGAMENTO = "ATÉ O VENCIMENTO, PREFERENCIALMENTE NO #banco.";
	private static final String INSTRUCOES = "APÓS O VENCIMENTO, SOMENTE NO #banco";
	public static final String BANCO_DO_BRASIL = "BANCO DO BRASIL";
	public static final String ITAU = "BANCO ITAÚ";
	public static final String BRADESCO = "BRADESCO";
	public static final String SANTANDER = "BANCO SANTANDER";
	public static final String REAL = "BANCO REAL";
	public static final String CEF = "CAIXA ECONÔMICA FEDERAL";
	public static final String HSBC = "HSBC";
	public static final String NOSSA_CAIXA = "NOSSA CAIXA";
	
	public Collection2Boleto(Collection titulo, String banco) {
		this.titulo = titulo;
		this.bank = banco;
		
		if (titulo != null) {
			if (titulo.getPayeer() != null && titulo.getPayeer().getRegister() != null) {
				sacado = (LegalRegister) titulo.getPayeer().getRegister();
				
				Iterator<Address> it = sacado.getAddresses().iterator();
			    
			    if (it.hasNext())
			      sacadoEndereco = it.next();
			}
			
			if (titulo.getPayee() != null && titulo.getPayee().getRegister() != null) {
				cedente = (LegalRegister) titulo.getPayee().getRegister();
			}
			
			if (titulo.getAccount() != null) {
				contaCol = titulo.getAccount();
			}
			
			if (contaCol != null && contaCol.getBankAccount() != null) {
				conta = titulo.getAccount().getBankAccount();
			}
		}
	}
	
	public Date getDataDocumento() {		
		if (titulo != null && titulo.getDocDate() != null)
			return titulo.getDocDate().getTime();
		else
			return null;
	}
	
	public String getDataDocumentoStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		if (titulo != null && titulo.getDocDate() != null)
			return sdf.format(titulo.getDocDate().getTime());
		else
			return "";
	}
	
	public String getDataProcessamentoStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		if (titulo != null && titulo.getIssue() != null)
			return sdf.format(titulo.getIssue().getTime());
		else
			return "";
	}
	
	public String getDataVencimentoStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		if (titulo != null && titulo.getMaturity() != null)
			return sdf.format(titulo.getMaturity().getTime());
		else
			return "";
	}
	
	public Date getDataVencimento() {		
		if (titulo != null && titulo.getMaturity() != null)
			return titulo.getMaturity().getTime();
		else
			return null;
	}
	
	public String getCedenteNome() {
		if (cedente != null && cedente.getCompanyName() != null)
			return cedente.getCompanyName();
		else
			return "";
	}
	
	public String getSacadoNome() {
		if (sacado != null && sacado.getCompanyName() != null)
			return sacado.getCompanyName();
		else
			return "";
	}
	
	public String getSacadoEndereco() {
		if (sacadoEndereco != null && sacadoEndereco.getStreet() != null)
			return sacadoEndereco.getStreet();
		else
			return "";
	}
	
	public String getSacadoBairro() {
		if (sacadoEndereco != null && sacadoEndereco.getNeighborhood() != null)
			return sacadoEndereco.getNeighborhood();
		else
			return "";
	}
	
	public String getSacadoCidade() {
		if (sacadoEndereco != null && sacadoEndereco.getCity() != null && sacadoEndereco.getCity().getName() != null)
			return sacadoEndereco.getCity().getName();
		else
			return "";
	}
	
	public String getSacadoUf() {
		if (sacadoEndereco != null && sacadoEndereco.getCity().getState() != null && sacadoEndereco.getCity().getState().getAbbreviation() != null)
			return sacadoEndereco.getCity().getState().getAbbreviation();
		else
			return "";
	}
	
	public String getSacadoCep() {
		if (sacadoEndereco != null && sacadoEndereco.getZipCode() != null)
			return sacadoEndereco.getZipCode();
		else
			return "";
	}
	
	public double getValorBoleto() {
		return titulo.getAmount();
	}
	
	public String getValorBoletoStr() {
		DecimalFormat fmt = new DecimalFormat("#,##0.00");
		
		if (titulo != null)
			return fmt.format(titulo.getAmount());
		else
			return "";
	}
	
	public String getContaCorrente() {
		if (conta != null && conta.getNumber() != null) 
			return conta.getNumber();
		else
			return "";
	}
	
	public int getContaCorrenteInt() {
		if (conta != null && conta.getNumber() != null) 
			return Integer.parseInt(conta.getNumber());
		else
			return 0;
	}
	
	public String getContaCorrenteDV() {
		if (conta != null && conta.getDigit() != null) 
			return conta.getDigit();
		else
			return "";
	}
	
	public String getAgencia() {
		if (conta != null && conta.getBankUnit() != null)
			return conta.getBankUnit().getNumber();
		else
			return "";
	}
	
	public int getAgenciaInt() {
		if (conta != null && conta.getBankUnit() != null)
			return Integer.parseInt(conta.getBankUnit().getNumber());
		else
			return 0;
	}
	
	public String getAgenciaDV() {
		if (conta != null && conta.getBankUnit().getDigit() != null)
			return conta.getBankUnit().getDigit();
		else
			return "";
	}

	public String getNossoNumero() {
		if (titulo != null && titulo.getOurNumber() != null)
			return titulo.getOurNumber();
		else
			return "";
	}

	public String getAceite() {
		if (titulo != null && titulo.getRegistries() != null && titulo.getRegistries().get("aceite") != null)
			return titulo.getRegistries().get("aceite").getValue();
		else
			return "";
	}

	public String getEspecieDoc() {
		String result = "";
		
		if (titulo != null && titulo.getRegistries() != null && titulo.getRegistries().get("especie") != null)
			result = titulo.getRegistries().get("especie").getValue();
		
		if (result.equalsIgnoreCase("DM"))
			result = "DM_DUPLICATA_MERCANTIL";

		return result;
	}

	public String getLocalPagamento() {
		return LOCAL_PAGAMENTO.replaceAll("#banco", bank);
	}

	public String getNumDoc() {
		if (titulo != null && titulo.getDocNumber() != null)
			return titulo.getDocNumber();
		else
			return "";
	}

	public String getSacadoCpf() {
		if (sacado != null && sacado.getRegistrations().get("cnpj") != null)
			return sacado.getRegistrations().get("cnpj").getValue();
		else
			return "";
	}

	public String getCedenteCpf() {
		if (cedente != null && cedente.getRegistrations().get("cnpj") != null)
			return cedente.getRegistrations().get("cnpj").getValue();
		else
			return "";
	}

	public String getCarteira() {
		if (contaCol != null && contaCol.getLine() != null) 
			return contaCol.getLine();
		else
			return "";
	}

	public int getCarteiraInt() {
		if (contaCol != null && contaCol.getLine() != null) 
			return Integer.parseInt(contaCol.getLine());
		else
			return 0;
	}

	public String getNumConvenio() {
		if (contaCol != null && contaCol.getAgreement() != null) 
			return contaCol.getAgreement();
		else
			return "";
	}

	public Vector<String> getInstrucoes() {
		Vector<String> instruc = new Vector<String>();
		instruc.add(INSTRUCOES.replaceAll("#banco", bank));
		return instruc;
	}

	public String getInstrucaoSacado() {
		return "";
	}

	public BigDecimal getDesconto() {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getDeducao() {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getMora() {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getAcrescimo() {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getValorCobrado() {
		// TODO Auto-generated method stub
		return null;
	}
}
