package net.kallx.portal.control;

import java.util.Map;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.kallx.architecture.control.AbstractMenuController;
import net.kallx.architecture.control.ConversationSubject;
import net.kallx.architecture.control.SessionHistoryManager;
import net.kallx.portalManager.control.ConversationManager;
import net.kallx.security.control.SecureSessionController;
import net.kallx.security.model.User;
import net.kallx.security.service.LoggedUser;

import org.primefaces.model.MenuModel;

@ConversationScoped
@Named("applicationMenuController")
public class ApplicationMenuController extends AbstractMenuController  {
	
	private MenuModel applicationModel;

//	@Inject
//	@LoggedUser
	private User user;
	
	@Inject
	private SecureSessionController sessionController;
	
	
	public MenuModel getApplicationModel() {
		return applicationModel;
	}
	public void setApplicationModel(MenuModel applicationModel) {
		this.applicationModel = applicationModel;
	}

	
	
	@Override
	public void load(String name) {
		super.load(name);
	}

	@Inject
	private Conversation conversation;
	
	@Inject
	private ConversationManager conversationManager;


	@Override
	public Conversation getConversation() {
		return conversation;
	}

	@Override
	public ConversationSubject getConversationSubject() {
		return conversationManager;
	}
	
	
	public void afterBegin() {
		
		super.afterBegin();
		SessionHistoryManager historyManager = getConversationSubject().getHistoryManager();
		Map<String, String> content = historyManager.getContent(getClass());
		
		if (content == null) return;
		if (content.containsKey("selectedMenu"))
			name = content.get("selectedMenu");
		
	}
	
	public void beforeEnd() {
		SessionHistoryManager historyManager = getConversationSubject().getHistoryManager();
		historyManager.register(getClass(), "selectedMenu", name);
		super.beforeEnd();
	}
	
}