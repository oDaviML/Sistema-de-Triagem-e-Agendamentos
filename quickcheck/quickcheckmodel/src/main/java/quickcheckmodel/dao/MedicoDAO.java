package quickcheckmodel.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import quickcheckmodel.dto.MedicoDTO;
import quickcheckmodel.service.SenhaService;

public class MedicoDAO extends BaseDAO<MedicoDTO> {

    // Cadastro Medico e Senha
    @Override
    protected String getInsertQuery() {
        return "INSERT INTO medico (cpf, nome, endereco, email, crm, telefone, nascimento, sexo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected void setInsertParameters(PreparedStatement preparedStatement, MedicoDTO medicoDTO) throws SQLException {
        preparedStatement.setString(1, medicoDTO.getCpf());
        preparedStatement.setString(2, medicoDTO.getNome());
        preparedStatement.setString(3, medicoDTO.getEndereco());
        preparedStatement.setString(4, medicoDTO.getEmail());
        preparedStatement.setString(5, medicoDTO.getCrm());
        preparedStatement.setString(6, medicoDTO.getTelefone());

        java.sql.Date sqlDate = new java.sql.Date(medicoDTO.getDataNascimento().getTime());
        preparedStatement.setDate(7, sqlDate);
        preparedStatement.setString(8, medicoDTO.getSexo());
    }

    @Override
    protected String getInsertSenhaQuery() {
        return "INSERT INTO senhasmedico (cpf, senha) VALUES (?, ?)";
    }

    @Override
    protected void setInsertSenhaParameters(PreparedStatement preparedStatement, MedicoDTO medicoDTO) throws SQLException {
        String senha = new SenhaService().criptografar(medicoDTO.getSenha());
        preparedStatement.setString(1, medicoDTO.getCpf());
        preparedStatement.setString(2, senha);
    }

    @Override
    protected String getAlterarSenhaQuery() {
        return "UPDATE senhasmedico SET senha = ? WHERE cpf = ?";
    }

    @Override
    protected void setAlterarSenhaQuery(PreparedStatement preparedStatement, MedicoDTO dto) throws SQLException {
        String senha = new SenhaService().criptografar(dto.getSenha());
        preparedStatement.setString(1, senha);
        preparedStatement.setString(2, dto.getCpf());
    }

    // Login
    @Override
    protected String getLoginQuery() {
        return "SELECT * FROM medico m INNER JOIN senhasmedico s ON s.cpf = m.cpf WHERE s.senha = ? AND m.cpf = ?";
    }

    @Override
    protected void setLoginParameters(PreparedStatement preparedStatement, String cpf, String senha) throws SQLException{
        String senhacriptografada = new SenhaService().criptografar(senha);
        preparedStatement.setString(1, senhacriptografada);
        preparedStatement.setString(2, cpf);
    }

    @Override
    protected MedicoDTO processLoginResult(ResultSet resultSet) throws SQLException {
       MedicoDTO medicoDTO = new MedicoDTO();
       if (resultSet.next()) {
           medicoDTO.setCpf(resultSet.getString("cpf"));
           medicoDTO.setNome(resultSet.getString("nome"));
           medicoDTO.setEndereco(resultSet.getString("endereco"));
           medicoDTO.setEmail(resultSet.getString("email"));
           medicoDTO.setCrm(resultSet.getString("crm"));
           medicoDTO.setTelefone(resultSet.getString("telefone"));
           Date data = new Date(resultSet.getDate("nascimento").getTime());
           medicoDTO.setSexo(resultSet.getString("sexo"));
           medicoDTO.setDataNascimento(data);
           return medicoDTO;
       }
       return null;
    }

    // Listar
    @Override
    protected String getListQuery() {
        return "SELECT * FROM medico";
    }

    @Override
    protected List<MedicoDTO> processListResult(ResultSet resultSet) throws SQLException {
        List<MedicoDTO> list = new ArrayList<>();
        while (resultSet.next()) {
            MedicoDTO medicoDTO = new MedicoDTO();
            medicoDTO.setCpf(resultSet.getString("cpf"));
            medicoDTO.setNome(resultSet.getString("nome"));
            medicoDTO.setEndereco(resultSet.getString("endereco"));
            medicoDTO.setEmail(resultSet.getString("email"));
            medicoDTO.setCrm(resultSet.getString("crm"));
            medicoDTO.setTelefone(resultSet.getString("telefone"));
            medicoDTO.setDataNascimento(resultSet.getDate("nascimento"));
            medicoDTO.setSexo(resultSet.getString("sexo"));
            list.add(medicoDTO);
        }
        return list;
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE medico SET nome = ?, email = ?, telefone = ?, nascimento = ?, endereco = ? WHERE cpf = ?";
    }

    @Override
    protected void setUpdateParameters(PreparedStatement preparedStatement, MedicoDTO dto) throws SQLException {
        preparedStatement.setString(1, dto.getNome());
        preparedStatement.setString(2, dto.getEmail());
        preparedStatement.setString(3, dto.getTelefone());
        java.sql.Date sqlDate = new java.sql.Date(dto.getDataNascimento().getTime());
        preparedStatement.setDate(4, sqlDate);
        preparedStatement.setString(5, dto.getEndereco());
        preparedStatement.setString(6, dto.getCpf());
    }

    @Override
    protected MedicoDTO processVerificarResult(ResultSet resultSet) throws SQLException {
        MedicoDTO medicoDTO = new MedicoDTO();
        if (resultSet.next()) {
            medicoDTO.setCpf(resultSet.getString("cpf"));
            medicoDTO.setNome(resultSet.getString("nome"));
            medicoDTO.setEmail(resultSet.getString("email"));
            medicoDTO.setCrm(resultSet.getString("crm"));
            medicoDTO.setTelefone(resultSet.getString("telefone"));
            return medicoDTO;
        }
        return null;
    }

    @Override
    protected String getVerificarQuery() {
        return "SELECT * FROM medico WHERE cpf = ?";
    }

    @Override
    protected void setVerificarParameters(PreparedStatement preparedStatement, MedicoDTO dto) throws SQLException {
        preparedStatement.setString(1, dto.getCpf());
    }

}
