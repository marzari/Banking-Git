package net.kallx.banking.bank.converter;

import java.util.HashMap;
import java.util.Map;

import javax.faces.convert.FacesConverter;

import net.kallx.architecture.control.ModelConverter;
import net.kallx.banking.bank.bean.BankBean;

/**
 * 
 */
@FacesConverter("bankConverter")
public class BankBeanConverter extends ModelConverter<BankBean> {

	private static Map<Long, BankBean> mapped = new HashMap<Long, BankBean>();

	@Override
	protected Map<Long, BankBean> mapped() {
		return mapped;
	}

}
