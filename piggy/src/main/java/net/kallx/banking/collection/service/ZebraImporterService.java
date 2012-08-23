/**
 * 
 */
package net.kallx.banking.collection.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.kallx.architecture.model.GenericModel;
import net.kallx.architecture.repository.PersistenceWrapper;
import net.kallx.architecture.repository.PersistenceWrapper.Querier;
import net.kallx.architecture.repository.Repository;
import net.kallx.banking.collection.model.Collection;
import net.kallx.banking.collection.model.CollectionAccount;
import net.kallx.banking.collection.model.CollectionSituation;
import net.kallx.banking.commons.model.Payee;
import net.kallx.banking.commons.model.Payer;
import net.kallx.banking.facade.BankingModuleFacade;
import net.kallx.modules.environment.model.political.City;
import net.kallx.modules.environment.model.political.State;
import net.kallx.modules.register.model.Address;
import net.kallx.modules.register.model.Contact;
import net.kallx.modules.register.model.Email;
import net.kallx.modules.register.model.LegalRegister;
import net.kallx.modules.register.model.Phone;
import net.kallx.modules.register.model.Registration;

/**
 * @author Tiago Felipe
 * 
 */
@Stateless
public class ZebraImporterService {

	@EJB
	private Repository<Collection> collectionRepository;

	@EJB
	private BankingModuleFacade bankingFacade;

	@EJB
	private Repository<LegalRegister> companyRepository;

	@EJB
	private Repository<GenericModel> repository;

	@Inject
	PersistenceWrapper wrapper;

