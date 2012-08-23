package net.kallx.banking;

import javax.el.MethodExpression;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.MethodExpressionActionListener;
import javax.inject.Inject;
import javax.inject.Named;

import net.kallx.architecture.control.AbstractMenuController;
import net.kallx.architecture.control.ConversationSubject;
import net.kallx.portalManager.control.ConversationManager;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.MenuModel;

/**
 * @author Marcos Vinicius
 *
 */
@Named("bankingMenu")
@ConversationScoped
public class BankingMenuController extends AbstractMenuController {
	
	private MenuModel applicationModel;
	
	private MenuItem newMenuItem(String label, String update, String actionListener) {

		MenuItem newMenuItem = new MenuItem();

		FacesContext context = FacesContext.getCurrentInstance();
		
		if (actionListener != null) {
			MethodExpression methodExpression0 = context
					.getApplication()
					.getExpressionFactory()
					.createMethodExpression(context.getELContext(),
							actionListener, null,
							new Class<?>[] { String.class });
			newMenuItem.addActionListener(new MethodExpressionActionListener(
					methodExpression0));
		}

		newMenuItem.setValue(label);
		newMenuItem.setUpdate(update);

		return newMenuItem;
	}
	
	
	private Submenu newSubMenu(String label) {

		Submenu newSubMenu = new Submenu();
		newSubMenu.setLabel(label);
		return newSubMenu;
	}
	
	
	public MenuModel getApplicationModel() {
		return applicationModel;
	}
	public void setApplicationModel(MenuModel applicationModel) {
		this.applicationModel = applicationModel;
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
	
}
