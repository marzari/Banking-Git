package net.kallx.banking.facade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;

import net.kallx.architecture.model.GenericModel;
import net.kallx.architecture.repository.Repository;
import net.kallx.banking.account.model.BankAccount;
import net.kallx.banking.account.model.CheckingAccount;
import net.kallx.banking.bank.model.BankUnit;
import net.kallx.banking.collection.model.Collection;
import net.kallx.banking.collection.model.CollectionAccount;
import net.kallx.banking.collection.model.CollectionFile;
import net.kallx.banking.collection.model.CollectionSituation;
import net.kallx.banking.collection.model.EspecieTitulo;
import net.kallx.banking.collection.model.state.CollectionState;
import net.kallx.banking.collection.model.state.CollectionStateFactory;
import net.kallx.banking.collection.model.state.StateChangeReport;
import net.kallx.banking.collection.service.CollectionPopulator;
import net.kallx.banking.payment.model.Payment;
import net.kallx.banking.payment.model.PaymentEvent;
import net.kallx.banking.payment.service.PaymentEventService;
import net.kallx.banking.payment.service.PropertySetterService;
import net.kallx.banking.service.CollectionChangeRequest;
import net.kallx.banking.service.CollectionChangeRequestProperty;
import net.kallx.banking.util.Collection2Boleto;
import net.kallx.collection.delivery.CollectionAdapterSet;
import net.kallx.kangaroo.delivery.service.DeliveryImporterService;
import net.kallx.kangaroo.document.facade.KangarooDocumentModuleFacade;
import net.kallx.kangaroo.document.model.LayoutInstance;
import net.kallx.kangaroo.document.model.ReferentialLayout;
import net.kallx.kangaroo.document.model.helpers.PhysicalLayoutInstance;
import net.kallx.kangaroo.document.service.ExporterService;
import net.kallx.kangaroo.document.service.ImportReport;
import net.kallx.modules.register.model.LegalRegister;
import net.kallx.utils.date.DateUtils;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.Aceite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@Remote(BankingModuleFacade.class)
public class BankingModuleFacadeImpl implements BankingModuleFacade {
	
	private static final Logger LOG = LoggerFactory.getLogger("net.kallx");

	private PaymentEventService paymentEventService;
	
	@Inject
	private PropertySetterService propertySetterService;

	private static final int BANCO_DO_BRASIL = 1;
	private static final int ITAU = 2;
	private static final int BANCO_REAL = 3;
	private static final int BRADESCO = 4;
	private static final int CAIXA_ECONOMICA = 5;
	private static final int HSBC = 6;
	private static final int NOSSACAIXA = 7;
	private static final int SANTANDER = 8;

	@EJB
	private Repository<GenericModel> repository;

	@EJB
	private KangarooDocumentModuleFacade kangaroo;

	@Inject
	private DeliveryImporterService deliveryImporter;

	@Inject
	private ExporterService exporterService;

	@SuppressWarnings("unchecked")
	@Override
	public <T extends GenericModel> Repository<T> getRepository(Class<T> clazz) {
		repository.loadClazz((Class<GenericModel>) clazz);
		return (Repository<T>) repository;
	}

	@Override
	public List<String> applyStatus(List<Collection> collections, CollectionSituation situation) {

		List<StateChangeReport> reports = new ArrayList<>();
		List<String> message = new ArrayList<>();

		for (Collection collection : collections) {

			CollectionState instance = CollectionStateFactory.getInstance(collection);
			StateChangeReport scr = instance.applyStatus(situation);

			message.add("Número do titulo: " + collection.getDocNumber() + " Nome do sacado: "
					+ ((LegalRegister) collection.getPayeer().getRegister()).getCompanyName() + " Detalhe: " + scr.getMessage());

			scr.setMessage(scr.getMessage());
			reports.add(scr);

		}
		return message;
	}
	
	public void alterProperty(List<Collection> list, List<CollectionChangeRequest> requests){
	       
        
        for (Collection c : list) {
       
            for (CollectionChangeRequest ccr : requests) {
               
                if (ccr.getProperty() == CollectionChangeRequestProperty.Account){
                    propertySetterService.alterAccount(c, (Long)ccr.getValue());
                }
                
                if (ccr.getProperty() == CollectionChangeRequestProperty.Especie){
                	propertySetterService.alterEspecie(c, (EspecieTitulo)ccr.getValue());
                }
           
            }
           
            getRepository(Collection.class).load(c).save();
 
        }
       
    }

