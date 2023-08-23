package quickcheckmodel.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import quickcheckmodel.db.DBConnector;
import quickcheckmodel.dto.ClinicaDTO;

public class ClinicaDAO {

    public static void inserirOuAtualizarClinica(ClinicaDTO clinica) throws ClassNotFoundException {
        try (Connection connection = DBConnector.getConexao()) {
            String sql = "INSERT INTO clinicas (cpfmedico, nome, endereco, telefone, convenio) " +
                         "VALUES (?, ?, ?, ?, ?) " +
                         "ON DUPLICATE KEY UPDATE nome = VALUES(nome), endereco = VALUES(endereco), telefone = VALUES(telefone), convenios = VALUES(convenios)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, clinica.getCpfmedico());
                preparedStatement.setString(2, clinica.getNome());
                preparedStatement.setString(3, clinica.getEndereco());
                preparedStatement.setString(4, clinica.getTelefone());

                String conveniosString = String.join(",", clinica.getConvenios());
                preparedStatement.setString(5, conveniosString);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ClinicaDTO obterClinicaPorCPF(String cpf) throws ClassNotFoundException {
        try (Connection connection = DBConnector.getConexao()) {
            String sql = "SELECT * FROM clinicas WHERE cpfmedico = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, cpf);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return new ClinicaDTO(
                            resultSet.getString("cpfmedico"),
                            resultSet.getString("nome"),
                            resultSet.getString("endereco"),
                            Arrays.asList(resultSet.getString("convenio").split(",")),
                            resultSet.getString("telefone")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LatLng obterCoordenadasPorCPF(String cpf) throws ClassNotFoundException {
        try {
             GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyC0QG_G0LdTKu_AIzR9awlnzqIMOU0g3pI").build();
            GeocodingResult[] results;
            results = GeocodingApi.geocode(context, obterClinicaPorCPF(cpf).getEndereco()).await();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            context.shutdown();
            return new LatLng(results[0].geometry.location.lat, results[0].geometry.location.lng);
        } catch (ApiException | InterruptedException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
