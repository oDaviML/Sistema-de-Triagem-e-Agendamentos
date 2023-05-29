package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {

    private DBConnector(){}

    // Strings com informações para conexão ao BD
    private static final String URL = "jdbc:mysql://us-cdbr-east-06.cleardb.net:3306/heroku_aa28e844ee07386?autoReconnect=true&useSSL=false";
    private static final String USUARIO = "b918cc3160b707";
    private static final String SENHA = "16d9b4b7";
    
    // Tenta estabelecer conexão com o banco de dados.
    public static Connection getConexao() throws SQLException, ClassNotFoundException {
        try {
           Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
    
    // Fecha conexão com o banco de dados imediatamente, ao invés de esperar o conector realizar a tarefa.
    public static void fecharConexao (Connection conexao, Statement comando) throws SQLException{
        if(comando != null) {
            comando.close();
        }
        if(conexao != null) {
            conexao.close();
        }
    }
}
