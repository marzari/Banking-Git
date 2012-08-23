package net.kallx.banking.collection.converter;

import java.util.HashMap;
import java.util.Map;

import javax.faces.convert.FacesConverter;

import net.kallx.architecture.control.ModelConverter;
import net.kallx.banking.collection.bean.PortfolioBean;

/**
 * 
 */
@FacesConverter("portfolioConverter")
public class PortfolioBeanConverter extends ModelConverter<PortfolioBean> {

	private static Map<Long, PortfolioBean> mapped = new HashMap<Long, PortfolioBean>();

	@Override
	protected Map<Long, PortfolioBean> mapped() {
		return mapped;
	}

}
