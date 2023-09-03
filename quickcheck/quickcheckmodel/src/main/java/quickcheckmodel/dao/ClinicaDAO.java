package quickcheckmodel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import quickcheckmodel.db.DBConnector;
import quickcheckmodel.dto.ClinicaDTO;

public class ClinicaDAO {

    public static ClinicaDTO inserirOuAtualizarClinica(ClinicaDTO clinica) throws ClassNotFoundException {
        try (Connection connection = DBConnector.getConexao()) {
            String sql = "INSERT INTO clinicas (cpfmedico, nome, endereco, telefone, convenio, especialidade, coordenada) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                         "ON DUPLICATE KEY UPDATE nome = VALUES(nome), endereco = VALUES(endereco), telefone = VALUES(telefone), convenio = VALUES(convenio), especialidade = VALUES(especialidade), coordenada = VALUES(coordenada);";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, clinica.getCpfmedico());
                preparedStatement.setString(2, clinica.getNome());
                preparedStatement.setString(3, clinica.getEndereco());
                preparedStatement.setString(4, clinica.getTelefone());

                String conveniosString = String.join(",", clinica.getConvenios());
                preparedStatement.setString(5, conveniosString);
                
                preparedStatement.setString(6, clinica.getEspecialidade());
                clinica.setCoordenada(obterCoordenadas(clinica.getEndereco()));
                preparedStatement.setString(7, clinica.getCoordenada());

                preparedStatement.executeUpdate();
                return clinica;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clinica;
    }

    public static ClinicaDTO obterClinicaPorCPF(String cpf) throws ClassNotFoundException {
        try (Connection connection = DBConnector.getConexao()) {
            String sql = "SELECT * FROM clinicas WHERE cpfmedico = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, cpf);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String convenioString = resultSet.getString("convenio");
                    String[] convenios = convenioString.split(",");
                    return new ClinicaDTO(
                            resultSet.getString("nome"),
                            resultSet.getString("endereco"),
                            resultSet.getString("telefone"),
                            convenios,
                            resultSet.getString("cpfmedico"),
                            resultSet.getString("especialidade"),
                            resultSet.getString("coordenada")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String obterCoordenadas(String endereco) {
        try {
            GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyC0QG_G0LdTKu_AIzR9awlnzqIMOU0g3pI").build();
            GeocodingResult[] results;
            System.out.println(endereco);
            results = GeocodingApi.geocode(context, endereco).await();
            context.shutdown();
            LatLng coordenadas = new LatLng(results[0].geometry.location.lat, results[0].geometry.location.lng);
            return coordenadas.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<ClinicaDTO> listarClinicas() throws ClassNotFoundException {
        List<ClinicaDTO> clinicas = new ArrayList<>();

        try (Connection connection = DBConnector.getConexao()) {
            String sql = "SELECT * FROM clinicas";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String convenioString = resultSet.getString("convenio");
                    String[] convenios = convenioString.split(",");
                    ClinicaDTO clinica = new ClinicaDTO(
                            resultSet.getString("nome"),
                            resultSet.getString("endereco"),
                            resultSet.getString("telefone"),
                            convenios,
                            resultSet.getString("cpfmedico"),
                            resultSet.getString("especialidade"),
                            resultSet.getString("coordenada")
                    );
                    clinicas.add(clinica);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return clinicas;
    }
}
