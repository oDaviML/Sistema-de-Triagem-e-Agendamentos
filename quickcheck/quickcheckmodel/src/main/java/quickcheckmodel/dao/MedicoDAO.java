package quickcheckmodel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import quickcheckmodel.db.DBConnector;
import quickcheckmodel.dto.MedicoDTO;
import quickcheckmodel.service.SenhaService;

public class MedicoDAO {

    public static void inserirMedico(MedicoDTO medicoDTO) throws ClassNotFoundException, SQLException {

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
            
            ps = connection.prepareStatement("INSERT INTO medico VALUES (?, ?, ?, ?, ?, ?, ?)");
            
            //Executa a função SQL recebida como parâmetro.
            
            ps.setString(1, medicoDTO.getCpf());
            ps.setString(2, medicoDTO.getNome());
            ps.setString(3, medicoDTO.getEndereco());
            ps.setString(4, medicoDTO.getEmail());
            ps.setString(5, medicoDTO.getCrm());
            ps.setString(6, medicoDTO.getTelefone());
            java.sql.Date sqlDate = new java.sql.Date(medicoDTO.getDataNascimento().getTime());
            ps.setDate(7, sqlDate);
            ps.executeUpdate();
            
            ps = connection.prepareStatement("INSERT INTO senhasmedico (cpf, senha) VALUES (?, ?)");
            ps.setString(1, medicoDTO.getCpf());
            String senha = new SenhaService().criptografar(medicoDTO.getSenha());
            ps.setString(2, senha);

            ps.executeUpdate();
            
        } finally {
            DBConnector.fecharConexao(connection, ps); //A função fecha o banco de dados, independente de erros
        }
    }

    public static List<MedicoDTO> buscar() {
        try (Connection connection = DBConnector.getConexao()) {
            String sql = "SELECT * FROM paciente";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<MedicoDTO> medicos = new ArrayList<>();
            while (resultSet.next()) {
                MedicoDTO MedicoDTO = new MedicoDTO();
                MedicoDTO.setCpf(resultSet.getString("cpf"));
                MedicoDTO.setNome(resultSet.getString("nome"));
                MedicoDTO.setEndereco(resultSet.getString("endereco"));
                MedicoDTO.setEmail(resultSet.getString("email"));
                MedicoDTO.setCrm(resultSet.getString("crm"));
                MedicoDTO.setTelefone(resultSet.getString("telefone"));
                MedicoDTO.setDataNascimento(resultSet.getDate("nascimento"));
                medicos.add(MedicoDTO);
            }
            return medicos;
        
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Boolean login(String cpf, String senha) {
        try (Connection connection = DBConnector.getConexao()) {
            String senhacriptografada = new SenhaService().criptografar(senha);
            String sql = "SELECT * FROM medico m INNER JOIN senhasmedico s ON s.cpf = m.cpf WHERE s.senha = '"+senhacriptografada+"' AND m.cpf = '"+cpf+"';";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
            return false;
        
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
