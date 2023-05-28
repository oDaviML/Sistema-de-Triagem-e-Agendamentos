package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {

    private DBConnector(){}


    public static Connection getConexao() throws SQLException, ClassNotFoundException {
        try {
           Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://us-cdbr-east-06.cleardb.net:3306/heroku_aa28e844ee07386?autoReconnect=true&useSSL=false", "b918cc3160b707", "16d9b4b7");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
        
    public static void fecharConexao (Connection conexao, Statement comando) throws SQLException{
        if(comando != null) {
            comando.close();
        }
        if(conexao != null) {
            conexao.close();
        }
    }
}
