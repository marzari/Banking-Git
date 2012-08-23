package net.kallx.banking.collection.model.state;

import java.util.GregorianCalendar;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.kallx.banking.collection.model.Collection;
import net.kallx.banking.collection.model.CollectionSituation;
import net.kallx.banking.facade.BankingModuleFacade;
import net.kallx.banking.facade.BankingModuleFacadeImpl;

public class RegisteredState extends AbstractCollectionState implements CollectionState {

	private Collection collection;

	public RegisteredState(Collection collection) {
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
		if (collection.isConfirmed()) {
			StateChangeReport.getContext().getToRegisterList().add(collection);
			collection.setSituation(CollectionSituation.LIQUIDATED);
			collection.addHistoryEntry(null, new GregorianCalendar(),
					CollectionSituation.LIQUIDATED);
			collection.setConfirmed(false);
			try {
				getFacade().getRepository(Collection.class).load(collection).save();
			} catch (NamingException e) {
				e.printStackTrace();
			}
			return StateChangeReport
					.trueChange("Liquidação efetuada com sucesso!");
		} else {
			return StateChangeReport
					.falseChange("Cobrança ainda aguardando confirmação de registro do banco, ação não pode ser realizada!");
		}
	}

	@Override
	public StateChangeReport quit() {
		if (collection.isConfirmed()) {
			StateChangeReport.getContext().getToRegisterList().add(collection);
			collection.setSituation(CollectionSituation.QUITED);
			collection.addHistoryEntry(null, new GregorianCalendar(),
					CollectionSituation.QUITED);
			collection.setConfirmed(false);
			try {
				getFacade().getRepository(Collection.class).load(collection).save();
			} catch (NamingException e) {
				e.printStackTrace();
			}
			return StateChangeReport.trueChange("Baixa realizada com sucesso!");
		} else {
			return StateChangeReport
					.falseChange("Cobrança ainda aguardando confirmação de registro do banco, ação não pode ser realizada!");
		}
	}

	@Override
	public StateChangeReport protest() {
		if (collection.isConfirmed()) {
			StateChangeReport.getContext().getToRegisterList().add(collection);
			collection.setSituation(CollectionSituation.PROTESTED);
			collection.addHistoryEntry(null, new GregorianCalendar(),
					CollectionSituation.PROTESTED);
			collection.setConfirmed(false);
			try {
				getFacade().getRepository(Collection.class).load(collection).save();
			} catch (NamingException e) {
				e.printStackTrace();
			}
			return StateChangeReport
					.trueChange("Protesto realizado com sucesso, informação enviada ao banco!");
		} else {
			return StateChangeReport
					.falseChange("Cobrança ainda aguardando confirmação de registro do banco, ação não pode ser realizada!");
		}
	}

	@Override
	public StateChangeReport open() {
		if (collection.isConfirmed()) {
			StateChangeReport.getContext().getToRegisterList().add(collection);
			collection.setSituation(CollectionSituation.OPEN);
			collection.addHistoryEntry(null, new GregorianCalendar(),
					CollectionSituation.OPEN);
			collection.setConfirmed(false);
			try {
				getFacade().getRepository(Collection.class).load(collection).save();
			} catch (NamingException e) {
				e.printStackTrace();
			}
			return StateChangeReport.trueChange("Titulo reaberto com sucesso");
		} else {
			return StateChangeReport
					.falseChange("Cobrança ainda aguardando confirmação de registro do banco, ação não pode ser realizada!");
		}
	}

	@Override
	public StateChangeReport cancel() {
		return StateChangeReport.falseChange("Titulo ativo no banco, não pode ser cancelado!");
	}

	@Override
	public StateChangeReport register() {
		if (collection.isConfirmed()) {
			return StateChangeReport.falseChange("Cobrança já se encontra em aberto e registrada no banco!");
		} else {
			return StateChangeReport.falseChange("Titulo aguardando processamento do banco!");
		}
	}

}