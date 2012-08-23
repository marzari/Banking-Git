package net.kallx.banking.collection.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.kallx.banking.collection.model.Collection;
import net.kallx.collection.delivery.CollectionAdapter;
import net.kallx.collection.delivery.CollectionAdapterItem;
import net.kallx.collection.delivery.CollectionAdapterSet;
import net.kallx.modules.register.model.LegalRegister;

public class CollectionPopulator {
	
	public List<Collection> populateToModel(List<CollectionAdapterSet> collections){
		
		List<Collection> ret = new ArrayList<>();
		

		return ret;
		
	}

	public List<CollectionAdapterSet> populateToAdapter(List<Collection> collections){

		// recebe uma lista de títulos
		// a lista pode ter titulos de multiplas empresas, e multiplas carteiras
		// deve ser gerada uma CollectionAdapter para cada combinação única empresa-carteira
		// solucao escolhida: gerar uma CollectionAdapter por carteira (pelo identificador da carteira)
		// implementacao #1 - gerar um mapa que classifica as cobrancas por carteira
		// #2 - gerar um CollectionAdapter por carteira

		Map<String, List<Collection>> mapped = new HashMap<>();

		// #1 gerando mapa com as cobranças classificadas por convenio
		for (Collection collection : collections) {
			String bank;
			
			if (collection.getAccount().getBankAccount().getBankUnit().getBank().getName() != null) {
			bank = collection.getAccount().getBankAccount().getBankUnit().getBank().getName();
			String agreement = collection.getAccount().getAgreement();

			// seguranca para que o numero de convenio nao se repita para bancos diferentes
			String key = bank + "-" + agreement;
			if (!mapped.containsKey(key))
				mapped.put(key, new ArrayList<Collection>());

			mapped.get(key).add(collection);
			}

		}

		// #2 - gerar um CollectionAdapter por carteira

		List<CollectionAdapterSet> ret = new ArrayList<>();
		CollectionAdapterSet set = new CollectionAdapterSet();
		ret.add(set);

		for (List<Collection> colls : mapped.values()) {

			CollectionAdapter adapter = new CollectionAdapter();

			boolean registered = false;

			// populando delivery lib
			for (Collection collection : colls) {

				if (!registered) {

					// Código do Banco na Compensação
					adapter.setCodeBank(collection.getAccount().getBankAccount().getBankUnit().getBank().getCode());
					// Código Agência Mantenedora da Conta
					adapter.setBankUnit(collection.getAccount().getBankAccount().getBankUnit().getNumber());
					// DV Dígito Verificador da Agência
					adapter.setBankUnitDigit(collection.getAccount().getBankAccount().getBankUnit().getDigit());
					// Número de Inscrição da Empresa
					adapter.setRegistration(((LegalRegister) collection.getPayee().getRegister()).getRegistration("cnpj").getValue());
					// Número da Conta Corrente
					adapter.setAccount(collection.getAccount().getBankAccount().getNumber());
					// DV Dígito Verificador da Conta
					adapter.setAccountDigit(collection.getAccount().getBankAccount().getDigit());
					// Código do Convênio no Banco
					adapter.setAgreementCodeInTheBank(collection.getAccount().getAgreement());
					// DV Dígito Verificador da Ag/Conta
					adapter.setAccountAndBankUnitDigit(collection.getAccount().getBankAccount().getDigit());
					// Tipo de Inscrição da Empresa
					adapter.setRegistrationType("02");
					// Nome da empresa
					adapter.setCompanyName(((LegalRegister) collection.getPayee().getRegister()).getCompanyName());

					registered = true;
				}

				CollectionAdapterItem item = new CollectionAdapterItem();

				// Valor do Título Valor Nominal do Título
				item.setAmount(collection.getAmount());
				// Nosso Número Identificação do Título no Banco
				item.setDocNumberBank(collection.getRegistry("nossoNumero"));
				// Código da Carteira
				item.setCodeCollectionPortfolio(collection.getAccount().getAgreement()); //  TODO
				// Nº do Documento de Cobrança
				item.setDocNumberCompany(collection.getRegistry("seuNumero"));
				// Vencimento Data de Vencimento do Título
				item.setMaturity(collection.getMaturity());
				// Data Emissão do Título Data da Emissão do Título
				item.setDateIssue(collection.getIssue());
				
				if (collection.getInterest() != null) {
					// Código do Juros de Mora
					item.setCodeInterest(Integer.valueOf(collection.getInterest().getCode()).toString());
					// Data Juros Mora
					item.setDateInterest(collection.getInterest().getDate());
					// Juros Mora Juros de Mora por Dia/Taxa
					item.setDailyValueOfInterest(collection.getInterest().getAmount());
				}

				if (collection.getDiscount() != null) {
					// Código do Desconto',
					// item.setDiscountCode(collection.getDiscount().getCode());
					// FIXME validar, pois há a entidade descontos
					// Data Desc. 1 Data do Desconto
					item.setOffDateFor(collection.getDiscount().getDateOff());
					// Desconto 1 Valor/Percentual a ser Concedido
					item.setDiscountPercentage(collection.getDiscount().getAmount());
				}

				if (collection.getProtesto() != null) {
					// Código p/ Protesto Código para Protesto
					item.setCodeProtest(Integer.valueOf(collection.getProtesto().getProtestType().getCode()).toString());
					// Prazo p/ Protesto Número de Dias para Protesto
					item.setDeadlineProtest(collection.getProtesto().getProtestType().getDescription());
				}

				if (collection.getQuit() != null) {
					// Código p/ Baixa/Devolução Código para Baixa/Devolução
					item.setCancelCode(collection.getQuit().getCode());
					// Prazo p/ Baixa/Devolução Número de Dias para Baixa/Devolução
					item.setDeadlineCancel(collection.getQuit().getTerm());
				}

				// Espécie de Título Espécie do Título
				item.setCollectionType(collection.getEspecieTitulo());
				if (collection.getPayeer().getRegister() != null) {
					// Aceite Identific. de Título Aceito/Não Aceito
					item.setAccept(((LegalRegister) collection.getPayee().getRegister()).getRegistration("aceite").getValue());
					// Número de Inscrição
					String value = ((LegalRegister) collection.getPayeer().getRegister()).getRegistration("cnpj").getValue();
					// Valida Número de Inscrição
					Pattern pattern = Pattern.compile("[^0-9][a-zA-Z ]*");
				 	Matcher matcher = pattern.matcher(value);
				 	value = matcher.replaceAll("");
				 	value = value.trim();
				 	if (value.length() > 14)
				 		value = value.substring(0, 14);
				 	item.setRegistrationNumberCostumer(value);
					// Nome do sacado
					item.setNameOfDrawee(((LegalRegister) collection.getPayeer().getRegister()).getCompanyName());
					// Endereço
					item.setAddress(((LegalRegister) collection.getPayeer().getRegister()).getFirstAddress().getStreet());
					// Bairro
					item.setNeighborhood(((LegalRegister) collection.getPayeer().getRegister()).getFirstAddress().getNeighborhood());
					// CEP
					item.setZipCode(((LegalRegister) collection.getPayeer().getRegister()).getFirstAddress().getZipCode());
					// Sufixo do CEP
					item.setZipCodeSuffix(((LegalRegister) collection.getPayeer().getRegister()).getFirstAddress().getZipSuffixCode());
					// Cidade
					item.setCity(((LegalRegister) collection.getPayeer().getRegister()).getFirstAddress().getCity().getName());
					// UF Unidade da Federação
					item.setUF(((LegalRegister) collection.getPayeer().getRegister()).getFirstAddress().getCity().getState().getAbbreviation());
				}

				// /////////////////////////itens do registro seguindo layout FEBRABAN 240 não populados////////////////////////////////

				// Cód. Mov. Código de Movimento Remessa',
				// Agência Mantenedora da Conta',
				// DV Dígito Verificador da Agência',
				// Conta Número Número da Conta Corrente',
				// DV Dígito Verificador da Conta',
				// DV Dígito Verificador da Ag/Conta',
				// Cadastramento Forma de Cadastr. do Título no Banco',
				// Documento Tipo de Documento',
				// Identificação da Emissão do Bloqueto',
				// Distrib. Bloqueto Identificação da Distribuição',
				// Ag. Cobradora Agência Encarregada da Cobrança',
				// DV Dígito Verificador da Agência',
				// Vlr IOF Valor do IOF a ser Recolhido',
				// Vlr Abatimento Valor do Abatimento',
				// Uso Empresa Cedente Identificação do Título na Empresa
				// Código da Moeda Código da Moeda',
				// Número do Contrato Nº do Contrato da Operação de Créd
				// Uso livre banco/empresa ou autorização de pagamento parcial
				// Tipo de Inscrição
				// Dt. Gravação Data de Gravação Remessa/Retorno (não tem no mql)
				// adapter.setDateRecordShipping(collection.getDocDate());
				// Data do Crédito (não tem no mql)
				// adapter.setDateCredit(collection.getDateCredit());

				set.getItems().add(item);

			}

			set.setAdapter(adapter);

		}

		return ret;

	}

}
