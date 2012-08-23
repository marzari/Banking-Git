package net.kallx.banking.collection.model.state;

import net.kallx.banking.collection.model.CollectionSituation;

public abstract class AbstractCollectionState implements CollectionState {

	@Override
	public StateChangeReport applyStatus(CollectionSituation situation) {
		
		switch (situation) {
			case CANCELED: return cancel();
			case LIQUIDATED: return liquidate();
			case OPEN: return open();
			case PROTESTED: return protest();
			case QUITED: return quit();
			case REGISTERED: return register();
		}
		return null;
	}

}
