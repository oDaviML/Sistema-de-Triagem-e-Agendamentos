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
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
           
            ps = connection.prepareStatement("INSERT INTO consulta (cpfmedico, cpfpaciente, especialidade, convenio, data, horario) VALUES (?,?,?,?,?,?)");
           
            //Executa a função SQL recebida como parâmetro.
           
            ps.setString(1, consultaDTO.getCpfmedico());
            ps.setString(2, consultaDTO.getCpfpaciente());
            ps.setString(3, consultaDTO.getEspecialidade());
            ps.setString(4, consultaDTO.getConvenio());
            ps.setDate(5, new java.sql.Date(consultaDTO.getData().getTime()));
            ps.setString(6, consultaDTO.getHorario());
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
           
            ps = connection.prepareStatement("DELETE FROM consulta WHERE id = ?");
           
            //Executa a função SQL recebida como parâmetro.
            System.out.println("ID: " + consultaDTO.getId());
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
            String sql = "SELECT m.nome, c.* FROM medico m JOIN consulta c ON m.cpf = c.cpfmedico WHERE c.cpfpaciente = '"+cpf+"';";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ConsultaDTO> consultas = new ArrayList<>();
            while (resultSet.next()) {
                ConsultaDTO consultaDTO = new ConsultaDTO();
                consultaDTO.setNomemedico(resultSet.getString("nome"));
                consultaDTO.setId(resultSet.getInt("id"));
                consultaDTO.setCpfmedico(resultSet.getString("cpfmedico"));
                consultaDTO.setCpfpaciente(resultSet.getString("cpfpaciente"));
                consultaDTO.setEspecialidade(resultSet.getString("especialidade"));
                consultaDTO.setConvenio(resultSet.getString("convenio"));
                consultaDTO.setData(resultSet.getDate("data")); 
                consultaDTO.setHorario(resultSet.getString("horario"));
                consultas.add(consultaDTO);
            }
            return consultas;
       
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static List<ConsultaDTO> listarConsultaCadastradas() {
        try (Connection connection = DBConnector.getConexao()) {
            String sql = "SELECT * FROM consultacadastradas WHERE (idClinica = ? AND especialidade = ? AND nomeMedico = ? AND sexoMedico = ? AND cidade = ? AND data = ? AND data BETWEEN dataInicial AND dataFinal)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ConsultaDTO> consultas = new ArrayList<>();
            while (resultSet.next()) {
                ConsultaDTO consultaDTO = new ConsultaDTO();
                consultaDTO.setId(resultSet.getInt("id"));
                consultaDTO.setCpfmedico(resultSet.getString("cpfmedico"));
                consultaDTO.setCpfpaciente(resultSet.getString("cpfpaciente"));
                consultaDTO.setEspecialidade(resultSet.getString("especialidade"));
                consultaDTO.setConvenio(resultSet.getString("convenio"));
                consultaDTO.setData(resultSet.getDate("data")); 
                consultaDTO.setHorario(resultSet.getString("horario")); 
                consultas.add(consultaDTO);
            }
            return consultas;
       
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<ConsultaDTO> listarConsultasMedicos(String cpf) {
        try (Connection connection = DBConnector.getConexao()) {
            String sql = "SELECT * FROM consulta WHERE cpfmedico = '"+cpf+"';";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ConsultaDTO> consultas = new ArrayList<>();
            while (resultSet.next()) {
                ConsultaDTO consultaDTO = new ConsultaDTO();
                consultaDTO.setId(resultSet.getInt("id"));
                consultaDTO.setCpfmedico(resultSet.getString("cpfmedico"));
                consultaDTO.setCpfpaciente(resultSet.getString("cpfpaciente"));
                consultaDTO.setEspecialidade(resultSet.getString("especialidade"));
                consultaDTO.setConvenio(resultSet.getString("convenio"));

                Timestamp timestamp = resultSet.getTimestamp("data");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                String formattedDate = dateFormat.format(timestamp);
                
                consultaDTO.setData(new Date(formattedDate));

                consultaDTO.setHorario(resultSet.getString("horario")); 
                consultas.add(consultaDTO);
            }
            return consultas;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    } 
}