package net.kallx.banking.collection.control;

import java.util.List;

import javax.faces.model.ListDataModel;

import net.kallx.banking.collection.bean.CollectionBean;

import org.primefaces.model.SelectableDataModel;

public class SelectionDataModel extends ListDataModel<CollectionBean> implements SelectableDataModel<CollectionBean> {

	public SelectionDataModel(List<CollectionBean> list) {
		super(list);
	}

	@Override
	public CollectionBean getRowData(String arg0) {
		
		List<CollectionBean> list = (List<CollectionBean>) getWrappedData();
		
		for (CollectionBean cb : list) {
			if (cb.getId() == Integer.parseInt(arg0))
				return cb;
		}
		
		return new CollectionBean();
	}

	@Override
	public Object getRowKey(CollectionBean arg0) {
		return arg0.getId();
	}

}
