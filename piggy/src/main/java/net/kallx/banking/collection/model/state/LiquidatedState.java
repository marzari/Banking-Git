package net.kallx.banking.collection.model.state;

import java.util.GregorianCalendar;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.kallx.banking.collection.model.Collection;
import net.kallx.banking.collection.model.CollectionSituation;
import net.kallx.banking.facade.BankingModuleFacade;
import net.kallx.banking.facade.BankingModuleFacadeImpl;

public class LiquidatedState extends AbstractCollectionState implements CollectionState {
	
	private Collection collection;
	
	public LiquidatedState(Collection collection) {
		this.collection = collection;
	}
	
	@EJB
	private BankingModuleFacadeImpl facade;
	
	private BankingModuleFacade getFacade() throws NamingException {
		return (BankingModuleFacade) InitialContext
				.doLookup("java:global/kallx-application/piggy/BankingModuleFacadeImpl");
	}

	@Override
	public StateChangeReport liquidate() {
		return StateChangeReport.falseChange("Esta cobrança já foi liquidada!");
	}

	@Override
	public StateChangeReport quit() {
		return StateChangeReport.falseChange("Esta cobrança já foi liquidada, não há como dar baixa.");
	}

	@Override
	public StateChangeReport protest() {
		return StateChangeReport.falseChange("Você não pode protestar uma cobrança que já tenha sido liquidada.");
	}

	@Override
	public StateChangeReport open() {
		collection.setSituation(CollectionSituation.OPEN);
		collection.addHistoryEntry(null, new GregorianCalendar(), CollectionSituation.OPEN);
		try {
			getFacade().getRepository(Collection.class).load(collection).save();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return StateChangeReport.trueChange("Cobrança reaberta com sucesso!");
	}

	@Override
	public StateChangeReport cancel() {
		return StateChangeReport.falseChange("Você não pode cancelar uma cobrança já liquidada!");
	}

	@Override
	public StateChangeReport register() {
		return StateChangeReport.falseChange("Cobrança já liquidada!");
	}
	
}