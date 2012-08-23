package net.kallx.banking.collection.bean;

import net.kallx.architecture.control.GenericBean;
import net.kallx.architecture.repository.GenericFactory;
import net.kallx.banking.collection.model.ReceiptInstruction;

public class ReceiptInstructionBean implements GenericBean<ReceiptInstruction> {

	private long id;
	private ReceiptInstruction model;
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
	public void setModel(ReceiptInstruction model) {
		this.model = model;
	}

	@Override
	public ReceiptInstruction getModel() {
		return this.model;
	}

	@Override
	public GenericBean<ReceiptInstruction> load(ReceiptInstruction model) {
		if (model == null)
			throw new IllegalStateException();

		setCode(model.getCode());
		setDescription(model.getDescription());
		setId(model.getId());

		return this;
	}

	@Override
	public ReceiptInstruction build(GenericFactory<ReceiptInstruction> factory) {
		if (factory == null)
			model = new ReceiptInstruction();

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
		if (!(obj instanceof ReceiptInstructionBean)) return false;
		ReceiptInstructionBean that = (ReceiptInstructionBean) obj;
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
