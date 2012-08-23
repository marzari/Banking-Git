package net.kallx.statement.delivery;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

	private Connection connection;
	private String url, user, password, driver;

	public ConnectionFactory() {
		readProperties();
	}

	private ConnectionFactory readProperties() {

		Properties props = new Properties();
		try {
			FileInputStream fis = new FileInputStream(new File(
					"conf.properties"));
			props.load(fis);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return readProperties(props);
	}

	private ConnectionFactory readProperties(Properties props) {

		user = props.getProperty("usuario");
		password = props.getProperty("senha");
		url = props.getProperty("string");
		driver = props.getProperty("driver");

		return this;
	}

	// --Modificadores

	public void setConexao(Connection con) {
		this.connection = con;
	}

	// --Recuperadores
	public Connection getConnection() {
		return connection;
	}

	// --Método para coneção com o banco de dados
	public Connection connect() throws SQLException {
		
		Class<?> clazz = null;
		try {
			clazz = Class.forName(driver);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		if (clazz == null) throw new IllegalStateException();
		
		return DriverManager.getConnection(url, user, password);
		
	}


	@Override
	public String toString() {
		return "driver:" + driver + " - url: " + url + " - user: " + user
				+ " - password: " + password + " - ";
	}

}
