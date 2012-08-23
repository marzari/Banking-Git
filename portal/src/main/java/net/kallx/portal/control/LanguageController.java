package net.kallx.portal.control;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named("languageController")
public class LanguageController implements Serializable {

	private String language;
	
	public LanguageController() {
		language = Locale.getDefault().toString();
	}
	
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	// TODO para nao onerar memória, pensar se no futuro nao eh melhor gerenciar isto pelo SessionController
	
}
