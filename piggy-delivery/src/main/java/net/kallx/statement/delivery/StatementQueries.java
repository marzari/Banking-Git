package net.kallx.statement.delivery;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;


public class StatementQueries {

	private ConnectionFactory connectionFactory;

	private PreparedStatement insertStatement;
	private PreparedStatement insertEntry;
	private ResultSet generatedKeys;
	private long ID_EXTRATO;


	public StatementQueries(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	void startStatements(Connection c) throws SQLException {

		insertStatement = c.prepareStatement("INSERT INTO FIN_EXTRATO (DAT_SALDO_INICIAL, VLR_SALDO_INICIAL, " +
											  "DAT_SALDO_FINAL, VLR_SALDO_FINAL, TOT_VLR_DEBITO, TOT_VLR_CREDITO, " +
											  "VLR_SALDO_BLOQUEADO, SEQ_EXTRATO) VALUES (?,?,?,?,?,?,?,?)", java.sql.Statement.RETURN_GENERATED_KEYS);
		
	}
	
	void startEntry(Connection c) throws SQLException {
		
		insertEntry = c.prepareStatement("INSERT INTO FIN_MOVIMENTO (SEQ_MOVIMENTO, COD_LANCAMENTO, DESC_HISTORICO, " +
																	"NUM_DOCUMENTO, DAT_MOVIMENTO, CAT_MOVIMENTO, " +
																	"TIPO_MOVIMENTO, VLR_MOVIMENTO, ID_EXTRATO) VALUES (?,?,?,?,?,?,?,?,?)");
		
	}
	public long insertStatement(Statement stm) {
		
		try (Connection c = connectionFactory.connect()) {
			
			startStatements(c);
			
			insertStatement.setDate(1, new Date(stm.getDatSaldoInicial().getTimeInMillis()));
			insertStatement.setDouble(2, stm.getVlrSaldoInicial());
			insertStatement.setDate(3, new Date(stm.getDatSaldoFinal().getTimeInMillis()));
			insertStatement.setDouble(4, stm.getVlrSaldofinal());
			insertStatement.setDouble(5, stm.getTotVlrDebito());
			insertStatement.setDouble(6, stm.getTotVlrCredito());
			insertStatement.setDouble(7, stm.getVlrSaldoBloqueado());
			insertStatement.setInt(8, stm.getSeqExtrato());
			
			insertStatement.executeUpdate();
			
			generatedKeys = insertStatement.getGeneratedKeys();
			generatedKeys.first();
			
			this.ID_EXTRATO = generatedKeys.getLong(1);

			JOptionPane.showMessageDialog(null,"O id gerado na tabela extrato é: " + ID_EXTRATO);
			return ID_EXTRATO;

		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
		
	}
	
	public void insertEntry(long statementId, Entry e) {
		
		try (Connection c = connectionFactory.connect()) {
			
			startEntry(c);
			
			insertEntry.setInt(1, e.getSeqMovimento());
			insertEntry.setString(2, e.getCodLancamento());
			insertEntry.setString(3, e.getDescHistorico());
			insertEntry.setString(4, e.getNumDocumento());
			insertEntry.setDate(5, new Date(e.getDatMovimento().getTimeInMillis()));
			insertEntry.setInt(6, e.getCatMovimento());
			insertEntry.setString(7, e.getTipoMovimento());
			insertEntry.setDouble(8, e.getVlrMovimento());
			insertEntry.setLong(9, ID_EXTRATO);
			
			insertEntry.executeUpdate();
			
		} catch (SQLException ec) {
			throw new IllegalStateException(ec);
		}
		
	}

}

	
