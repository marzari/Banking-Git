package net.kallx.portal.exception.handler;

import java.util.Iterator;

import javax.ejb.EJBException;
import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;

import net.kallx.architecture.exception.ResultNotFoundException;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KallxExceptionHandler extends ExceptionHandlerWrapper {

	private static final Logger LOG = LoggerFactory.getLogger("net.kallx");

	private final javax.faces.context.ExceptionHandler wrapped;

	public KallxExceptionHandler(final javax.faces.context.ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public javax.faces.context.ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle() throws FacesException {
		// final FacesContext facesContext = FacesContext.getCurrentInstance();
		// final ExternalContext externalContext = facesContext.getExternalContext();
		// final Map<String, Object> requestMap = externalContext.getRequestMap();

		for (final Iterator<ExceptionQueuedEvent> it = getUnhandledExceptionQueuedEvents().iterator(); it.hasNext();) {
			Throwable t = it.next().getContext().getException();
			while ((t instanceof FacesException || t instanceof EJBException || t instanceof ELException)
					&& t.getCause() != null) {
				t = t.getCause();
			}

			try {
				if (t instanceof FacesException) {
					logMessage(t, "Faces exception ");
				} else if (t instanceof EJBException) {
					logMessage(t, "EJB exception ");
				} else if (t instanceof ELException) {
					logMessage(t, "EL exception ");
				} else {
					if (t instanceof PersistenceException) {
						if (t instanceof ResultNotFoundException || t instanceof NoResultException) {
							addError(t.getMessage());
						} else if (t instanceof NonUniqueResultException) {
							addError(t.getMessage());
						} else if (t instanceof ConstraintViolationException) {
							addError(t.getMessage());
						} else if (t instanceof EntityNotFoundException) {
							addError(t.getMessage());
						}
					} else if (t instanceof HibernateException) {
						if (t instanceof ConstraintViolationException) {
							addError(t.getMessage());
						}
					} else {
						logMessage(t, "Generic exception ");
					}
				}

				// sendToErrorPage(t, facesContext, externalContext,
				// requestMap);
			} finally {
				it.remove();
			}
		}
		getWrapped().handle();
	}

	private void logMessage(Throwable t, String exceptionSort) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(exceptionSort, t);
		} else if (LOG.isInfoEnabled()) {
			LOG.info(exceptionSort + " {}: {}", t.getClass().getSimpleName(), t.getMessage());
		}
	}

	public void addInfo(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", message));
	}

	public void addWarn(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", message));
	}

	public void addError(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", message));
	}

	public void addFatal(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro Fatal", message));
	}

	// private void sendToErrorPage(Throwable t, final FacesContext
	// facesContext, final ExternalContext externalContext,
	// final Map<String, Object> requestMap) {
	// String message;
	// if (t instanceof ViewExpiredException) {
	// final String viewId = ((ViewExpiredException) t).getViewId();
	// message = "View is expired. <a href='/ifos" + viewId + "'>Back</a>";
	// } else {
	// message = t.getMessage(); // beware, don't leak internal
	// // info!
	// }
	//
	// requestMap.put("errorMsg", message);
	// try {
	// externalContext.dispatch("/error.jsp");
	// } catch (final IOException e) {
	// LOG.error("Error view '/error.jsp' unknown!", e);
	// }
	// facesContext.responseComplete();
	// }
}