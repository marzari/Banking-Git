package net.kallx.banking.collection.model.state;

import java.util.GregorianCalendar;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.kallx.banking.collection.model.Collection;
import net.kallx.banking.collection.model.CollectionSituation;
import net.kallx.banking.facade.BankingModuleFacade;
import net.kallx.banking.facade.BankingModuleFacadeImpl;

public class OpenState extends AbstractCollectionState implements CollectionState {

	private Collection collection;
	
	
	public OpenState(Collection collection) {
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
		if (collection.getAccount().isRegistered()){
			//cobrança com registro isRegistered = true, deve ser registrado antes
			return StateChangeReport.falseChange("Usuário deve antes registrar esta cobrança.");
		} else {
			//cobrança sem registro pode liquidar
			collection.setSituation(CollectionSituation.LIQUIDATED);
			collection.addHistoryEntry(null, new GregorianCalendar(), CollectionSituation.LIQUIDATED);
			try {
				getFacade().getRepository(Collection.class).load(collection).save();
			} catch (NamingException e) {
				e.printStackTrace();
			}
			return StateChangeReport.trueChange("Cobrança liquidada com sucesso.");
		}
	}

	@Override
	public StateChangeReport quit() {
		if (collection.getAccount().isRegistered()){
			return StateChangeReport.falseChange("Usuário deve antes registrar esta cobrança.");
		} else {
			collection.setSituation(CollectionSituation.QUITED);
			collection.addHistoryEntry(null, new GregorianCalendar(), CollectionSituation.QUITED);
			try {
				getFacade().getRepository(Collection.class).load(collection).save();
			} catch (NamingException e) {
				e.printStackTrace();
			}
			
			return StateChangeReport.trueChange("Cobrança baixada com sucesso.");
		}
	}
	

	@Override
	public StateChangeReport protest() {
		if (collection.getAccount().isRegistered()){
			return StateChangeReport.falseChange("Usuário deve antes registrar esta cobrança.");
		} else {
			collection.setSituation(CollectionSituation.PROTESTED);
			collection.addHistoryEntry(null, new GregorianCalendar(), CollectionSituation.PROTESTED);
			try {
				getFacade().getRepository(Collection.class).load(collection).save();
			} catch (NamingException e) {
				e.printStackTrace();
			}
			return StateChangeReport.trueChange("Cobrança sem registro, status alterado para protesto.");
		}
	
	}

	@Override
	public StateChangeReport open() {
		return StateChangeReport.falseChange("Cobrança já aberta.");
	}

	@Override
	public StateChangeReport cancel() {
		collection.setSituation(CollectionSituation.CANCELED);
		collection.addHistoryEntry(null, new GregorianCalendar(), CollectionSituation.CANCELED);
		try {
			getFacade().getRepository(Collection.class).load(collection).save();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return StateChangeReport.trueChange("Cobrança cancelada com sucesso!");
	}

	@Override
	public StateChangeReport register() {
		if (collection.getAccount().isRegistered()){
			StateChangeReport.getContext().getToRegisterList().add(collection);
			collection.addHistoryEntry(null, new GregorianCalendar(), CollectionSituation.REGISTERED);
			collection.setSituation(CollectionSituation.REGISTERED);
			collection.setConfirmed(false);
			try {
				getFacade().getRepository(Collection.class).load(collection).save();
			} catch (NamingException e) {
				e.printStackTrace();
			}
			return StateChangeReport.trueChange("Cobrança enviada ao banco, aguardar confirmação de registro.");
		} else {
			return StateChangeReport.falseChange("Um titulo de uma carteira que não é de registro não pode ser registrado.");
		}
	}
	
}