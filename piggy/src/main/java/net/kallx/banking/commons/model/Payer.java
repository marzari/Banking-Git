package net.kallx.banking.commons.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.kallx.architecture.model.GenericModel;
import net.kallx.modules.register.model.Register;

@Entity
@Table(name="Sacado", schema="banking")
@NamedQueries({
	@NamedQuery(name="Payer_findByRegister", query="select x from Payer x where x.register=:register"),
	@NamedQuery(name="Payer_findByRegistration", query="select x from Payer x join x.register.registrations r where index(r) = :type and r.value = :value")
})
public class Payer implements GenericModel {
	
	public static final String findByRegister = "Payer_findByRegister";
	public static final String Payer_findByRegistration = "Payer_findByRegistration";
	
	private long id;
	private Register register;


	@Id @GeneratedValue
	@Column(name="cId")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="cRegister")
	public Register getRegister() {
		return register;
	}
	public void setRegister(Register register) {
		this.register = register;
	}
	

}
