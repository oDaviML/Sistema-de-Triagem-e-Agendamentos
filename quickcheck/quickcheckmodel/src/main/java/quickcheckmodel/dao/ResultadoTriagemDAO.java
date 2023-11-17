package quickcheckmodel.dao;

import quickcheckmodel.db.DBConnector;
import quickcheckmodel.dto.PacienteDTO;
import quickcheckmodel.dto.ResultadoTriagemDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ResultadoTriagemDAO {
    public static List<ResultadoTriagemDTO> listarTriagens(PacienteDTO pacienteDTO) {
        List<ResultadoTriagemDTO> resultadoTriagem = new ArrayList<>();
        try (Connection connection = DBConnector.getConexao()) {
            String sql = "SELECT * FROM triagem WHERE cpfpaciente = '" + pacienteDTO.getCpf() + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ResultadoTriagemDTO resultadoTriagemDTO = new ResultadoTriagemDTO();
                resultadoTriagemDTO.setResultado(resultSet.getString("resultado"));
                resultadoTriagemDTO.setData(resultSet.getTimestamp("horario"));
                resultadoTriagem.add(resultadoTriagemDTO);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultadoTriagem;
    }
}
