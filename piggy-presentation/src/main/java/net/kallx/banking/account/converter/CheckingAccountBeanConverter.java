package net.kallx.banking.account.converter;

import java.util.HashMap;
import java.util.Map;

import javax.faces.convert.FacesConverter;

import net.kallx.architecture.control.ModelConverter;
import net.kallx.banking.account.bean.CheckingAccountBean;

@FacesConverter("checkingAccountConverter")
public class CheckingAccountBeanConverter extends ModelConverter<CheckingAccountBean> {
	
	private static Map<Long, CheckingAccountBean> mapped = new HashMap<Long, CheckingAccountBean>();

	@Override
	protected Map<Long, CheckingAccountBean> mapped() {
		return mapped;
	}
	
}
