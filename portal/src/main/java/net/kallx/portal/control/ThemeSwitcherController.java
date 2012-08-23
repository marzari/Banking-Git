package net.kallx.portal.control;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

@ConversationScoped
@Named("themeSwitcherController")
public class ThemeSwitcherController implements Serializable {

	private Map<String, String> themes;

	@PostConstruct
	void init() {

		themes = new TreeMap<String, String>();
		themes.put("Cupertino", "cupertino");
		themes.put("E-Sales Default", "esales-theme-default");
		themes.put("Humanity", "humanity");
		themes.put("Redmond", "redmond");
		themes.put("Smoothness", "smoothness");
		themes.put("Sunny", "sunny");
		themes.put("UI-Lightness", "ui-lightness");
		themes.put("Vader", "vader");

	}
	
	public Map<String, String> getThemes() {
		return themes;
	}
	public void setThemes(Map<String, String> themes) {
		this.themes = themes;
	}

}
