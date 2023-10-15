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
import quickcheckmodel.dto.DocumentoDTO;
import quickcheckmodel.dto.PacienteDTO;

public class DocumentoDAO {
    
    public static void inserirDocumento(DocumentoDTO documentoDTO) {
        try (Connection connection = DBConnector.getConexao()) {

            String sql = "INSERT INTO documentospacientes (cpfpaciente, tipo, nome, link) VALUES (?, ?, ?, ?)";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, documentoDTO.getCpf());
            preparedStatement.setString(2, documentoDTO.getTipo());
            preparedStatement.setString(3, documentoDTO.getNome());
            preparedStatement.setString(4, documentoDTO.getLink());

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void atualizarDocumento(DocumentoDTO documentoDTO) {
        try (Connection connection = DBConnector.getConexao()) {

            String sql = "UPDATE documentospacientes SET tipo = ?, nome = ?, link = ? WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, documentoDTO.getTipo());
            preparedStatement.setString(2, documentoDTO.getNome());
            preparedStatement.setString(3, documentoDTO.getLink());
            preparedStatement.setInt(4, documentoDTO.getId());

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void removerDocumento(DocumentoDTO documentoDTO) {
        try (Connection connection = DBConnector.getConexao()) {

            String sql = "DELETE FROM documentospacientes WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, documentoDTO.getId());

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<DocumentoDTO> listar(String cpf) {
        try (Connection connection = DBConnector.getConexao()) {
            String sql = "SELECT d.* FROM documentospacientes d JOIN paciente p ON p.cpf = d.cpfpaciente WHERE p.cpf = '"+cpf+"';";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<DocumentoDTO> documentos = new ArrayList<>();
            while (resultSet.next()) {
                DocumentoDTO documentoDTO = new DocumentoDTO();
                documentoDTO.setLink(resultSet.getString("link"));
                documentoDTO.setNome(resultSet.getString("nome"));
                documentoDTO.setTipo(resultSet.getString("tipo"));

                Timestamp timestamp = resultSet.getTimestamp("data");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String formattedDate = dateFormat.format(timestamp);
                documentoDTO.setData(formattedDate);
                documentoDTO.setId(resultSet.getInt("id"));

                documentos.add(documentoDTO);
            }
            return documentos;
        
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<PacienteDTO> listarPacientes(String cpf) throws ClassNotFoundException, SQLException {
        try (Connection connection = DBConnector.getConexao()) {
            String sql = "SELECT DISTINCT p.* FROM paciente p JOIN consulta c ON p.cpf = c.cpfpaciente WHERE c.cpfmedico = '"+cpf+"';";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<PacienteDTO> pacientes = new ArrayList<>();
            while (resultSet.next()) {
                PacienteDTO paciente = new PacienteDTO();
                paciente.setCpf(resultSet.getString("cpf"));
                paciente.setNome(resultSet.getString("nome"));
                paciente.setEndereco(resultSet.getString("endereco"));
                paciente.setEmail(resultSet.getString("email"));
                paciente.setConvenio(resultSet.getString("convenio"));
                paciente.setTelefone(resultSet.getString("telefone"));
                paciente.setDatanascimento(resultSet.getDate("nascimento"));      
                pacientes.add(paciente);
            }
            return pacientes;
        }
    }
}
