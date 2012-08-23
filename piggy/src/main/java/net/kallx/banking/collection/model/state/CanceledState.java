package net.kallx.banking.collection.model.state;

import java.util.Calendar;

import net.kallx.banking.collection.model.Collection;
import net.kallx.banking.collection.model.CollectionSituation;

public class CanceledState extends AbstractCollectionState implements CollectionState {

	private Collection collection;
	
	
	public CanceledState(Collection collection) {
		this.collection = collection;
	}
	
	@Override
	public StateChangeReport liquidate() {
		return StateChangeReport.falseChange("Impossivel liquidar uma cobrança cancelada.");
	}

	@Override
	public StateChangeReport quit() {
		return StateChangeReport.falseChange("Impossivel baixar uma cobrança já cancelada.");
	}

	@Override
	public StateChangeReport protest() {
		return StateChangeReport.falseChange("Você não pode protestar um titulo cancelado.");
	}

	@Override
	public StateChangeReport open() {
		collection.setSituation(CollectionSituation.OPEN);
		collection.addHistoryEntry(null, Calendar.getInstance(), CollectionSituation.OPEN); // TODO pegar o usuário
		return StateChangeReport.trueChange("Mudança efetuada com sucesso");
	}

	@Override
	public StateChangeReport cancel() {
		return StateChangeReport.falseChange("Você não pode cancelar uma cobrança que já tenha sido cancelada.");
	}

	@Override
	public StateChangeReport register() {
		return StateChangeReport.falseChange("Você não pode registrar uma cobrança que já tenha sido cancelada.");
	}
	
}