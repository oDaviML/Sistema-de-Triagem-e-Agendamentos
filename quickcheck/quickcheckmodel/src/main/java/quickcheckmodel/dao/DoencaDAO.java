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
import java.util.TimeZone;

import quickcheckmodel.db.DBConnector;
import quickcheckmodel.dto.DoencaDTO;

public class DoencaDAO {

    public static void inserirDoenca(DoencaDTO doencaDTO) throws ClassNotFoundException, SQLException {

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
            ps = connection.prepareStatement("INSERT INTO doenca (nome, gravidade, regiao, descricao, tratamento) VALUES (?,?,?,?,?)");

            //Executa a função SQL recebida como parâmetro.
            ps.setString(1, doencaDTO.getNome());
            ps.setString(2, doencaDTO.getGravidade());
            ps.setString(3, doencaDTO.getRegiao());
            ps.setString(4, doencaDTO.getDescricao());
            ps.setString(5, doencaDTO.getTratamento());
            ps.executeUpdate();

        } finally {
            DBConnector.fecharConexao(connection, ps); //A função fecha o banco de dados, independente de erros
        }
    }

    public static void removerDoenca(DoencaDTO doencaDTO) throws SQLException {

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
            ps = connection.prepareStatement("DELETE FROM doenca WHERE id = ?");

            //Executa a função SQL recebida como parâmetro.
            ps.setInt(1, doencaDTO.getId());

            ps.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnector.fecharConexao(connection, ps); //A função fecha o banco de dados, independente de erros
        }
    }

    public static void atualizaDoenca(DoencaDTO doencaDTO) throws SQLException {

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
            ps = connection.prepareStatement("UPDATE doenca SET nome = ?, gravidade = ?, regiao = ?, descricao = ?, tratamento = ? WHERE id = ?");

            //Executa a função SQL recebida como parâmetro.
            ps.setString(1, doencaDTO.getNome());
            ps.setString(2, doencaDTO.getGravidade());
            ps.setString(3, doencaDTO.getRegiao());
            ps.setString(4, doencaDTO.getDescricao());
            ps.setString(5, doencaDTO.getTratamento());
            ps.setInt(6, doencaDTO.getId());
            ps.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnector.fecharConexao(connection, ps); //A função fecha o banco de dados, independente de erros
        }
    }

    public static List<DoencaDTO> listarDoencas() throws SQLException, ClassNotFoundException {

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
            ps = connection.prepareStatement("SELECT * FROM doenca");

            //Executa a função SQL recebida como parâmetro.
            ResultSet resultSet = ps.executeQuery();
            List<DoencaDTO> doencas = new ArrayList<>();
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
            while (resultSet.next()) {
                DoencaDTO doencaDTO = new DoencaDTO();
                doencaDTO.setId(resultSet.getInt("id"));
                doencaDTO.setNome(resultSet.getString("nome"));
                doencaDTO.setGravidade(resultSet.getString("gravidade"));
                doencaDTO.setRegiao(resultSet.getString("regiao"));
                doencaDTO.setDescricao(resultSet.getString("descricao"));
                doencaDTO.setTratamento(resultSet.getString("tratamento"));
                doencas.add(doencaDTO);

            }
            return doencas;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
