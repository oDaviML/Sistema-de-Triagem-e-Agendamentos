package quickcheckmodel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import quickcheckmodel.db.DBConnector;
import quickcheckmodel.dto.DocumentoDTO;

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

    public static List<DocumentoDTO> listar(DocumentoDTO documentoDTO) {
        try (Connection connection = DBConnector.getConexao()) {
            String sql = "SELECT d.* FROM documentospacientes d JOIN paciente p ON p.cpf = d.cpfpaciente WHERE p.cpf = '"+documentoDTO.getCpf()+"';";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<DocumentoDTO> documentos = new ArrayList<>();
            while (resultSet.next()) {
  

            }
            return documentos;
        
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
