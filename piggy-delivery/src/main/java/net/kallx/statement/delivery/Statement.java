package net.kallx.statement.delivery;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.kallx.kangaroo.delivery.annotations.RField;
import net.kallx.kangaroo.delivery.annotations.RLayout;
import net.kallx.kangaroo.delivery.annotations.RSegment;

@RLayout("extrato")
public class Statement {
	
	@RField("InfoSaldoInicial.DataSaldoInicial")
	private Calendar datSaldoInicial;
	
	@RField("InfoSaldoInicial.SaldoInicial")
	private double vlrSaldoInicial;
	
	@RField("InfoSaldoAtual.DataSaldoAtual")
	private Calendar datSaldoFinal;
	
	@RField("InfoSaldoAtual.SaldoAtual")
	private double vlrSaldofinal;
	
	@RField("InfoSaldoAtual.TotalDebito")
	private double totVlrDebito;
	
	@RField("InfoSaldoAtual.TotalCredito")
	private double totVlrCredito;
	
	@RField("InfoSaldoAtual.TotalBloqueado")
	private double vlrSaldoBloqueado;
	
	@RField("InfoExtato.SeqExtrato")
	private int seqExtrato;
	
	@RSegment(value="InfoMovimento", forClass=Entry.class)
	private List<Entry> movimentos;
	
	
	public Statement() {
		movimentos = new ArrayList<>();
	}
	
	
	public List<Entry> getMovimentos() {
		return movimentos;
	}
	public void setMovimentos(List<Entry> movimentos) {
		this.movimentos = movimentos;
	}
	
	
	public Calendar getDatSaldoInicial() {
		return datSaldoInicial;
	}
	public void setDatSaldoInicial(Calendar datSaldoInicial) {
		this.datSaldoInicial = datSaldoInicial;
	}
	
	
	public double getVlrSaldoInicial() {
		return vlrSaldoInicial;
	}
	public void setVlrSaldoInicial(double vlrSaldoInicial) {
		this.vlrSaldoInicial = vlrSaldoInicial;
	}
	
	
	public Calendar getDatSaldoFinal() {
		return datSaldoFinal;
	}
	public void setDatSaldoFinal(Calendar datSaldoFinal) {
		this.datSaldoFinal = datSaldoFinal;
	}
	
	
	public double getVlrSaldofinal() {
		return vlrSaldofinal;
	}
	public void setVlrSaldofinal(double vlrSaldofinal) {
		this.vlrSaldofinal = vlrSaldofinal;
	}
	
	
	public double getTotVlrDebito() {
		return totVlrDebito;
	}
	public void setTotVlrDebito(double totVlrDebito) {
		this.totVlrDebito = totVlrDebito;
	}
	
	
	public double getTotVlrCredito() {
		return totVlrCredito;
	}
	public void setTotVlrCredito(double totVlrCredito) {
		this.totVlrCredito = totVlrCredito;
	}
	
	
	public double getVlrSaldoBloqueado() {
		return vlrSaldoBloqueado;
	}
	public void setVlrSaldoBloqueado(double vlrSaldoBloqueado) {
		this.vlrSaldoBloqueado = vlrSaldoBloqueado;
	}
	
	
	public int getSeqExtrato() {
		return seqExtrato;
	}
	public void setSeqExtrato(int seqExtrato) {
		this.seqExtrato = seqExtrato;
	}
	

}
