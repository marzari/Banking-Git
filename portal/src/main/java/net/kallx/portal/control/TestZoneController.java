package net.kallx.portal.control;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.kallx.architecture.control.AbstractMenuController;
import net.kallx.architecture.control.ConversationSubject;
import net.kallx.portalManager.control.ConversationManager;

@ConversationScoped
@Named("testZoneController")
public class TestZoneController extends AbstractMenuController  {

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

}
