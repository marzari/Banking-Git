package net.kallx.banking.collection.bean;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.kallx.architecture.control.GenericBean;
import net.kallx.architecture.repository.GenericFactory;
import net.kallx.banking.collection.model.CollectionFile;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class CollectionFileBean implements GenericBean<CollectionFile> {

	private long id;
	private CollectionFile model;
	
	private String content;
	private Calendar when;
	private int quantity;
	
	private String creationFormatted;
	private String physicalName;
	private String referentialName;
	
	private StreamedContent file;
	
	
	@Override
	public long getId() {
		return id;
	}
	
	
	@Override
	public void setModel(CollectionFile model) {
		this.model = model;
	}
	@Override
	public CollectionFile getModel() {
		return model;
	}
	

	@Override
	public CollectionFileBean load(CollectionFile model) {
		
		if (model == null)
			throw new IllegalStateException();
		
		this.model = model;
		this.id = model.getId();
		
		this.quantity = model.getQuantity();
		this.when = model.getWhen();
		this.content = model.getContent();
		this.creationFormatted = new SimpleDateFormat("dd-MM-yyyy hh:mm").format(when.getTime());
		this.physicalName = model.getPhysicalInstance().getPhysicalLayout().getName();
		this.referentialName = model.getInstance().getReferentialLayout().getName();
		
		file = new DefaultStreamedContent(new ByteArrayInputStream(content.getBytes()), "text/plain", "remessa.txt");
		
		return this;
	}

	@Override
	public CollectionFile build(GenericFactory<CollectionFile> factory) {
		return null;
	}

	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	public Calendar getWhen() {
		return when;
	}
	public void setWhen(Calendar when) {
		this.when = when;
	}
	
	
	public String getReferentialName() {
		return referentialName;
	}
	public void setReferentialName(String referentialName) {
		this.referentialName = referentialName;
	}
	
	
	public String getCreationFormatted() {
		return creationFormatted;
	}
	public void setCreationFormatted(String creationFormatted) {
		this.creationFormatted = creationFormatted;
	}
	
	
	public String getPhysicalName() {
		return physicalName;
	}
	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
	}
	
	
	public StreamedContent getFile() {
		return file;
	}
	public void setFile(StreamedContent file) {
		this.file = file;
	}
}