package net.kallx.banking.collection.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Registry implements Serializable {

	private String name;
	private String value;

	
	public Registry() {
	}
	
	public Registry(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof Registry)) return false;
		Registry that = (Registry) obj;
		if (this.name == null ? that.name == null : this.name.equals(that.name)
				&& (this.value == null ? that.value == null : this.value.equals(that.value)))
			return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		int result = 13;
		result = result * 31 + (name != null ? name.hashCode() : 0);
		result = result * 31 + (value != null ? value.hashCode() : 0);
		return result;
	}
	
	
	
}
