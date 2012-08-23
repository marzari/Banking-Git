/**
 * 
 */
package net.kallx.banking.collection.model;

import java.util.Calendar;

/**
 * @author Tiago Felipe			
 *
 */
public class CobTituloEvento {
	
	private long id;
	private Calendar dataEvento;
	private String descricaoEvento;
	private Collection b;
	private BcoCobEvento e;
	private long idUsuario;
	
	
	//getters
	public long getId() {
		return id;
	}
	public Calendar getDataEvento() {
		return dataEvento;
	}
	public String getDescricaoEvento() {
		return descricaoEvento;
	}
	public Collection getB() {
		return b;
	}
	public BcoCobEvento getE() {
		return e;
	}
	public long getIdUsuario() {
		return idUsuario;
	}
	
	//setters
	public void setId(long id) {
		this.id = id;
	}
	public void setDataEvento(Calendar dataEvento) {
		this.dataEvento = dataEvento;
	}
	public void setDescricaoEvento(String descricaoEvento) {
		this.descricaoEvento = descricaoEvento;
	}
	public void setB(Collection b) {
		this.b = b;
	}
	public void setE(BcoCobEvento e) {
		this.e = e;
	}
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	
	

}
