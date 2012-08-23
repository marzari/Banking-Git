package net.kallx.banking.collection.service;

import java.util.List;

import javax.ejb.Stateless;

import net.kallx.banking.collection.model.CollectionAccount;

@Stateless
public class CollectionService {

	public void protest(List<Long> ids){
		System.out.println("estou protestando este titulo");
		
	}
	
	public void cancel(List<Long> ids){
		System.out.println("estou cancelando titulos");
	}
	
	public void changeAccount(CollectionAccount account, List<Long> ids){
		System.out.println("alterando a cobrança");
	}
	
	public void generateFile(List<Long> ids){
		System.out.println("estou Gerando arquivos");
	}
	
}
