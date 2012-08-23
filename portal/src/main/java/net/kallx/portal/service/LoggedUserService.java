package net.kallx.portal.service;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

import net.kallx.security.model.User;
import net.kallx.security.service.LoggedUser;

@Any
@Default
public class LoggedUserService  {

	@Resource
	private SessionContext context;
	
	@Produces @SessionScoped
	@LoggedUser
	public User getLoggedUser(){
		Object object = context.getContextData().get("loggedUser");
		if (object == null)
			return null;
		return (User) object;
	}
	
}
