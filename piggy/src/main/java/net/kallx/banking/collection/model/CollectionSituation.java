package net.kallx.banking.collection.model;

public enum CollectionSituation {

	CANCELED("Título Cancelado"),
	OPEN("Título em Aberto"),
	QUITED("Baixa de Título"),
	PROTESTED("Título Protestado"),
	LIQUIDATED("Título Liquidado"),
	REGISTERED("Título Registrado");

	private String description;

	private CollectionSituation(String desc) {
		this.setDescription(desc);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

}
