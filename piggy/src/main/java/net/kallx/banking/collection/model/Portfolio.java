package net.kallx.banking.collection.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import net.kallx.architecture.model.GenericModel;

@Entity
@Table(name = "portfolio", schema = "banking")
public class Portfolio implements GenericModel {

	private long id;
	private String code;
	private String description;

	@Override
	@Id
	@GeneratedValue
	@Column(name = "cId")
	public long getId() {
		return this.id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "cCode")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "cDescription")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
