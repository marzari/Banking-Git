package net.kallx.banking.collection.model.state;

public class StateChangeReport {

	private static StatusChangeContext context;
	
	private boolean success;
	private String message;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
	public static StateChangeReport falseChange(String message){
		StateChangeReport ret = new StateChangeReport();
		ret.setMessage(message);
		ret.setSuccess(false);
		return ret;
	}
	
	
	public static StatusChangeContext getContext() {
		return context;
	}
	
	
	public static StateChangeReport trueChange(String message){
		StateChangeReport ret = new StateChangeReport();
		ret.setMessage(message);
		ret.setSuccess(true);
		return ret;
	}
	
}
