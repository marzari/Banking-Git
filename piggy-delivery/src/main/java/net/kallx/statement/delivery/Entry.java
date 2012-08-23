package net.kallx.statement.delivery;

import java.util.Calendar;

import net.kallx.kangaroo.delivery.annotations.RField;
import net.kallx.kangaroo.delivery.annotations.RSegment;

@RSegment ("InfoMovimento")
public class Entry {
	
	@RField("SeqMovimento")
	private int seqMovimento;
	
	@RField("CodigoMovimento")
	private String codLancamento;
	
	@RField("Historico")
	private String descHistorico;
	
	@RField("NumDocMovimento")
	private String numDocumento;
	
	@RField("DataLancamento")
	private Calendar datMovimento;
	
	@RField("CategoriaMovimento")
	private int catMovimento;
	
	@RField("TipoMovimento")
	private String tipoMovimento;
	
	@RField("Valorlancamento")
	private double vlrMovimento;
	
	
	public int getSeqMovimento() {
		return seqMovimento;
	}
	public void setSeqMovimento(int seqMovimento) {
		this.seqMovimento = seqMovimento;
	}
	
	
	public String getCodLancamento() {
		return codLancamento;
	}
	public void setCodLancamento(String codLancamento) {
		this.codLancamento = codLancamento;
	}
	
	
	public String getDescHistorico() {
		return descHistorico;
	}
	public void setDescHistorico(String descHistorico) {
		this.descHistorico = descHistorico;
	}
	
	
	public String getNumDocumento() {
		return numDocumento;
	}
	public void setNumDocumento(String numDocumento) {
		this.numDocumento = numDocumento;
	}
	
	
	public Calendar getDatMovimento() {
		return datMovimento;
	}
	public void setDatMovimento(Calendar datMovimento) {
		this.datMovimento = datMovimento;
	}
	
	
	public int getCatMovimento() {
		return catMovimento;
	}
	public void setCatMovimento(int catMovimento) {
		this.catMovimento = catMovimento;
	}
	
	
	public String getTipoMovimento() {
		return tipoMovimento;
	}
	public void setTipoMovimento(String tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}
	
	
	public double getVlrMovimento() {
		return vlrMovimento;
	}
	public void setVlrMovimento(double vlrMovimento) {
		this.vlrMovimento = vlrMovimento;
	}
	
}