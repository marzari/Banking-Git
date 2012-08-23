/**
 * 
 */
package net.kallx.banking.commons.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import net.kallx.architecture.model.GenericModel;

/**
 * Entity that represents Discounts as described in document
 * "FEBRABAN - CNAB240" C021 of Collection.
 * 
 * @author Rodrigo H. Almeida
 * 
 */

@Entity
@Table(name = "DiscountType", schema = "banking")
public class DiscountType implements GenericModel {

	/**
	 * id do banco
	 */
	private long id;
	private String code;
	private String description;

	@Id
	@GeneratedValue
	@Column(name = "cId")
	public long getId() {
		return id;
	}

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
