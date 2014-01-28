package model.services;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {

	// In�cio Singleton
	private ConnectionManager() {
	}

	private static ConnectionManager instance = null;

	public static ConnectionManager getInstance() {
		if (instance == null) {
			instance = new ConnectionManager();
		}
		return (instance);
	}

	// Termino Singleton

	// Atributos

	private String url = "jdbc:postgresql://localhost:5432/terraplanagem";
	private String username = "postgres";
	private String password = "postgres";
	private String driver = "org.postgresql.Driver";

	// Metodos de servico

	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			System.out.println("N�o foi possivel conectar ao bd!");
		}
		return (conn);
	}
}
