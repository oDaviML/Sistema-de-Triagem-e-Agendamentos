package quickcheckmodel.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import quickcheckmodel.dto.PacienteDTO;
import quickcheckmodel.service.SenhaService;

public class PacienteDAO extends BaseDAO<PacienteDTO> {

    //Cadastro Paciente e Senha
    @Override
    protected String getInsertQuery() {
        return "INSERT INTO paciente (cpf, nome, endereco, email, convenio, telefone, nascimento) VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected void setInsertParameters(PreparedStatement preparedStatement, PacienteDTO pacienteDTO) throws SQLException {
        preparedStatement.setString(1, pacienteDTO.getCpf());
        preparedStatement.setString(2, pacienteDTO.getNome());
        preparedStatement.setString(3, pacienteDTO.getEndereco());
        preparedStatement.setString(4, pacienteDTO.getEmail());
        preparedStatement.setString(5, pacienteDTO.getConvenio());
        preparedStatement.setString(6, pacienteDTO.getTelefone());

        java.sql.Date sqlDate = new java.sql.Date(pacienteDTO.getDatanascimento().getTime());
        preparedStatement.setDate(7, sqlDate);
    }

    @Override
    protected String getInsertSenhaQuery() {
        return "INSERT INTO senhaspacientes (cpf, senha) VALUES (?, ?)";
    }

    @Override
    protected void setInsertSenhaParameters(PreparedStatement preparedStatement, PacienteDTO pacienteDTO) throws SQLException {
        preparedStatement.setString(1, pacienteDTO.getCpf());
        String senha = new SenhaService().criptografar(pacienteDTO.getSenha());
        preparedStatement.setString(2, senha);
    }

    //listar

     @Override
    protected String getListQuery() {
        return "SELECT * FROM paciente";
    }

    @Override
    protected List<PacienteDTO> processListResult(ResultSet resultSet) throws SQLException {
        return null;
        // Processar o resultado da listagem específico para pacientes
    }  

    //Login

    @Override
    protected String getLoginQuery() {
        return "SELECT * FROM paciente p INNER JOIN senhaspacientes s ON s.cpf = p.cpf WHERE s.senha = ? AND p.cpf = ?;";
    }

    @Override
    protected void setLoginParameters(PreparedStatement preparedStatement, String cpf, String senha) throws SQLException {
        String senhacriptografada = new SenhaService().criptografar(senha);
        preparedStatement.setString(1, senhacriptografada);
        preparedStatement.setString(2, cpf);
    }

    @Override
    protected PacienteDTO processLoginResult(ResultSet resultSet) throws SQLException {
        PacienteDTO pacienteDTO = new PacienteDTO();
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
    }
    
}
