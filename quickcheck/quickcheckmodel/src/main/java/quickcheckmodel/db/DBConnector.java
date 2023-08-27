package quickcheckmodel.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {
    /*
     * Nesta implementação, o construtor da classe DBConnector é declarado como privado, o que 
     * impede a criação de instâncias externamente. Além disso, o método getConexao() é declarado como synchronized, 
     * garantindo que apenas uma thread possa acessá-lo por vez. Essas alterações tornam a classe DBConnector mais alinhada 
     * com o padrão Singleton;
     * 
     */

    private static Connection conexao;

    private DBConnector() {
        // Construtor privado para evitar a criação de instâncias
    }

    public static synchronized Connection getConexao() throws SQLException, ClassNotFoundException {
        if (conexao == null || conexao.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexao = DriverManager.getConnection("jdbc:mysql://quickcheck.cty2gsaowhzj.us-east-2.rds.amazonaws.com:3306/quickcheck?zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false", "admin", "n6D3lEStHlkDvj0");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return conexao;
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