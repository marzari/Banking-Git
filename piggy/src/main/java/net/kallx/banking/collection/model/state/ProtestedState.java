package net.kallx.banking.collection.model.state;

import java.util.GregorianCalendar;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.kallx.banking.collection.model.Collection;
import net.kallx.banking.collection.model.CollectionSituation;
import net.kallx.banking.facade.BankingModuleFacade;
import net.kallx.banking.facade.BankingModuleFacadeImpl;

public class ProtestedState extends AbstractCollectionState implements CollectionState {

	private Collection collection;
	
	@EJB
	private BankingModuleFacadeImpl facade;
	
	public ProtestedState(Collection collection) {
		this.collection = collection;
	}
	
	private BankingModuleFacade getFacade() throws NamingException {
		return (BankingModuleFacade) InitialContext
				.doLookup("java:global/kallx-application/piggy/BankingModuleFacadeImpl");
	}
	
	@Override
	public StateChangeReport liquidate() {
		
		if (collection.getAccount().isRegistered()){
			if(collection.isConfirmed()) {
			StateChangeReport.getContext().getToRegisterList().add(collection);
			collection.addHistoryEntry(null, new GregorianCalendar(), CollectionSituation.LIQUIDATED);
			collection.setSituation(CollectionSituation.LIQUIDATED);
			collection.setConfirmed(false);
			try {
				getFacade().getRepository(Collection.class).load(collection).save();
			} catch (NamingException e) {
				e.printStackTrace();
			}

			return StateChangeReport.trueChange("Titulo liquidado, informação enviada ao banco");
			}
			else return StateChangeReport.falseChange("Cobrança aguardando confirmação de protesto do banco, ação não pode ser completada!");
				
		} else {
			collection.addHistoryEntry(null, new GregorianCalendar(), CollectionSituation.LIQUIDATED);
			collection.setSituation(CollectionSituation.LIQUIDATED);
			try {
				getFacade().getRepository(Collection.class).load(collection).save();
			} catch (NamingException e) {
				e.printStackTrace();
			}

			return StateChangeReport.trueChange("Titulo sem registro, liquidado com sucesso");
		}
	}

	@Override
	public StateChangeReport quit() {
		if (collection.getAccount().isRegistered()){
			if(collection.isConfirmed()) {
			StateChangeReport.getContext().getToRegisterList().add(collection);
			collection.addHistoryEntry(null, new GregorianCalendar(), CollectionSituation.QUITED);
			collection.setSituation(CollectionSituation.QUITED);
			collection.setConfirmed(false);
			try {
				getFacade().getRepository(Collection.class).load(collection).save();
			} catch (NamingException e) {
				e.printStackTrace();
			}
			return StateChangeReport.trueChange("Titulo baixado, informação enviada ao banco");
			}
			else return StateChangeReport.falseChange("Cobrança aguardando confirmação de protesto do banco, ação não pode ser completada.");
				
		} else {
			collection.addHistoryEntry(null, new GregorianCalendar(), CollectionSituation.QUITED);
			collection.setSituation(CollectionSituation.QUITED);
			try {
				getFacade().getRepository(Collection.class).load(collection).save();
			} catch (NamingException e) {
				e.printStackTrace();
			}

			return StateChangeReport.trueChange("Titulo sem registro, baixado com sucesso!");
		}
	}

	@Override
	public StateChangeReport protest() {
		if (collection.isConfirmed()){
			return StateChangeReport.falseChange("Cobrança já foi protestada, ação não realizada!");
			} else {
				return StateChangeReport.falseChange("Cobrança já foi protestada, aguardando retorno do banco.");
			}
	}

	@Override
	public StateChangeReport open() {
		return StateChangeReport.falseChange("Cobrança em protesto, não pode ser reaberta!");
	}

	@Override
	public StateChangeReport cancel() {
		if (collection.getAccount().isRegistered()){
			if(collection.isConfirmed()) {
			StateChangeReport.getContext().getToRegisterList().add(collection);
			collection.addHistoryEntry(null, new GregorianCalendar(), CollectionSituation.CANCELED);
			collection.setSituation(CollectionSituation.CANCELED);
			collection.setConfirmed(false);
			try {
				getFacade().getRepository(Collection.class).load(collection).save();
			} catch (NamingException e) {
				e.printStackTrace();
			}

			return StateChangeReport.trueChange("Titulo cancelado, informação enviada ao banco!");
			}
			else return StateChangeReport.falseChange("Cobrança aguardando confirmação de protesto do banco, ação não pode ser completada!");
				
		} else {
			collection.addHistoryEntry(null, new GregorianCalendar(), CollectionSituation.CANCELED);
			collection.setSituation(CollectionSituation.CANCELED);
			try {
				getFacade().getRepository(Collection.class).load(collection).save();
			} catch (NamingException e) {
				e.printStackTrace();
			}

			return StateChangeReport.trueChange("Titulo sem registro, cancelado com sucesso!");
		}
	}

	@Override
	public StateChangeReport register() {
		if (collection.getAccount().isRegistered()){
			if(collection.isConfirmed())
				return StateChangeReport.falseChange("Cobrança já esta registrada, ação não realizada!");
			else
				return StateChangeReport.falseChange("Ação não pode ser realizada, titulo em protesto aguardando processamento do banco!");
		} else {
			return StateChangeReport.falseChange("Carteira sem registro titulo não pode ser registrado!");
		}
	}
	
}