/**
 * 
 */
package net.kallx.banking.collection.bean;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import net.kallx.banking.collection.model.Collection;
import net.kallx.banking.collection.model.Registry;

/**
 * @author Tiago Felipe
 * 
 */
public class CollectionFilterBean {

	private Date initialDateGenerate;
	private Date finalDateGenerate;
	private Date initialDateMaturity;
	private Date finalDateMaturity;
	private String selectEspecie;
	private Calendar calendarInitialDateGenerate;
	private Calendar calendarfinalDateGenerate;
	private Calendar calendarInitialDateMaturity;
	private Calendar calendarFinalDateMaturity;

	public String getSelectEspecie() {
		return selectEspecie;
	}

	public void setSelectEspecie(String selectEspecie) {
		this.selectEspecie = selectEspecie;
	}

	public Date getInitialDateGenerate() {
		return initialDateGenerate;
	}

	public void setInitialDateGenerate(Date initialDateGenerate) {
		this.initialDateGenerate = initialDateGenerate;
	}

	public Date getFinalDateGenerate() {
		return finalDateGenerate;
	}

	public void setFinalDateGenerate(Date finalDateGenerate) {
		this.finalDateGenerate = finalDateGenerate;
	}

	public Date getFinalDateMaturity() {
		return finalDateMaturity;
	}

	public void setFinalDateMaturity(Date finalDateMaturity) {
		this.finalDateMaturity = finalDateMaturity;
	}

	public Date getInitialDateMaturity() {
		return initialDateMaturity;
	}

	public void setInitialDateMaturity(Date initialDateMaturity) {
		this.initialDateMaturity = initialDateMaturity;
	}

	public Calendar getCalendarInitialDateGenerate() {
		if (getInitialDateGenerate() == null)
			return null;
		calendarInitialDateGenerate = converterDateToCalendar(getInitialDateGenerate());
		return calendarInitialDateGenerate;
	}

	public Calendar getCalendarFinalDateGenerate() {
		if (getFinalDateGenerate() == null)
			return null;
		calendarfinalDateGenerate = converterDateToCalendar(getFinalDateGenerate());
		return calendarfinalDateGenerate;
	}

	public Calendar getCalendarFinalDateMaturity() {
		if (getFinalDateMaturity() == null)
			return null;
		calendarFinalDateMaturity = converterDateToCalendar(getFinalDateMaturity());
		return calendarFinalDateMaturity;
	}

	public Calendar getCalendarInitialDateMaturity() {
		if (getInitialDateMaturity() == null)
			return null;
		calendarInitialDateMaturity = converterDateToCalendar(getInitialDateMaturity());
		return calendarInitialDateMaturity;
	}

	public Calendar converterDateToCalendar(Date date) {

		Calendar c = new GregorianCalendar();
		c.setTime(date);
		return c;

	}

	public String getQueryName() {
		System.out.println(selectEspecie);
		System.out.println(initialDateGenerate);
		System.out.println(finalDateGenerate);
		System.out.println(initialDateMaturity);
		System.out.println(finalDateMaturity);
		
		// não informado nada no filtro, retorna todos os registros do banco
		if ((selectEspecie == null || selectEspecie.length() < 1)
				&& (initialDateMaturity == null && finalDateMaturity == null)
				&& (initialDateGenerate == null && finalDateGenerate == null))

			return Collection.filterAll;

		// informado somente data de geraçao
		else if ((selectEspecie == null || selectEspecie.length() < 1)
				&& (initialDateMaturity == null && finalDateMaturity == null)
				&& (initialDateGenerate != null && finalDateGenerate != null))

			return Collection.filterByIssue;

		// informado somente a data de vencimento
		else if ((selectEspecie == null || selectEspecie.length() < 1)
				&& (initialDateGenerate == null && finalDateGenerate == null)
				&& (initialDateMaturity != null && finalDateMaturity != null))

			return Collection.filterByMaturity;

		// informado somente especie
		else if ((selectEspecie == null || selectEspecie.length() >= 1)
				&& (initialDateGenerate == null && finalDateGenerate == null)
				&& (initialDateMaturity == null && finalDateMaturity == null))

			return Collection.filterByEspecie;

		// informado data de vencimento e data de geração
		else if ((selectEspecie == null || selectEspecie.length() < 1)
				&& (initialDateGenerate != null && finalDateGenerate != null)
				&& (initialDateMaturity != null && finalDateMaturity != null))

			return Collection.filterByMaturityAndIssue;

		// informado especie e data vencimento
		else if ((selectEspecie == null || selectEspecie.length() >= 1)
				&& (initialDateMaturity != null && finalDateMaturity != null)
				&& (initialDateGenerate == null && finalDateGenerate == null))

			return Collection.filterByMaturityAndEspecie;

		// informado especie e data de geracao
		else if ((selectEspecie == null || selectEspecie.length() >= 1)
				&& (initialDateGenerate != null && finalDateGenerate != null)
				&& (initialDateMaturity == null && finalDateMaturity == null))

			return Collection.filterByIssueAndEspecie;

		// informado todos os campos do filtro
		else if ((selectEspecie == null || selectEspecie.length() >= 1)
				&& (initialDateMaturity != null && finalDateMaturity != null)
				&& (initialDateGenerate != null && finalDateGenerate != null))

			return Collection.filterByIssueMaturityAndEspecie;

		else

			return "se esse resultado saiu é porque nenhuma condição do if foi satisfeita";

	}

	public Registry getObjectEspecie() {
		return new Registry("especie", selectEspecie);
	}
}
