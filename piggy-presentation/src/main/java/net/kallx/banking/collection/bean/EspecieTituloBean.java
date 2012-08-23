package net.kallx.banking.collection.bean;

import net.kallx.architecture.control.GenericBean;
import net.kallx.architecture.repository.GenericFactory;
import net.kallx.banking.collection.model.EspecieTitulo;

public class EspecieTituloBean implements GenericBean<EspecieTitulo> {

	private EspecieTitulo model;

	private long id;
	private String code;
	private String description;

	@Override
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public void setModel(EspecieTitulo model) {
		this.model = model;
	}

	@Override
	public EspecieTitulo getModel() {
		return this.model;
	}

	@Override
	public EspecieTituloBean load(EspecieTitulo model) {
		if (model == null)
			throw new IllegalStateException();

		setId(model.getId());
		setCode(model.getCode());
		setDescription(model.getDescription());

		return this;
	}

	@Override
	public EspecieTitulo build(GenericFactory<EspecieTitulo> factory) {
		if (model == null)
			model = new EspecieTitulo();

		model.setId(getId());
		model.setCode(getCode());
		model.setDescription(getDescription());

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
		if (!(obj instanceof EspecieTituloBean)) return false;
		EspecieTituloBean that = (EspecieTituloBean) obj;
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
