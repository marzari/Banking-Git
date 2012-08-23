package net.kallx.portal.control;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import net.kallx.security.facade.SecurityModuleFacade;
import net.kallx.security.model.User;
import net.kallx.security.model.UserActivation;
import net.kallx.security.model.UserStatus;

@Named("activation")
@RequestScoped
public class ActivationController {

	@EJB
	private SecurityModuleFacade facade;
	
	private String key;
	private boolean valid;
	
	@PostConstruct
	void init(){
		
		String key = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("key");
		UserActivation ea = facade.getRepository(UserActivation.class).namedQuery(UserActivation.findByKey, UserActivation.class, "key", key);
		if (ea != null && ea.getUser() != null){
			ea.getUser().setStatus(UserStatus.Active);
			facade.getRepository(User.class).load(ea.getUser()).save();
			valid = true;
		}
			
		
	}
	
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
}
