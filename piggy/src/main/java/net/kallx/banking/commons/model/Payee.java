/**
 * 
 */
package net.kallx.banking.commons.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.kallx.architecture.model.GenericModel;
import net.kallx.modules.register.model.Register;

/**
 * @author Tiago
 *
 */

@Entity
@Table(name="Cedente", schema="banking")
@NamedQueries({
	@NamedQuery(name="Payee_findByRegister", query="select x from Payee x where x.register=:register"),
	@NamedQuery(name="Payee_findByName", query="select x from Payee x where x.register.companyName=:name"),
	@NamedQuery(name="Payee_findByRegistration", query="select x from Payee x join x.register.registrations r where index(r) = :type and r.value = :value")
})
public class Payee implements GenericModel {
	
	public static final String findByRegister = "Payee_findByRegister";
	public static final String Payee_findByRegistration = "Payee_findByRegistration";
	
	private long id;
	private Register register;
	
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="cRegister")
	public Register getRegister() {
		return register;
	}
	public void setRegister(Register register) {
		this.register = register;
	}

	@Id @GeneratedValue
	@Column(name="cId")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	

}
