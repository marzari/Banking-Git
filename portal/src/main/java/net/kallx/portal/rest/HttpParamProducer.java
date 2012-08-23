package net.kallx.portal.rest;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;

@RequestScoped
public class HttpParamProducer {
 
	@Produces
	@HttpParam
	public String getHttpParameter(InjectionPoint ip) {
		String name = ip.getAnnotated().getAnnotation(HttpParam.class).value();
		System.out.println(name);
		System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name));
		if ("".equals(name))
			name = ip.getMember().getName();
		return 
			FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
	}
}