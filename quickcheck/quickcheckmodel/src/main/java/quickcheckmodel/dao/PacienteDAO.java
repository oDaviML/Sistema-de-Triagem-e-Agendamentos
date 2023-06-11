package quickcheckmodel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import quickcheckmodel.db.DBConnector;
import quickcheckmodel.dto.PacienteDTO;
import quickcheckmodel.service.SenhaService;

public class PacienteDAO {
    public static void inserirPaciente(PacienteDTO pacienteDTO) {
        try (Connection connection = DBConnector.getConexao()) {

            String sql = "INSERT INTO paciente (cpf, nome, endereco, email, convenio, telefone, nascimento) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, pacienteDTO.getCpf());
            preparedStatement.setString(2, pacienteDTO.getNome());
            preparedStatement.setString(3, pacienteDTO.getEndereco());
            preparedStatement.setString(4, pacienteDTO.getEmail());
            preparedStatement.setString(5, pacienteDTO.getConvenio());
            preparedStatement.setString(6, pacienteDTO.getTelefone());

            java.sql.Date sqlDate = new java.sql.Date(pacienteDTO.getDatanascimento().getTime());
            preparedStatement.setDate(7, sqlDate);

            preparedStatement.executeUpdate();
            
            sql = "INSERT INTO senhaspacientes (cpf, senha) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, pacienteDTO.getCpf());
            String senha = new SenhaService().criptografar(pacienteDTO.getSenha());
            preparedStatement.setString(2, senha);

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static List<PacienteDTO> buscar() {
        try (Connection connection = DBConnector.getConexao()) {
            String sql = "SELECT * FROM paciente";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<PacienteDTO> pacientes = new ArrayList<>();
            while (resultSet.next()) {
                PacienteDTO pacienteDTO = new PacienteDTO();
                pacienteDTO.setCpf(resultSet.getString("cpf"));
                pacienteDTO.setNome(resultSet.getString("nome"));
                pacienteDTO.setEndereco(resultSet.getString("endereco"));
                pacienteDTO.setEmail(resultSet.getString("email"));
                pacienteDTO.setConvenio(resultSet.getString("convenio"));
                pacienteDTO.setTelefone(resultSet.getString("telefone"));
                pacienteDTO.setDatanascimento(resultSet.getDate("nascimento"));
                pacientes.add(pacienteDTO);
            }
            return pacientes;
        
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PacienteDTO login(String cpf, String senha) {
        try (Connection connection = DBConnector.getConexao()) {
            String senhacriptografada = new SenhaService().criptografar(senha);
            String sql = "SELECT * FROM paciente p INNER JOIN senhaspacientes s ON s.cpf = p.cpf WHERE s.senha = '"+senhacriptografada+"' AND p.cpf = '"+cpf+"';";
            PacienteDTO pacienteDTO = new PacienteDTO();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                
                pacienteDTO.setCpf(resultSet.getString("cpf"));
                pacienteDTO.setNome(resultSet.getString("nome"));
                pacienteDTO.setEndereco(resultSet.getString("endereco"));
                pacienteDTO.setEmail(resultSet.getString("email"));
                pacienteDTO.setConvenio(resultSet.getString("convenio"));
                pacienteDTO.setTelefone(resultSet.getString("telefone"));
                pacienteDTO.setDatanascimento(resultSet.getDate("nascimento"));
                return pacienteDTO;
            }
        return null;
        
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
