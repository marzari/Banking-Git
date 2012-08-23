package net.kallx.statement.delivery;

import java.util.GregorianCalendar;

public class TestarConexao {
	public static void main(String[] args) {

		try {

			Statement s = new Statement();
			s.setDatSaldoInicial(new GregorianCalendar(2012, 1, 28));
			s.setVlrSaldoInicial(15.45);
			s.setDatSaldoFinal(new GregorianCalendar(2012, 1, 29));
			s.setVlrSaldofinal(18.78);
			s.setTotVlrDebito(45.45);
			s.setTotVlrCredito(89.78);
			s.setVlrSaldoBloqueado(12.03);
			s.setSeqExtrato(1);

			Entry ea = new Entry();
			ea.setCatMovimento(5);
			ea.setCodLancamento("616874687");
			ea.setDatMovimento(new GregorianCalendar(2012, 1, 10));
			ea.setDescHistorico("Este é um exemplo de histórico");
			ea.setNumDocumento("168416846");
			ea.setSeqMovimento(1);
			ea.setTipoMovimento("Este é um exemplo de tipo de movimento");
			ea.setVlrMovimento(15.56);

			Entry eb = new Entry();
			eb.setCatMovimento(4);
			eb.setCodLancamento("16874165486846");
			eb.setDatMovimento(new GregorianCalendar(2012, 1, 16));
			eb.setDescHistorico("Este é um exemplo de outro histórico");
			eb.setNumDocumento("35648955");
			eb.setSeqMovimento(2);
			eb.setTipoMovimento("Este é um exemplo de outro tipo de movimento");
			eb.setVlrMovimento(78.32);

			s.getMovimentos().add(ea);
			s.getMovimentos().add(eb);

			StatementProcessor sp = new StatementProcessor();
			sp.process(s);
		} finally {
		}
		// boolean conectou;
		// ConnectionFactory c = new ConnectionFactory();
		//
		// conectou = c.connect();
		// if(conectou)
		// c.Fechar();
	}

}
