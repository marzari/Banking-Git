package net.kallx.banking.collection.converter;

import java.util.HashMap;
import java.util.Map;

import javax.faces.convert.FacesConverter;

import net.kallx.architecture.control.ModelConverter;
import net.kallx.banking.collection.bean.CollectionAccountBean;

/**
 * 
 */
@FacesConverter("collectionAccountConverter")
public class CollectionAccountBeanConverter extends ModelConverter<CollectionAccountBean> {

	private static Map<Long, CollectionAccountBean> mapped = new HashMap<Long, CollectionAccountBean>();

	@Override
	protected Map<Long, CollectionAccountBean> mapped() {
		return mapped;
	}

}
