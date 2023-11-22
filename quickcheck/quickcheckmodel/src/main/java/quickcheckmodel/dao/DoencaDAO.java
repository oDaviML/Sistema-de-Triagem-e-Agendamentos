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

    public static DoencaDTO inserirDoenca(DoencaDTO doencaDTO) throws ClassNotFoundException, SQLException {

        Connection connection = null; //Conexão com o banco de dados

        PreparedStatement ps = null; //Interage com o banco de dados a partir de comandos SQL
        
        System.out.println("Cheguei aqui");
        
        try {

            //Estabelece a conexão com o banco de dados.
            connection = DBConnector.getConexao();

            /*
            - A função prepareStatement recebe como parâmetro um código SQL que será executado.
            - ps se tornar a instância de prepareStatement que se relaciona com o banco de dados conectado.
            - Cada '?' é um campo subtstituído por um valor.
             */
            ps = connection.prepareStatement("INSERT INTO doenca (nome, gravidade, regiao, descricao, tratamento, sintomasCabecaPescoso, sintomasRespiratorio, sintomasGastrointestinal, sintomasPelve, sintomasMuscular, sintomasVisual, cpfMedico, nomeMedico)" +
                                            " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");

            //Executa a função SQL recebida como parâmetro.
            ps.setString(1, doencaDTO.getNome());
            ps.setString(2, doencaDTO.getGravidade());
            ps.setString(3, doencaDTO.getRegiao());
            ps.setString(4, doencaDTO.getDescricao());
            ps.setString(5, doencaDTO.getTratamento());
            
            String sintomasStringCabecaPescoso = String.join(",", doencaDTO.getSintomasCabecaPescoso());
            ps.setString(6, sintomasStringCabecaPescoso);
            
            String sintomasStringRespiratorio = String.join(",", doencaDTO.getSintomasRespiratorio());
            ps.setString(7, sintomasStringRespiratorio);
            
            String sintomasStringGastrointestinal = String.join(",", doencaDTO.getSintomasGastrointestinal());
            ps.setString(8, sintomasStringGastrointestinal);
            
            String sintomasStringPelve = String.join(",", doencaDTO.getSintomasPelve());
            ps.setString(9, sintomasStringPelve);
            
            String sintomasStringMuscular = String.join(",", doencaDTO.getSintomasMuscular());
            ps.setString(10, sintomasStringMuscular);
            
            String sintomasStringVisual = String.join(",", doencaDTO.getSintomasVisual());
            ps.setString(11, sintomasStringVisual);
            
            ps.setString(12, doencaDTO.getCpfMedico());
            ps.setString(13, doencaDTO.getNomeMedico());
            
            ps.executeUpdate();
            return doencaDTO;
            
        } finally {
            DBConnector.fecharConexao(connection, ps); //A função fecha o banco de dados, independente de erros
        }
        
    }
    
    public static DoencaDTO obterDoencaPorCPF(String cpf) throws ClassNotFoundException {
        try (Connection connection = DBConnector.getConexao()) {
            String sql = "SELECT * FROM doenca WHERE cpfMedico = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, cpf);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String sintomasCabecaPescosoString = resultSet.getString("sintomasCabecaPescoso");
                    String[] sintomasCabecaPescoso = sintomasCabecaPescosoString.split(",");
                    String sintomasRespiratorioString = resultSet.getString("sintomasRespiratorio");
                    String[] sintomasRespiratorio = sintomasRespiratorioString.split(",");
                    String sintomasGastrointestinalString = resultSet.getString("sintomasGastrointestinal");
                    String[] sintomasGastrointestinal = sintomasGastrointestinalString.split(",");
                    String sintomasPelveString = resultSet.getString("sintomasPelve");
                    String[] sintomasPelve = sintomasPelveString.split(",");
                    String sintomasMuscularString = resultSet.getString("sintomasMuscular");
                    String[] sintomasMuscular = sintomasMuscularString.split(",");
                    String sintomasVisualString = resultSet.getString("sintomasVisual");
                    String[] sintomasVisual = sintomasVisualString.split(",");
                    return new DoencaDTO(
                            resultSet.getString("nome"),
                            resultSet.getString("gravidade"),
                            resultSet.getString("regiao"),
                            resultSet.getString("descricao"),
                            resultSet.getString("tratamento"),
                            resultSet.getString("cpfMedico"),
                            resultSet.getString("nomeMedico"),
                            sintomasCabecaPescoso,
                            sintomasRespiratorio,
                            sintomasGastrointestinal,
                            sintomasPelve,
                            sintomasMuscular,
                            sintomasVisual      
                    );
                }
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public static List<DoencaDTO> listarDoencas(String cpf) throws ClassNotFoundException {
        List<DoencaDTO> doencas = new ArrayList<>();

        try (Connection connection = DBConnector.getConexao()) {
            String sql = "SELECT * FROM doenca WHERE cpfMedico = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, cpf);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String sintomasCabecaPescosoString = resultSet.getString("sintomasCabecaPescoso");
                    String[] sintomasCabecaPescoso = sintomasCabecaPescosoString.split(",");
                    String sintomasRespiratorioString = resultSet.getString("sintomasRespiratorio");
                    String[] sintomasRespiratorio = sintomasRespiratorioString.split(",");
                    String sintomasGastrointestinalString = resultSet.getString("sintomasGastrointestinal");
                    String[] sintomasGastrointestinal = sintomasGastrointestinalString.split(",");
                    String sintomasPelveString = resultSet.getString("sintomasPelve");
                    String[] sintomasPelve = sintomasPelveString.split(",");
                    String sintomasMuscularString = resultSet.getString("sintomasMuscular");
                    String[] sintomasMuscular = sintomasMuscularString.split(",");
                    String sintomasVisualString = resultSet.getString("sintomasVisual");
                    String[] sintomasVisual = sintomasVisualString.split(",");
                    DoencaDTO doenca = new DoencaDTO(
                            resultSet.getString("nome"),
                            resultSet.getString("gravidade"),
                            resultSet.getString("regiao"),
                            resultSet.getString("descricao"),
                            resultSet.getString("tratamento"),
                            resultSet.getString("cpfMedico"),
                            resultSet.getString("nomeMedico"),
                            sintomasCabecaPescoso,
                            sintomasRespiratorio,
                            sintomasGastrointestinal,
                            sintomasPelve,
                            sintomasMuscular,
                            sintomasVisual 
                    );
                    doencas.add(doenca);
                }
            }
        } catch (SQLException e) {
        }
        return doencas;

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
            //ps.setInt(1, doencaDTO.getId());

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
            //ps.setInt(6, doencaDTO.getId());
            ps.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnector.fecharConexao(connection, ps); //A função fecha o banco de dados, independente de erros
        }
    }

    /*public static List<DoencaDTO> listarDoencas() throws SQLException, ClassNotFoundException {

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
           /* ps = connection.prepareStatement("SELECT * FROM doenca");

            //Executa a função SQL recebida como parâmetro.
            ResultSet resultSet = ps.executeQuery();
            List<DoencaDTO> doencas = new ArrayList<>();
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
            while (resultSet.next()) {
                DoencaDTO doencaDTO = new DoencaDTO();
                //doencaDTO.setId(resultSet.getInt("id"));
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
    }*/


}
