package net.kallx.banking.collection.model.state;

import net.kallx.banking.collection.model.CollectionSituation;

public interface CollectionState {

	StateChangeReport liquidate();
	StateChangeReport quit();
	StateChangeReport protest();
	StateChangeReport open();
	StateChangeReport cancel();
	StateChangeReport register();
	StateChangeReport applyStatus(CollectionSituation situation);
	
}