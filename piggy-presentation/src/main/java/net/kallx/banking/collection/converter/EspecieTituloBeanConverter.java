package net.kallx.banking.collection.converter;

import java.util.HashMap;
import java.util.Map;

import javax.faces.convert.FacesConverter;

import net.kallx.architecture.control.ModelConverter;
import net.kallx.banking.collection.bean.EspecieTituloBean;

/**
 * 
 */
@FacesConverter("especieTituloConverter")
public class EspecieTituloBeanConverter extends ModelConverter<EspecieTituloBean> {

	private static Map<Long, EspecieTituloBean> mapped = new HashMap<Long, EspecieTituloBean>();

	@Override
	protected Map<Long, EspecieTituloBean> mapped() {
		return mapped;
	}

}
