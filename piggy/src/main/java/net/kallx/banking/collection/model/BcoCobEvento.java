/**
 * 
 */
package net.kallx.banking.collection.model;

/**
 * @author Tiago Felipe
 *
 */
public class BcoCobEvento {
	
	private long id;
	private String descricaoEvento;
	private String status;
	
	public long getId() {
		return id;
	}
	public String getDescricaoEvento() {
		return descricaoEvento;
	}
	public String getStatus() {
		return status;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setDescricaoEvento(String descricaoEvento) {
		this.descricaoEvento = descricaoEvento;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