	void dealReports(List<StateChangeReport> reports) {

		for (StateChangeReport stateChangeReport : reports) {

			stateChangeReport.getContext().getToRegisterList();

		}

	}

	private Boleto geraBoleto(Collection2Boleto boleto, ContaBancaria contaBancaria) {
		/*
		 * INFORMANDO DADOS SOBRE O CEDENTE.
		 */
		Cedente cedente = new Cedente(boleto.getCedenteNome(), boleto.getCedenteCpf());

		/*
		 * INFORMANDO DADOS SOBRE O SACADO.
		 */
		Sacado sacado = new Sacado(boleto.getSacadoNome(), boleto.getSacadoCpf());

		// Informando o endereço do sacado.
		Endereco enderecoSac = new Endereco();
		enderecoSac.setUF(UnidadeFederativa.valueOfSigla(boleto.getSacadoUf()));
		enderecoSac.setLocalidade(boleto.getSacadoCidade());
		enderecoSac.setCep(new CEP(boleto.getSacadoCep()));
		enderecoSac.setBairro(boleto.getSacadoBairro());
		enderecoSac.setLogradouro(boleto.getSacadoEndereco());
		enderecoSac.setNumero(null);
		sacado.addEndereco(enderecoSac);

		/*
		 * INFORMANDO OS DADOS SOBRE O TÍTULO.
		 */

		Titulo titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNumeroDoDocumento(boleto.getNumDoc());
		titulo.setNossoNumero(boleto.getNossoNumero());
		titulo.setValor(BigDecimal.valueOf(boleto.getValorBoleto()));
		titulo.setDataDoDocumento(boleto.getDataDocumento());
		titulo.setDataDoVencimento(boleto.getDataVencimento());
		titulo.setTipoDeDocumento(TipoDeTitulo.valueOf(boleto.getEspecieDoc()));
		titulo.setAceite(Aceite.valueOf(boleto.getAceite()));
		titulo.setDesconto(boleto.getDesconto());
		titulo.setDeducao(boleto.getDeducao());
		titulo.setMora(boleto.getMora());
		titulo.setAcrecimo(boleto.getAcrescimo());
		titulo.setValorCobrado(boleto.getValorCobrado());

		/*
		 * INFORMANDO OS DADOS SOBRE O BOLETO.
		 */
		Boleto bol = new Boleto(titulo);

		bol.setLocalPagamento(boleto.getLocalPagamento());
		bol.setInstrucaoAoSacado(boleto.getInstrucaoSacado());

		Vector<String> instrucoes = boleto.getInstrucoes();
		for (int i = 0; i < instrucoes.size(); i++)
			switch (i) {
			case 0:
				bol.setInstrucao1(instrucoes.get(i));
				break;
			case 1:
				bol.setInstrucao2(instrucoes.get(i));
				break;
			case 2:
				bol.setInstrucao3(instrucoes.get(i));
				break;
			case 3:
				bol.setInstrucao4(instrucoes.get(i));
				break;
			case 4:
				bol.setInstrucao5(instrucoes.get(i));
				break;
			case 5:
				bol.setInstrucao6(instrucoes.get(i));
				break;
			case 6:
				bol.setInstrucao7(instrucoes.get(i));
				break;
			case 7:
				bol.setInstrucao8(instrucoes.get(i));
				break;
			}

		return bol;
	}

	private Boleto geraBoletoBB(Collection collection) {
		Collection2Boleto boleto = new Collection2Boleto(collection, Collection2Boleto.BANCO_DO_BRASIL);

		// Informando dados sobre a conta bancária do título.
		ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.BANCO_DO_BRASIL.create());
		contaBancaria.setNumeroDaConta(new NumeroDaConta(boleto.getContaCorrenteInt(), boleto.getContaCorrenteDV()));
		contaBancaria.setCarteira(new Carteira(boleto.getCarteiraInt()));
		contaBancaria.setAgencia(new Agencia(boleto.getAgenciaInt(), boleto.getAgenciaDV()));

