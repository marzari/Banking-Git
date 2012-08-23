package net.kallx.banking.collection.model.state;

import net.kallx.banking.collection.model.Collection;
import net.kallx.banking.collection.model.CollectionSituation;

public class CollectionStateFactory {
	
	private CollectionStateFactory(){};

	public static CollectionState getInstance(Collection collection){
		
		if (collection == null)
			throw new IllegalStateException();
		
		if (collection.getSituation() == null)
			throw new IllegalStateException();
		
		CollectionSituation situation = collection.getSituation();
		
		switch (situation) {
			case CANCELED: return new CanceledState(collection);
			case OPEN: return new OpenState(collection);
			case LIQUIDATED: return new LiquidatedState(collection);
			case PROTESTED: return new ProtestedState(collection);
			case QUITED: return new QuitedState(collection);
			case REGISTERED: return new RegisteredState(collection);
		}
		
		throw new IllegalStateException();
		
	}
	
}



