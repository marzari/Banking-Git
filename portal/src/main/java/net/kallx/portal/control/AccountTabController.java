package net.kallx.portal.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

@ConversationScoped
@Named("accountTabController")
public class AccountTabController implements Serializable {

	private List<AccountTabBean> beans;
	
	public AccountTabController() {
		beans = new ArrayList<>();
	}
	
	@PostConstruct
	void init(){
		AccountTabBean bean = new AccountTabBean();
		bean.setName("Informações Pessoais");
		bean.setUrl("/WEB-INF/structure/security/account/tabs.xhtml");
		beans.add(bean);
	}
	
	
	public List<AccountTabBean> getBeans() {
		return beans;
	}
	public void setBeans(List<AccountTabBean> beans) {
		this.beans = beans;
	}
	
}