		return geraBoleto(boleto, contaBancaria);
	}

	private Boleto geraBoletoItau(Collection collection) {
		Collection2Boleto boleto = new Collection2Boleto(collection, Collection2Boleto.ITAU);

		// Informando dados sobre a conta bancária do título.
		ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.BANCO_ITAU.create());
		contaBancaria.setNumeroDaConta(new NumeroDaConta(boleto.getContaCorrenteInt(), boleto.getContaCorrenteDV()));
		contaBancaria.setCarteira(new Carteira(boleto.getCarteiraInt()));
		contaBancaria.setAgencia(new Agencia(boleto.getAgenciaInt(), boleto.getAgenciaDV()));

		return geraBoleto(boleto, contaBancaria);
	}

	private Boleto geraBoletoReal(Collection collection) {
		Collection2Boleto boleto = new Collection2Boleto(collection, Collection2Boleto.REAL);

		// Informando dados sobre a conta bancária do título.
		ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.BANCO_ABN_AMRO_REAL.create());
		contaBancaria.setNumeroDaConta(new NumeroDaConta(boleto.getContaCorrenteInt(), boleto.getContaCorrenteDV()));
		contaBancaria.setCarteira(new Carteira(boleto.getCarteiraInt()));
		contaBancaria.setAgencia(new Agencia(boleto.getAgenciaInt(), boleto.getAgenciaDV()));

		return geraBoleto(boleto, contaBancaria);
	}

	private Boleto geraBoletoCEF(Collection collection) {
		Collection2Boleto boleto = new Collection2Boleto(collection, Collection2Boleto.CEF);

		// Informando dados sobre a conta bancária do título.
		ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.CAIXA_ECONOMICA_FEDERAL.create());
		contaBancaria.setNumeroDaConta(new NumeroDaConta(boleto.getContaCorrenteInt(), boleto.getContaCorrenteDV()));
		contaBancaria.setCarteira(new Carteira(boleto.getCarteiraInt()));
		contaBancaria.setAgencia(new Agencia(boleto.getAgenciaInt(), boleto.getAgenciaDV()));

		return geraBoleto(boleto, contaBancaria);
	}

	private Boleto geraBoletoBradesco(Collection collection) {
		Collection2Boleto boleto = new Collection2Boleto(collection, Collection2Boleto.BRADESCO);

		// Informando dados sobre a conta bancária do título.
		ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.BANCO_BRADESCO.create());
		contaBancaria.setNumeroDaConta(new NumeroDaConta(boleto.getContaCorrenteInt(), boleto.getContaCorrenteDV()));
		contaBancaria.setCarteira(new Carteira(boleto.getCarteiraInt()));
		contaBancaria.setAgencia(new Agencia(boleto.getAgenciaInt(), boleto.getAgenciaDV()));

		return geraBoleto(boleto, contaBancaria);
	}

	private Boleto geraBoletoSantander(Collection collection) {
		Collection2Boleto boleto = new Collection2Boleto(collection, Collection2Boleto.SANTANDER);

		// Informando dados sobre a conta bancária do título.
		ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.BANCO_SANTANDER.create());
		contaBancaria.setNumeroDaConta(new NumeroDaConta(boleto.getContaCorrenteInt(), boleto.getContaCorrenteDV()));
		contaBancaria.setCarteira(new Carteira(boleto.getCarteiraInt()));
		contaBancaria.setAgencia(new Agencia(boleto.getAgenciaInt(), boleto.getAgenciaDV()));

		return geraBoleto(boleto, contaBancaria);
	}

	private Boleto geraBoletoHSBC(Collection collection) {
		Collection2Boleto boleto = new Collection2Boleto(collection, Collection2Boleto.HSBC);

		// Informando dados sobre a conta bancária do título.
		ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.HSBC.create());
		contaBancaria.setNumeroDaConta(new NumeroDaConta(boleto.getContaCorrenteInt(), boleto.getContaCorrenteDV()));
		contaBancaria.setCarteira(new Carteira(boleto.getCarteiraInt()));
		contaBancaria.setAgencia(new Agencia(boleto.getAgenciaInt(), boleto.getAgenciaDV()));

		return geraBoleto(boleto, contaBancaria);
	}

	private Boleto geraBoletoNossaCaixa(Collection collection) {
		Collection2Boleto boleto = new Collection2Boleto(collection, Collection2Boleto.NOSSA_CAIXA);

		// Informando dados sobre a conta bancária do título.
		ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.NOSSA_CAIXA.create());
		contaBancaria.setNumeroDaConta(new NumeroDaConta(boleto.getContaCorrenteInt(), boleto.getContaCorrenteDV()));
		contaBancaria.setCarteira(new Carteira(boleto.getCarteiraInt()));
		contaBancaria.setAgencia(new Agencia(boleto.getAgenciaInt(), boleto.getAgenciaDV()));

		return geraBoleto(boleto, contaBancaria);
	}

	private int getBank(String banco) {
		if (banco != null)
			if (banco.equalsIgnoreCase("001"))
				return BANCO_DO_BRASIL;
			else if (banco.equalsIgnoreCase("341"))
				return ITAU;
			else if (banco.equalsIgnoreCase("356"))
				return BANCO_REAL;
			else if (banco.equalsIgnoreCase("237"))
				return BRADESCO;
			else if (banco.equalsIgnoreCase("104"))
				return CAIXA_ECONOMICA;
			else if (banco.equalsIgnoreCase("399"))
				return HSBC;
			else if (banco.equalsIgnoreCase("151"))
				return NOSSACAIXA;
			else if (banco.equalsIgnoreCase("033") || banco.equalsIgnoreCase("351") || banco.equalsIgnoreCase("353"))
				return SANTANDER;

		return -1;
	}

	@Override
	public byte[] printCollections(List<Collection> list) {
		List<Boleto> boletos = new ArrayList<Boleto>();

		for (Collection collection : list) {
			switch (getBank(collection.getAccount().getBankAccount().getBankUnit().getBank().getCode())) {
			case BANCO_DO_BRASIL:
				boletos.add(geraBoletoBB(collection));
				break;
			case ITAU:
				boletos.add(geraBoletoItau(collection));
				break;
			case BANCO_REAL:
				boletos.add(geraBoletoReal(collection));
				break;
			case BRADESCO:
				boletos.add(geraBoletoBradesco(collection));
				break;
			case CAIXA_ECONOMICA:
				boletos.add(geraBoletoCEF(collection));
				break;
			case HSBC:
				boletos.add(geraBoletoHSBC(collection));
				break;
			case NOSSACAIXA:
				boletos.add(geraBoletoNossaCaixa(collection));
				break;
			case SANTANDER:
				boletos.add(geraBoletoSantander(collection));
				break;
			}
		}

		return BoletoViewer.groupInOnePDF(boletos);
	}
	
	
	public String validaCollection(List<Collection> collection) {
		
		for (Collection c : collection) {

			if (c.getAccount() == null)
				return "Erro: remessa não gerada, vincule os titulos selecionados a pelo menos uma carteira";
			if (c.getEspecieTitulo() == null)
				return "Erro: remessa não gerada, selecione uma espécie antes de gerar a remessa";
			if (c.getOurNumber() == null)
				return "Erro: remessa não gerada, nosso número nulo.";
			if (c.getDocNumber() == null)
				return "Erro: remessa não gerada, seu numero nulo";
			if (DateUtils.truncateDate(c.getMaturity()).before(DateUtils.truncateDate(Calendar.getInstance())))
				return "Erro: remessa não gerada, há títulos com vencimento menor que a data atual";
		}
		
		return "";
		
	}

	@Override
	public String exportToEMapping(List<Collection> toExport) {
		
		if (toExport.isEmpty())
			return "Erro: nenhum titulo selecionado";
		
		
		String validaCollection = validaCollection(toExport);
		if (validaCollection.length() > 1)
			return validaCollection;

		try {
			List<CollectionAdapterSet> populateSet = new CollectionPopulator().populateToAdapter(toExport);
			ReferentialLayout layout = kangaroo.getRepository(ReferentialLayout.class).namedQuery(
					ReferentialLayout.findByName, ReferentialLayout.class, "name", "cobranca_remessa");

			if (layout == null)
				return "Erro: não há layout cadastrado na base";

			ArrayList<CollectionAdapterSet> arrayList = new ArrayList<>(populateSet);
			CollectionAdapterSet collectionAdapterSet = arrayList.get(0);

			LOG.debug("Gerou " + collectionAdapterSet.getTotalItems() + " itens");

			LayoutInstance generated = deliveryImporter.doImport(layout, collectionAdapterSet);

			LOG.debug("InstanceLayout retornou " + generated.getSegments().size() + " referential segments");

			ImportReport report = exporterService.run("Cobranca Itau Remessa 400 Posicoes", generated);

			LOG.debug("InstanceLayout retornou " + report.getPhysicalInstance().getSegments().size() + " physical segments");

			CollectionFile cf = new CollectionFile();
			cf.setContent(report.getSerializedLayout().toString());
			cf.setWhen(Calendar.getInstance());

			PhysicalLayoutInstance pi = report.getPhysicalInstance();
			cf.setPhysicalInstance(pi);
			cf.setInstance(report.getReferentialInstance());
			getRepository(CollectionFile.class).load(cf).save();
		} catch (Exception e) {
			LOG.error("Erro: Dados insuficientes para geração da remessa", e);
			return "Erro: Dados insuficientes para geração da remessa";
		}


		// cf.setQuantity(pi.getSegments().size()); FIXME comentado pois não compila

		return "Sucesso: remessa gerada! Para vizualizar clique no menu superior 'Remessa Cobrança', e depois em download.";
	}

	@Override
	public void executeEvent(PaymentEvent event, List<Payment> list) {

		// deal with eventing logic\
		switch (event) {
		case Cancel:
			paymentEventService.cancelPayments(list);
			break;

		default:
			break;
		}
	}

	public Collection loadCollectionByDocNumberOrErpNumber(Collection filter) {
		List<Collection> query = getRepository(Collection.class).namedQuery(Collection.filterByDocNumberOrOurNumber,
				"docNumber", filter.getDocNumber(), "ourNumber", filter.getOurNumber());

		// FIXME fwk must handle this. Remove after implementation is done.
		if (query == null || (query != null && query.isEmpty()))
			throw new EntityNotFoundException("Título não Econtrado");

		// FIXME fwk must handle this. Remove after implementation is done.
		if (query != null && query.size() > 1)
			throw new NonUniqueResultException("Há Títulos Duplicados");

		return query.get(0);
	}

	@Override
	public Collection saveCollection(Collection entity) {
		Collection collection = getRepository(Collection.class).load(entity).namedQuery(
				Collection.filterByDocNumber, Collection.class, "docNumber", entity.getDocNumber());
		
//		if (collection != null)
//			throw new NonUniqueResultException("Documento já associado a um Título"); // FIXME
		
		collection = getRepository(Collection.class).load(entity).namedQuery(
				Collection.filterByOurNumber, Collection.class, "ourNumber",
				entity.getOurNumber());
		
//		if (collection != null)
//			throw new NonUniqueResultException("Já existe um Título com o número digitado"); // FIXME
		
		if (entity != null && entity.getId() == 0 && entity.getSituation() == null) {
			entity.setSituation(CollectionSituation.OPEN);
			entity.addHistoryEntry(null, Calendar.getInstance(), CollectionSituation.OPEN);
		}

		Repository<Collection> savedEntity = getRepository(Collection.class).load(entity).save();
		return savedEntity.getModel();
	}

	@Override
	public BankAccount saveBankAccount(BankAccount entity) {
		if (entity == null)
			return null;

		// salva/atualiza agência
		Repository<BankUnit> savedBankUnit = getRepository(BankUnit.class).load(entity.getBankUnit()).save();
		entity.setBankUnit(savedBankUnit.getModel());
		
		// salva a entidade 
		Repository<BankAccount> savedEntity = getRepository(BankAccount.class).load(entity).save();
		
		// salva as collectionAccount da conta
		if (entity instanceof CheckingAccount) {
			CheckingAccount chkAccount = (CheckingAccount) entity;
			
			if (chkAccount.getCollectionAccounts() != null) {
				Set<CollectionAccount> list = new HashSet<CollectionAccount>();
				for (CollectionAccount iter : chkAccount.getCollectionAccounts()) {
					iter.setBankAccount((CheckingAccount) savedEntity.getModel());
					Repository<CollectionAccount> save = getRepository(CollectionAccount.class).load(iter).save();
					list.add(save.getModel());
				}
				((CheckingAccount) entity).setCollectionAccounts(list);
			}
		}
		
		return savedEntity.getModel();
	}
}