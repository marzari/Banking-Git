package net.kallx.banking.collection.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.kallx.security.model.User;

@Table(schema = "banking")
@Entity
public class CollectionHistory implements Serializable {

	private Collection collection;
	private Calendar when;
	private User user;
	private CollectionSituation situation;
	private long id;

	@Id
	@GeneratedValue
	@Column(name = "cId")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "cCollection")
	public Collection getCollection() {
		return collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dIssue")
	public Calendar getWhen() {
		return when;
	}

	public void setWhen(Calendar when) {
		this.when = when;
	}

	@ManyToOne
	@JoinColumn(name = "cUser")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "cSituation")
	public CollectionSituation getSituation() {
		return situation;
	}

	public void setSituation(CollectionSituation situation) {
		this.situation = situation;
	}
}
