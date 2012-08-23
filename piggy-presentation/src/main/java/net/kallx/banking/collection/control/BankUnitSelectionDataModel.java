package net.kallx.banking.collection.control;

import java.util.List;

import javax.faces.model.ListDataModel;

import net.kallx.banking.bank.bean.BankUnitBean;

import org.primefaces.model.SelectableDataModel;

public class BankUnitSelectionDataModel extends ListDataModel<BankUnitBean> implements SelectableDataModel<BankUnitBean> {

	public BankUnitSelectionDataModel(List<BankUnitBean> list) {
		super(list);
	}

	@Override
	public BankUnitBean getRowData(String arg0) {
		
		List<BankUnitBean> list = (List<BankUnitBean>) getWrappedData();
		
		for (BankUnitBean cb : list) {
			if (cb.getId() == Integer.parseInt(arg0))
				return cb;
		}
		
		return new BankUnitBean();
	}

	@Override
	public Object getRowKey(BankUnitBean arg0) {
		return arg0.getId();
	}
}
