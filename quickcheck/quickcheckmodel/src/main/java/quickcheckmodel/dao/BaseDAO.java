package quickcheckmodel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import quickcheckmodel.db.DBConnector;

public abstract class BaseDAO<T> { 
    /*
     * a classe BaseDAO fornece uma estrutura comum para operações de acesso ao banco de dados, 
     * enquanto permite que as subclasses forneçam a implementação específica para cada tipo de 
     * dado que será manipulado. Dessa forma, a lógica básica de conexão ao banco de dados e execução 
     * de consultas é centralizada e reutilizada em todas as subclasses, evitando duplicação de código e promovendo 
     * a consistência das operações.
     */

    // <T> = tipo genérico

    // Cadastro usuario e senha
    public void inserir(T dto) {
        try (Connection connection = DBConnector.getConexao()) {
            String sql = getInsertQuery();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            setInsertParameters(preparedStatement, dto);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);  
        }
    }

    protected abstract String getInsertQuery();

    protected abstract void setInsertParameters(PreparedStatement preparedStatement, T dto) throws SQLException;

    public void inserirSenha(T dto) {
        try (Connection connection = DBConnector.getConexao()) {
            String sql = getInsertSenhaQuery();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            setInsertSenhaParameters(preparedStatement, dto);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);  
        }
    }

    protected abstract String getInsertSenhaQuery();

    protected abstract void setInsertSenhaParameters(PreparedStatement preparedStatement, T dto) throws SQLException;

    // Recuperar senha

    public void alterarSenha(T dto) {
        try (Connection connection = DBConnector.getConexao()) {
            String sql = getAlterarSenhaQuery();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            setAlterarSenhaQuery(preparedStatement, dto);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract String getAlterarSenhaQuery();

    protected abstract void setAlterarSenhaQuery(PreparedStatement preparedStatement, T dto) throws SQLException;

    // Login

    public <T> T login(String cpf, String senha) {
        try (Connection connection = DBConnector.getConexao()) {
            String sql = getLoginQuery();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            setLoginParameters(preparedStatement, cpf, senha);
            ResultSet resultSet = preparedStatement.executeQuery();
            return processLoginResult(resultSet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract String getLoginQuery();

    protected abstract void setLoginParameters(PreparedStatement preparedStatement, String cpf, String senha) throws SQLException;

    protected abstract <T> T processLoginResult(ResultSet resultSet) throws SQLException;


    // Listar
    public List<T> listar() {
        try (Connection connection = DBConnector.getConexao()) {
            String sql = getListQuery();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            return processListResult(resultSet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract String getListQuery();

    protected abstract List<T> processListResult(ResultSet resultSet) throws SQLException;

}