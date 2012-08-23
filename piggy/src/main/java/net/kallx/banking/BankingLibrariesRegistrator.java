package net.kallx.banking;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import net.kallx.collection.delivery.CollectionAdapter;
import net.kallx.kangaroo.delivery.service.DeliveryLibraryRegistrator;

@Startup
@Singleton
public class BankingLibrariesRegistrator {
	
	@EJB
	private DeliveryLibraryRegistrator registrator;

	@PostConstruct
	void init(){
		registrator.addClass(CollectionAdapter.class);
	}
	
}