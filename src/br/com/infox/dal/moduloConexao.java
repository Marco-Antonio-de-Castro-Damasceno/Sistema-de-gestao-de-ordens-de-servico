package br.com.infox.dal;

import java.sql.*;

public class moduloConexao {
	public static Connection conector() {
		Connection conexao = null;
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/dbinfox?characterEncoding=utf-8";
		String user = "DBA";
		String password = "Infox@123456";
		try {
			Class.forName(driver);
			conexao = DriverManager.getConnection(url, user, password);
			return conexao;
		} catch (Exception e) {
			System.out.println(e);
			return null;
			// TODO: handle exception
		}
	}
}
