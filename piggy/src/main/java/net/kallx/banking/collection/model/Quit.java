/**
 * 
 */
package net.kallx.banking.collection.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Tiago
 * 
 *
 */

@Entity
@Table(name="Quit", schema="banking")
public class Quit implements Serializable {
	
	private int term;
	private String code;
	private long id;
	
	@Id @GeneratedValue
	@Column(name="cId")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	@Column(name="cCode")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	@Column(name="cTerm")
	public int getTerm() {
		return term;
	}
	public void setTerm(int term) {
		this.term = term;
	}

}
