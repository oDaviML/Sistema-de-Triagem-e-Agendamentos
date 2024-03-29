package quickcheckmodel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import quickcheckmodel.db.DBConnector;
import quickcheckmodel.dto.PacienteDTO;
import quickcheckmodel.service.SenhaService;

public class PacienteDAO extends BaseDAO<PacienteDTO> {

    //Cadastro Paciente e Senha
    @Override
    protected String getInsertQuery() {
        return "INSERT INTO paciente (cpf, nome, endereco, email, convenio, telefone, nascimento, sexo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
        preparedStatement.setString(8, pacienteDTO.getSexo());
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

    @Override
    protected String getAlterarSenhaQuery() {
        return "UPDATE senhaspacientes SET senha = ? WHERE cpf = ?";
    }

    @Override
    protected void setAlterarSenhaQuery(PreparedStatement preparedStatement, PacienteDTO dto) throws SQLException {
        String senha = new SenhaService().criptografar(dto.getSenha());
        preparedStatement.setString(1, senha);
        preparedStatement.setString(2, dto.getCpf());
    }

    //listar

     @Override
    protected String getListQuery() {
        return "SELECT * FROM paciente";
    }

    @Override
    protected List<PacienteDTO> processListResult(ResultSet resultSet) throws SQLException {
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
            paciente.setSexo(resultSet.getString("sexo"));
            pacientes.add(paciente);
        }
        return pacientes;
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE paciente SET nome = ?, email = ?, convenio = ?, telefone = ?, nascimento = ?, endereco = ? WHERE cpf = ?";
    }

    @Override
    protected void setUpdateParameters(PreparedStatement preparedStatement, PacienteDTO dto) throws SQLException {
        preparedStatement.setString(1, dto.getNome());
        preparedStatement.setString(2, dto.getEmail());
        preparedStatement.setString(3, dto.getConvenio());
        preparedStatement.setString(4, dto.getTelefone());
        java.sql.Date sqlDate = new java.sql.Date(dto.getDatanascimento().getTime());
        preparedStatement.setDate(5, sqlDate);
        preparedStatement.setString(6, dto.getEndereco());
        preparedStatement.setString(7, dto.getCpf());

    }

    @Override
    protected PacienteDTO processVerificarResult(ResultSet resultSet) throws SQLException {
        PacienteDTO pacienteDTO = new PacienteDTO();
        if (resultSet.next()) {
            pacienteDTO.setCpf(resultSet.getString("cpf"));
            pacienteDTO.setNome(resultSet.getString("nome"));
            pacienteDTO.setEmail(resultSet.getString("email"));
            pacienteDTO.setTelefone(resultSet.getString("telefone"));
            return pacienteDTO;
        }
        return null;
    }

    @Override
    protected String getVerificarQuery() {
        return "SELECT * FROM paciente WHERE cpf = ?";
    }

    @Override
    protected void setVerificarParameters(PreparedStatement preparedStatement, PacienteDTO dto) throws SQLException {
        preparedStatement.setString(1, dto.getCpf());
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
            Date data = new Date(resultSet.getDate("nascimento").getTime());
            pacienteDTO.setSexo(resultSet.getString("sexo"));
            pacienteDTO.setDatanascimento(data);
            return pacienteDTO;
        }
        return null;
    }



    public static String obterCoordenada(String endereco) {
        try {
            GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyC0QG_G0LdTKu_AIzR9awlnzqIMOU0g3pI").build();
            GeocodingResult[] results;
            results = GeocodingApi.geocode(context, endereco).await();
            context.shutdown();
            LatLng coordenadas = new LatLng(results[0].geometry.location.lat, results[0].geometry.location.lng);
            return coordenadas.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<PacienteDTO> listarPacientes(String cpf) {
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
                paciente.setSexo(resultSet.getString("sexo"));
                pacientes.add(paciente);
            }
            return pacientes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
