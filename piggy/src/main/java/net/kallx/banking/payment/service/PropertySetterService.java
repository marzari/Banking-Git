package net.kallx.banking.payment.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import net.kallx.architecture.model.GenericModel;
import net.kallx.architecture.repository.Repository;
import net.kallx.banking.collection.model.Collection;
import net.kallx.banking.collection.model.CollectionAccount;
import net.kallx.banking.collection.model.EspecieTitulo;

@Stateless
public class PropertySetterService {

	@EJB
	private Repository<GenericModel> repository;

	@SuppressWarnings("unchecked")
	public <T extends GenericModel> Repository<T> getRepository(Class<T> clazz) {
		repository.loadClazz((Class<GenericModel>) clazz);
		return (Repository<T>) repository;
	}

	public void alterAccount(Collection collection, long collectionAccountId) {
		Repository<CollectionAccount> load = getRepository(
				CollectionAccount.class).load(collectionAccountId);
		collection.setAccount(load.getModel());

	}

	public void alterEspecie(Collection collection, EspecieTitulo especieTitulo) {

		collection.setEspecieTitulo(especieTitulo);

	}

}
