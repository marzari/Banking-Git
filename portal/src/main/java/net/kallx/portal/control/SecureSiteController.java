package net.kallx.portal.control;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import net.kallx.architecture.control.AbstractMenuController;
import net.kallx.architecture.control.ConversationSubject;

@Named("secureSiteController")
@SessionScoped
public class SecureSiteController extends AbstractMenuController {
	
	
	@Override
	public Conversation getConversation() {
		return null;
	}
	@Override
	public ConversationSubject getConversationSubject() {
		return null;
	}
	
}
