package net.kallx.banking.collection.converter;

import java.util.HashMap;
import java.util.Map;

import javax.faces.convert.FacesConverter;

import net.kallx.architecture.control.ModelConverter;
import net.kallx.banking.collection.bean.ReceiptInstructionBean;

/**
 * 
 */
@FacesConverter("receiptInstructionConverter")
public class ReceiptInstructionConverter extends ModelConverter<ReceiptInstructionBean> {

	private static Map<Long, ReceiptInstructionBean> mapped = new HashMap<Long, ReceiptInstructionBean>();

	@Override
	protected Map<Long, ReceiptInstructionBean> mapped() {
		return mapped;
	}

}
