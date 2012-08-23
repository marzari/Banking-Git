package net.kallx.portal.exception.handler;

import javax.faces.context.ExceptionHandler;

public class KallxExceptionHandlerFactory extends
		javax.faces.context.ExceptionHandlerFactory {

	private final javax.faces.context.ExceptionHandlerFactory parent;

	public KallxExceptionHandlerFactory(
			final javax.faces.context.ExceptionHandlerFactory parent) {
		this.parent = parent;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		return new KallxExceptionHandler(this.parent.getExceptionHandler()); 
	}

}
