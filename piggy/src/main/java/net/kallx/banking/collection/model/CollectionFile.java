package net.kallx.banking.collection.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.kallx.architecture.model.GenericModel;
import net.kallx.kangaroo.document.model.LayoutInstance;
import net.kallx.kangaroo.document.model.helpers.PhysicalLayoutInstance;

@Entity
@Table(name="CollectionFile", schema="banking")
public class CollectionFile implements GenericModel {
	
	private long id;
	
	private Calendar when;
	private String content;
	private int quantity;
	
	private LayoutInstance instance;
	private PhysicalLayoutInstance physicalInstance;

	
	@Id @GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	@Column(name="cWhen")
	public Calendar getWhen() {
		return when;
	}
	public void setWhen(Calendar when) {
		this.when = when;
	}
	
	
	@Lob
	@Column(name="cContent")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	@Column(name="cQuantity")
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	@ManyToOne
	@JoinColumn(name="cLayoutInstance")
	public LayoutInstance getInstance() {
		return instance;
	}
	public void setInstance(LayoutInstance instance) {
		this.instance = instance;
	}
	
	
	@ManyToOne
	@JoinColumn(name="cPhysicalInstance")
	public PhysicalLayoutInstance getPhysicalInstance() {
		return physicalInstance;
	}
	public void setPhysicalInstance(PhysicalLayoutInstance physicalInstance) {
		this.physicalInstance = physicalInstance;
	}
	
}
