package net.kallx.banking.collection.bean;

import net.kallx.architecture.control.GenericBean;
import net.kallx.architecture.repository.GenericFactory;
import net.kallx.banking.commons.model.DiscountType;

public class DiscountTypeBean implements GenericBean<DiscountType> {

	private long id;
	private DiscountType model;
	private String code;
	private String description;

	@Override
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public void setModel(DiscountType model) {
		this.model = model;
	}

	@Override
	public DiscountType getModel() {
		return this.model;
	}

	@Override
	public GenericBean<DiscountType> load(DiscountType model) {
		if (model == null)
			throw new IllegalStateException();

		setCode(model.getCode());
		setDescription(model.getDescription());
		setId(model.getId());

		return this;
	}

	@Override
	public DiscountType build(GenericFactory<DiscountType> factory) {
		if (factory == null)
			model = new DiscountType();

		model.setId(this.id);
		model.setDescription(getDescription());
		model.setCode(getCode());

		return model;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof DiscountTypeBean)) return false;
		DiscountTypeBean that = (DiscountTypeBean) obj;
		if (id > 0)
			return this.id == that.id;
		else 
			if (this.code == null ? that.code == null : this.code.equals(that.code))
			return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		if (this.id > 0)
			result = result * 31 + (int)(id^(id>>>32));
		else 
			result = result * 31 + (code != null ? code.hashCode() : 0);
		
		return result;
	}
}
