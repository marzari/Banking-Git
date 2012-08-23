package net.kallx.collection.delivery;

import java.util.ArrayList;
import java.util.List;

import net.kallx.kangaroo.delivery.annotations.RLayout;
import net.kallx.kangaroo.delivery.annotations.RSegment;
import net.kallx.kangaroo.delivery.annotations.RSegments;

@RLayout("cobranca_remessa")
public class CollectionAdapterSet {

	@RSegments({
		@RSegment("cedente"),
		@RSegment("banco_cedente")
	})
	private CollectionAdapter adapter;
	
	@RSegments({
		@RSegment(value = "titulo", forClass = CollectionAdapterItem.class),
		@RSegment(value = "sacado", forClass = CollectionAdapterItem.class)
	})
	private List<CollectionAdapterItem> items;
	
	
	public CollectionAdapterSet() {
		items = new ArrayList<>();
	}
	
	
	public int getTotalItems(){
		return (items != null && !items.isEmpty() ? items.size() : 0);
	}
	
	public CollectionAdapter getAdapter() {
		return adapter;
	}
	public void setAdapter(CollectionAdapter adapter) {
		this.adapter = adapter;
	}
	
	public List<CollectionAdapterItem> getItems() {
		return items;
	}
	public void setItems(List<CollectionAdapterItem> items) {
		this.items = items;
	}
	
}
