package net.kallx.portal.control;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import net.kallx.architecture.control.AbstractMenuController;
import net.kallx.architecture.control.ConversationSubject;

@ConversationScoped
@Named("contentController")
public class ContentController extends AbstractMenuController {

	@Override
	public Conversation getConversation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConversationSubject getConversationSubject() {
		// TODO Auto-generated method stub
		return null;
	}

	
}