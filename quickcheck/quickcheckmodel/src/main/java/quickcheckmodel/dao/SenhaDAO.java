package quickcheckmodel.dao;

import quickcheckmodel.db.DBConnector;
import quickcheckmodel.dto.PacienteDTO;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

interface PasswordEncryptionStrategy {
    String encrypt(String password);
}

public class SenhaDAO implements PasswordEncryptionStrategy  {

    @Override
    public String encrypt(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao criptografar a senha: " + e.getMessage());
        }
    }

    public String generateRandomKey() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomKey = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(10);
            randomKey.append(characters.charAt(index + 52));
        }

        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(52);
            randomKey.append(characters.charAt(index));
        }

        for (int i = randomKey.length() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = randomKey.charAt(i);
            randomKey.setCharAt(i, randomKey.charAt(j));
            randomKey.setCharAt(j, temp);
        }

        return randomKey.toString();
    }

    public Boolean verificarSenha(String cpf, String senha, int medicooupaciente) {
        encrypt(senha);
        try(Connection connection = DBConnector.getConexao()) {
            if (medicooupaciente == 1) {
                String query = "SELECT * FROM senhasmedico WHERE cpf = ? AND senha = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, cpf);
                preparedStatement.setString(2, encrypt(senha));
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next();
            } else {
                String query = "SELECT * FROM senhaspacientes WHERE cpf = ? AND senha = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, cpf);
                preparedStatement.setString(2, encrypt(senha));
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao verificar a senha: " + e.getMessage());
        }
    }
}
