package quickcheckmodel.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {

    private DBConnector(){}


    public static Connection getConexao() throws SQLException, ClassNotFoundException {
        try {
           Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://database-1.cty2gsaowhzj.us-east-2.rds.amazonaws.com:3306/quickcheck?zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false", "admin", "12345678");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

    public static boolean testarConexao() {
        try {
            Connection conexao = getConexao();
            if (conexao != null) {
                conexao.close();
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Erro ao testar a conexão: " + e.getMessage());
        }
        return false;
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