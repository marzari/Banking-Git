package net.kallx.banking.collection.model.state;

import java.util.GregorianCalendar;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.kallx.banking.collection.model.Collection;
import net.kallx.banking.collection.model.CollectionSituation;
import net.kallx.banking.facade.BankingModuleFacade;
import net.kallx.banking.facade.BankingModuleFacadeImpl;

public class QuitedState extends AbstractCollectionState implements CollectionState {

	private Collection collection;
	
	@EJB
	private BankingModuleFacadeImpl facade;

	public QuitedState(Collection collection) {
		this.collection = collection;
	}
	
	private BankingModuleFacade getFacade() throws NamingException {
		return (BankingModuleFacade) InitialContext
				.doLookup("java:global/kallx-application/piggy/BankingModuleFacadeImpl");
	}

	@Override
	public StateChangeReport liquidate() {
			return StateChangeReport.falseChange("Cobrança já baixada impossivel realizar liquidação!");
	}

	@Override
	public StateChangeReport quit() {
		if(collection.getAccount().isRegistered()) {
		if(collection.isConfirmed())
			return StateChangeReport.falseChange("Cobrança já baixada impossivel realizar ação novamente!");
		else
			return StateChangeReport.falseChange("Cobrança já baixada aguardando processamento do banco!");
		} else {
			return StateChangeReport.falseChange("Cobrança sem registro já baixada, impossivel realizar ação!");
	}
	}

	@Override
	public StateChangeReport protest() {
			return StateChangeReport.falseChange("Cobrança já baixada impossivel realizar protesto!");
	}

	@Override
	public StateChangeReport open() {
		if (collection.getAccount().isRegistered()){
			if(collection.isConfirmed()) {
			collection.addHistoryEntry(null, new GregorianCalendar(), CollectionSituation.OPEN);
			collection.setSituation(CollectionSituation.OPEN);
			collection.setConfirmed(false);
			try {
				getFacade().getRepository(Collection.class).load(collection).save();
			} catch (NamingException e) {
				e.printStackTrace();
			}
			return StateChangeReport.trueChange("Titulo reaberto com sucesso!");
			}
			else return StateChangeReport.falseChange("Cobrança aguardando confirmação de baixa do banco, impossivel ser reaberto!");
				
		} else {
			collection.addHistoryEntry(null, new GregorianCalendar(), CollectionSituation.OPEN);
			collection.setSituation(CollectionSituation.OPEN);
			try {
				getFacade().getRepository(Collection.class).load(collection).save();
			} catch (NamingException e) {
				e.printStackTrace();
			}
			return StateChangeReport.trueChange("Titulo sem registro, reaberto com sucesso!");
		}
	}

	@Override
	public StateChangeReport cancel() {
		return StateChangeReport.falseChange("Titulo já baixado, ação não realizada!");
	}

	@Override
	public StateChangeReport register() {
		return StateChangeReport.falseChange("Titulo já baixado, ação não realizada!");
	}

}