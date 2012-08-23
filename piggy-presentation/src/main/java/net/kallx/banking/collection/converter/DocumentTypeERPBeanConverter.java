package net.kallx.banking.collection.converter;

import java.util.HashMap;
import java.util.Map;

import javax.faces.convert.FacesConverter;

import net.kallx.architecture.control.ModelConverter;
import net.kallx.banking.collection.bean.DocumentTypeERPBean;

/**
 * 
 */
@FacesConverter("documentTypeConverter")
public class DocumentTypeERPBeanConverter extends ModelConverter<DocumentTypeERPBean> {

	private static Map<Long, DocumentTypeERPBean> mapped = new HashMap<Long, DocumentTypeERPBean>();

	@Override
	protected Map<Long, DocumentTypeERPBean> mapped() {
		return mapped;
	}

}
