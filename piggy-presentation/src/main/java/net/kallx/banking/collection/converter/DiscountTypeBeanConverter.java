package net.kallx.banking.collection.converter;

import java.util.HashMap;
import java.util.Map;

import javax.faces.convert.FacesConverter;

import net.kallx.architecture.control.ModelConverter;
import net.kallx.banking.collection.bean.DiscountTypeBean;

/**
 * 
 */
@FacesConverter("discountTypeConverter")
public class DiscountTypeBeanConverter extends ModelConverter<DiscountTypeBean> {

	private static Map<Long, DiscountTypeBean> mapped = new HashMap<Long, DiscountTypeBean>();

	@Override
	protected Map<Long, DiscountTypeBean> mapped() {
		return mapped;
	}

}
