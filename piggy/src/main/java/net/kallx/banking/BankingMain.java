package net.kallx.banking;

import java.io.Serializable;
import java.util.concurrent.ThreadFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import net.kallx.applications.control.PortalManagerMain;

@Stateless
public class BankingMain implements Serializable {

	private ThreadFactory factory;
	
	private PortalManagerMain portalManager;
	
	@PostConstruct
	void prepare(){
		
		System.out.println(portalManager);
		
		Thread.currentThread().getContextClassLoader().getResource("anyBanking.xml");
		
	}
	
}