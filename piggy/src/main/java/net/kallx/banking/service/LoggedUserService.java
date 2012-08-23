package net.kallx.banking.service;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

import net.kallx.security.model.User;
import net.kallx.security.service.LoggedUser;

public class LoggedUserService  {

	private SessionContext context;
	
	public User getLoggedUser(){
		Object object = context.getContextData().get("loggedUser");
		if (object == null)
			return null;
		return (User) object;
	}
	
}
