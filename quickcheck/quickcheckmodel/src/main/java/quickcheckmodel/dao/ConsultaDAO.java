/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quickcheckmodel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import quickcheckmodel.db.DBConnector;
import quickcheckmodel.dto.ConsultaDTO;

public class ConsultaDAO {

    public static void inserirConsulta(ConsultaDTO consultaDTO) throws ClassNotFoundException, SQLException {

        Connection connection = null; //Conexão com o banco de dados

        PreparedStatement ps = null; //Interage com o banco de dados a partir de comandos SQL
        try {

            //Estabelece a conexão com o banco de dados.
           
            connection = DBConnector.getConexao();
           
            /*
            - A função prepareStatement recebe como parâmetro um código SQL que será executado.
            - ps se tornar a instância de prepareStatement que se relaciona com o banco de dados conectado.
            - Cada '?' é um campo subtstituído por um valor.
            */
           
            ps = connection.prepareStatement("INSERT INTO consulta VALUES (?, ?, ?, ?, ?)");
           
            //Executa a função SQL recebida como parâmetro.
           
            ps.setString(1, consultaDTO.getAssunto());
            ps.setString(2, consultaDTO.getEndereco());
            ps.setString(3, consultaDTO.getEmail());
            ps.setString(4, consultaDTO.getTelefone());
            java.sql.Date sqlDate = new java.sql.Date(consultaDTO.getData().getTime());
            ps.setDate(5, sqlDate);
            ps.executeUpdate();

            ps.executeUpdate();
           
        } finally {
            DBConnector.fecharConexao(connection, ps); //A função fecha o banco de dados, independente de erros
        }
    }
   
    public static void atualizarConsulta(ConsultaDTO consultaDTO) throws SQLException {
        Connection connection = null; //Conexão com o banco de dados

        PreparedStatement ps = null; //Interage com o banco de dados a partir de comandos SQL
       
        try {

            //Estabelece a conexão com o banco de dados.
           
            connection = DBConnector.getConexao();

            /*
            - A função prepareStatement recebe como parâmetro um código SQL que será executado.
            - ps se tornar a instância de prepareStatement que se relaciona com o banco de dados conectado.
            - Cada '?' é um campo subtstituído por um valor.
            */
           
            ps = connection.prepareStatement("UPDATE consulta SET assunto = ?, endereco = ?, email = ?, telefone = ?, data = ?  WHERE assunto = ?");

            ps.setString(1, consultaDTO.getAssunto());
            ps.setString(2, consultaDTO.getEndereco());
            ps.setString(3, consultaDTO.getEmail());
            ps.setString(4, consultaDTO.getTelefone());
            java.sql.Date sqlDate = new java.sql.Date(consultaDTO.getData().getTime());
            ps.setDate(5, sqlDate);
           
            ps.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnector.fecharConexao(connection, ps); //A função fecha o banco de dados, independente de erros
        }
    }

    public static void removerConsulta(ConsultaDTO consultaDTO) throws SQLException {
       
        Connection connection = null; //Conexão com o banco de dados

        PreparedStatement ps = null; //Interage com o banco de dados a partir de comandos SQL
       
        try {

            //Estabelece a conexão com o banco de dados.
           
            connection = DBConnector.getConexao();

            /*
            - A função prepareStatement recebe como parâmetro um código SQL que será executado.
            - ps se tornar a instância de prepareStatement que se relaciona com o banco de dados conectado.
            - Cada '?' é um campo subtstituído por um valor.
            */
           
            ps = connection.prepareStatement("DELETE FROM consulta WHERE assunto = ?");
           
            //Executa a função SQL recebida como parâmetro.

            ps.setString(1, consultaDTO.getAssunto());

            ps.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnector.fecharConexao(connection, ps); //A função fecha o banco de dados, independente de erros
        }
    }
   
    public static List<ConsultaDTO> buscar() {
        try (Connection connection = DBConnector.getConexao()) {
            String sql = "SELECT * FROM paciente";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ConsultaDTO> consultas = new ArrayList<>();
            while (resultSet.next()) {
                ConsultaDTO ConsultaDTO = new ConsultaDTO();
                ConsultaDTO.setAssunto(resultSet.getString("assunto"));
                ConsultaDTO.setEndereco(resultSet.getString("endereco"));
                ConsultaDTO.setEmail(resultSet.getString("email"));
                ConsultaDTO.setTelefone(resultSet.getString("telefone"));
                ConsultaDTO.setData(resultSet.getDate("nascimento"));
                consultas.add(ConsultaDTO);
            }
            return consultas;
       
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}