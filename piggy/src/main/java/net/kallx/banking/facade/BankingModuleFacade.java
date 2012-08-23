package net.kallx.banking.facade;



import java.util.List;

import javax.ejb.Remote;

import net.kallx.architecture.facade.ModuleFacade;
import net.kallx.banking.account.model.BankAccount;
import net.kallx.banking.collection.model.Collection;
import net.kallx.banking.collection.model.CollectionSituation;
import net.kallx.banking.payment.model.Payment;
import net.kallx.banking.payment.model.PaymentEvent;
import net.kallx.banking.service.CollectionChangeRequest;

@Remote
public interface BankingModuleFacade extends ModuleFacade {

	/**
	 * Salva a Collection passada como param.<br>
	 * Realiza validações de unicidade ao salvar uma collection.
	 * TODO Considerar mover esse save para um Serviço próprio da Collection.
	 * 
	 * @param entity
	 *            Entidade a ser salva.
	 * @return A entidade salva.
	 */
	Collection saveCollection(Collection entity);
	
	/**
	 * Salva uma conta bancária e suas dependências. No momento apenas a
	 * BankUnit se não existir.
	 * 
	 * @param entity
	 *            Entidade a ser persistida.
	 * @return Entidade salva.
	 */
	BankAccount saveBankAccount(BankAccount entity);
	
	byte[] printCollections(List<Collection> list); 
	void alterProperty(List<Collection> list, List<CollectionChangeRequest> requests);
	String exportToEMapping(List<Collection> toExport); 
	void executeEvent(PaymentEvent event, List<Payment> list);
	List<String> applyStatus(List<Collection> collections, CollectionSituation situation);
	
	/**
	 * Load a Collection by DocNumber Or ErpNumber field.
	 * 
	 * @param filter
	 *            Collection entity with fields necessary to the filters
	 * @return The Collections that matched with filter.
	 */
	Collection loadCollectionByDocNumberOrErpNumber(Collection filter);
}
