package net.kallx.statement.delivery;

import java.util.List;

public class StatementProcessor {

	public void process(Statement s) {

		ConnectionFactory f = new ConnectionFactory();
		StatementQueries q = new StatementQueries(f);
		
		long id = q.insertStatement(s);
		List<Entry> movimentos = s.getMovimentos();
		for (Entry entry : movimentos) {
			q.insertEntry(id, entry);
		}
		
	}
	
}
