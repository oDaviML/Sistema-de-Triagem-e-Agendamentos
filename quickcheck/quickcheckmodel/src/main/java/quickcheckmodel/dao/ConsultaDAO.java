/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quickcheckmodel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import quickcheckmodel.db.DBConnector;
import quickcheckmodel.dto.ConsultaDTO;

public class ConsultaDAO {

    public static void inserirConsultaPaciente(ConsultaDTO consultaDTO) throws ClassNotFoundException, SQLException {

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
           
            ps = connection.prepareStatement("INSERT INTO consultaspacientes VALUES (idclinica, cpfpaciente, especialidade, cidade, nomemedico, sexomedico, emailcontato, telefonecontato, data, horario) (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
           
            //Executa a função SQL recebida como parâmetro.
           
            ps.setInt(1, consultaDTO.getIdClinica());
            ps.setString(2, consultaDTO.getCpfpaciente());
            ps.setString(3, consultaDTO.getEspecialidade());
            ps.setString(4, consultaDTO.getCidade());
            ps.setString(5, consultaDTO.getNomeMedico());
            ps.setString(6, consultaDTO.getSexoMedico());
            ps.setString(7, consultaDTO.getEmailContato());
            ps.setString(8, consultaDTO.getTelefoneContato());
            java.sql.Date sqlDate = new java.sql.Date(consultaDTO.getData().getTime());
            ps.setDate(9, sqlDate);
            ps.setTime(10, consultaDTO.getHorario());

            ps.executeUpdate();
           
        } finally {
            DBConnector.fecharConexao(connection, ps); //A função fecha o banco de dados, independente de erros
        }
    }

    public static void removerConsultaPaciente(ConsultaDTO consultaDTO) throws SQLException {
       
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
           
            ps = connection.prepareStatement("DELETE FROM consultaspacientes WHERE id = ?");
           
            //Executa a função SQL recebida como parâmetro.

            ps.setInt(1, consultaDTO.getId());

            ps.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnector.fecharConexao(connection, ps); //A função fecha o banco de dados, independente de erros
        }
    }
   
    public static List<ConsultaDTO> listarConsultaPaciente(String cpf) {
        try (Connection connection = DBConnector.getConexao()) {
            String sql = "SELECT c.* FROM consultaspacientes c JOIN paciente p ON p.cpf = c.cpfpaciente WHERE p.cpf = '"+cpf+"';";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ConsultaDTO> consultas = new ArrayList<>();
            while (resultSet.next()) {
                ConsultaDTO ConsultaDTO = new ConsultaDTO();
                ConsultaDTO.setIdClinica(resultSet.getInt("idClinica"));
                ConsultaDTO.setEspecialidade(resultSet.getString("especialidade"));
                ConsultaDTO.setNomeMedico(resultSet.getString("nomeMedico"));
                ConsultaDTO.setSexoMedico(resultSet.getString("sexoMedico"));
                ConsultaDTO.setCidade(resultSet.getString("sexoMedico"));
                ConsultaDTO.setEmailContato(resultSet.getString("sexoMedico"));
                ConsultaDTO.setTelefoneContato(resultSet.getString("sexoMedico"));
                ConsultaDTO.setData(resultSet.getDate("data")); 
                ConsultaDTO.setHorario(resultSet.getTime("horario")); 
                
                consultas.add(ConsultaDTO);
            }
            return consultas;
       
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static List<ConsultaDTO> listarConsultaCadastradas(ConsultaDTO consultaDTO) {
        try (Connection connection = DBConnector.getConexao()) {
            String sql = "SELECT * FROM consultacadastradas WHERE (idClinica = ? AND especialidade = ? AND nomeMedico = ? AND sexoMedico = ? AND cidade = ? AND data = ? AND data BETWEEN dataInicial AND dataFinal)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ConsultaDTO> consultas = new ArrayList<>();
            while (resultSet.next()) {
                ConsultaDTO ConsultaDTO = new ConsultaDTO();
                ConsultaDTO.setIdClinica(resultSet.getInt("idClinica"));
                ConsultaDTO.setEspecialidade(resultSet.getString("especialidade"));
                ConsultaDTO.setNomeMedico(resultSet.getString("nomeMedico"));
                ConsultaDTO.setSexoMedico(resultSet.getString("sexoMedico"));
                ConsultaDTO.setCidade(resultSet.getString("cidade")); 
                ConsultaDTO.setData(resultSet.getDate("data"));
                ConsultaDTO.setHorario(resultSet.getTime("horario")); 
                
                consultas.add(ConsultaDTO);
            }
            return consultas;
       
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}