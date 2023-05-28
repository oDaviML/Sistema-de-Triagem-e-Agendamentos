package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import db.DBConnector;
import dto.PacienteDTO;

public class PacienteDAO {
    public static PacienteDTO inserirPaciente(String nome, String endereco, String email, String convenio, String cpf, String telefone,
                Date datanascimento) {
        try (Connection connection = DBConnector.getConexao()) {

            String sql = "INSERT INTO paciente (nome, cpf, email, convenio, telefone, endereco, datanascimento) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PacienteDTO paciente = new PacienteDTO(nome, endereco, email, convenio, cpf, telefone, datanascimento);

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,nome);
            preparedStatement.setString(2, cpf);
            preparedStatement.setDate(3, datanascimento);
            preparedStatement.setString(4, endereco);
            preparedStatement.setString(5, telefone);
            preparedStatement.setString(6, email);
            preparedStatement.setString(7, convenio);

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                paciente.setCodigo(id);
            }
            
            return paciente;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }
}