	@Schedule(hour="*", minute="*/2", persistent=false)
	public void init() {

		System.out.println("\n\n\n Executando servico de pesquisa do Zebra.... \n\n\n");
		
		Querier queryHere = wrapper.createQuery("select max(z.lastImportedCollectionId) from ZebraImporterInfo z");
		Long lastIdHere = queryHere.executeSingle();
		
		Long lastIdThere = null;
		
		try (Connection con = DriverManager.getConnection(
				"jdbc:mysql://kallxtechnology.com:3306", "marcosromans",
				"mV240705")) {
			System.out.println("Conectado com a base de dados do Zebra.");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select max(c.cId) as cLast from bill.Collection c");

			rs.next();

			lastIdThere = rs.getLong("cLast");

		} catch (Exception e) {
		}
		
		System.out.println(lastIdHere + " - " + lastIdThere);
		
		if (lastIdHere != null && !(lastIdThere > lastIdHere ))
			return;

		int count = 0;
		System.out
				.println("\n\n\n\nIniciando comunicação com o BD do Zebra...\n\n\n");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Classe do driver mysql não encontrada"
					+ e.getMessage());
		}
		try (Connection con = DriverManager.getConnection(
				"jdbc:mysql://kallxtechnology.com:3306", "marcosromans",
				"mV240705")) {
			System.out.println("Conectado com a base de dados do Zebra.");

			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from bill.Collection c, banking.CollectionAccount ca where c.cAccountId = ca.cId order by c.cId desc limit 10");
			while (rs.next()) {
				
				try {

				Collection c = new Collection();
				
				long id = rs.getLong("cId");

				c.setSituation(CollectionSituation.OPEN);
				c.addHistoryEntry(null, Calendar.getInstance(),
						CollectionSituation.OPEN); // TODO setar usuario

				String collectionAccount = rs.getString("cName");
				CollectionAccount account = bankingFacade.getRepository(
						CollectionAccount.class).namedQuery(
						CollectionAccount.findByName, CollectionAccount.class,
						"name", collectionAccount);

				c.addRegistration("aceite", rs.getString("cBrAceite"));
				c.addRegistration("Situação", rs.getString("cSituation"));
				c.addRegistration("idNota", rs.getString("cInvoiceId"));
				c.setDocNumber(rs.getString("cDocNumber")); //seu numero
				c.setOurNumber("1098376537"); // Nosso numero, dados fake até o portal gerar o nosso numero, TODO modificar isso assim que possivel

				// Seta data da geracao do arquivo no modelo
				Date date = rs.getDate("cProcessDate");
				Calendar issue = new GregorianCalendar();
				issue.setTime(date);
				c.setIssue(issue);

				// seta data de vencimento no modelo
				Date date2 = rs.getDate("cMaturityDate");
				Calendar maturity = new GregorianCalendar();
				maturity.setTime(date2);
				c.setMaturity(maturity);

				// seta data de geração da nota fiscal
				Date date3 = rs.getDate("cDocDate");
				Calendar docDate = new GregorianCalendar();
				docDate.setTime(date3);
				c.setDocDate(docDate);

				// seta o valor da cobranca no modelo
				c.setAmount(rs.getDouble("cValue") / 100);     //TODO esta errado, se vem o valor 20000 ele coloca 200,00 e não 20000,00

				String companyIdentification = rs.getString("cCompanyIdentification");

				//Busca no banco se essa empresa já existe, senão existir vamos criar
				List<LegalRegister> registers = companyRepository.namedQuery(
						LegalRegister.findByRegistration, "type", "cnpj",
						"value", companyIdentification);

				LegalRegister r = null;

				if (registers == null)
					registers = new ArrayList<>();

				if (registers.size() == 0) {
					//Empresa não existe, criando um novo sacado...
					r = new LegalRegister();
					r.setCompanyName(rs.getString("cCompanyName"));
					
					
					/////////////////valida CNPJ//////////////////
					String value = rs.getString("cCompanyIdentification");
					Pattern pattern = Pattern.compile("[^0-9][a-zA-Z ]*");
				 	Matcher matcher = pattern.matcher(value);
				 	value = matcher.replaceAll("");
				 	value = value.trim();
				 	if (value.length() > 14)
				 		value = value.substring(0, 14);
				 	//seta CNPJ
					r.getRegistrations().put("cnpj", new Registration("cnpj", value));
					//Novo endereço
					Address a = new Address();
					
					String cityName = rs.getString("cCompanyCity");
					String stateAbbreviation = rs.getString("cCompanyState");

					City city = wrapper.namedQuery(City.findByName, City.class, "name", cityName);
					if (city == null) {
						city = new City();
						city.setName(cityName);
						State state = wrapper.namedQuery(State.findByAbbreviation, State.class, "abbreviation", stateAbbreviation);
						if (state == null){
							state = new State();
							state.setAbbreviation(stateAbbreviation);
							wrapper.saveOrUpdate(state);
						}
						city.setState(state);
						wrapper.saveOrUpdate(city);
					}

					a.setCity(city);
					//seta bairro no endereço
					a.setNeighborhood(rs.getString("cCompanyNeighborhood"));
					a.setStreet(rs.getString("cCompanyAddress"));
					a.setZipCode(rs.getString("cCompanyZipCode"));

					r.addAddress(a);

					Payee cedente = new Payee();
					cedente.setRegister(r);

					wrapper.saveOrUpdate(cedente);

					c.setPayee(cedente);

				} else if (registers.size() == 1) {
					r = registers.get(0);

					Payee cedente = wrapper.namedQuery(Payee.findByRegister,
							Payee.class, "register", r);

					c.setPayee(cedente);
				} else
					System.out
							.println("Erro na consulta, CNPJ do cedente duplicado na base!!!");

				// Reutilizando a variável registers
				registers = null;
				/////////////////valida CNPJ//////////////////
					String value = rs.getString("cCostumerIdentification");
					Pattern pattern = Pattern.compile("[^0-9][a-zA-Z ]*");
				 	Matcher matcher = pattern.matcher(value);
				 	value = matcher.replaceAll("");
				 	value = value.trim();
				 	if (value.length() > 14)
				 		value = value.substring(0, 14);
				 	//seta CNPJ
					r.getRegistrations().put("cnpj", new Registration("cnpj", value));
				// Buscando registros com este CNPJ
				registers = companyRepository.namedQuery(LegalRegister.findByRegistration, "type", "cnpj", "value", value);

				if (registers.size() == 0 & registers.isEmpty()) {

					LegalRegister rc = new LegalRegister();

					Contact cont = new Contact();
					Email mail = new Email();
					mail.setEmail(rs.getString("cCostumerEmail"));
					cont.addEmail(mail);
					Phone fone = new Phone();
					fone.setNumber(rs.getString("cCostumerPhone"));

					rc.getRegistrations().put("cnpj",
							new Registration("cnpj", value));

					cont.addPhone(fone);
					rc.addContact(cont);
					rc.setCompanyName(rs.getString("cCostumerName"));
					Address b = new Address();
					b.setStreet(rs.getString("cCostumerAddress"));
					b.setNeighborhood(rs.getString("cCostumerNeighborhood"));
					
					String cityName = rs.getString("cCostumerCity");
					String stateAbbreviation = rs.getString("cCostumerState");
					
					City city = wrapper.namedQuery(City.findByName, City.class, "name", cityName);
					if (city == null) {
						city = new City();
						city.setName(cityName);
						State state = wrapper.namedQuery(State.findByAbbreviation, State.class, "abbreviation", stateAbbreviation);
						if (state == null){
							state = new State();
							state.setAbbreviation(stateAbbreviation);
							wrapper.saveOrUpdate(state);
						}
						city.setState(state);
						wrapper.saveOrUpdate(city);
					}
					
					b.setCity(city);
					b.setZipCode(rs.getString("cCostumerZipCode"));
					State state = new State();
					state.setAbbreviation(rs.getString("cCostumerState"));
					b.getCity().setState(state);
					rc.addAddress(b);

					Payer sacado = new Payer();
					sacado.setRegister(rc);
					c.setPayeer(sacado);

					wrapper.saveOrUpdate(sacado);

				} else if (registers.size() == 1) {
					r = registers.get(0);

					Payer sacado = wrapper.namedQuery(Payer.findByRegister,
							Payer.class, "register", r);

					c.setPayeer(sacado);
				} else
					System.out
							.println("Erro na consulta, CNPJ do cedente duplicado na base!!!");

				bankingFacade.saveCollection(c);
				count++;
				System.out.println("Importado(s): " + count + " registro(s).");
				
				ZebraImporterInfo info = new ZebraImporterInfo();
				info.setLastImportedCollectionId(id);
				info.setWhen(Calendar.getInstance());
				
				wrapper.saveOrUpdate(info);
				
				} catch (Exception e) {
					
					System.out.println("Registro " + count + " não pode ser importado, erro: \n" + e.getStackTrace());
					//e.printStackTrace();
					
				}

			}
			rs.close();
			st.close();
		} catch (SQLException e) {

			System.out.println("Erro na conexão" + e.getMessage());
			e.printStackTrace();
		}

	}

}

@Entity
@Table(name="zebraImporterInfo", schema="banking")
@NamedQuery(name="ZebraImporterInfo_findLastCollectionId", query="select max(z.when) from ZebraImporterInfo z")
class ZebraImporterInfo implements GenericModel {
	
	public static final String findLastCollectionId = "ZebraImporterInfo_findLastCollectionId";
	
	private long id;
	private Calendar when;
	private long lastImportedCollectionId;
	
	
	@Id
	@Column(name = "cId")
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="cWhen")
	public Calendar getWhen() {
		return when;
	}
	public void setWhen(Calendar when) {
		this.when = when;
	}
	
	
	@Column(name="cLastImportedCollectionId")
	public long getLastImportedCollectionId() {
		return lastImportedCollectionId;
	}
	public void setLastImportedCollectionId(long lastImportedCollectionId) {
		this.lastImportedCollectionId = lastImportedCollectionId;
	}
	
}