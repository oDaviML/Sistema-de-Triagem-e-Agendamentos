/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

import model.db.DBConnector;
import model.dto.MedicoDTO;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Usuário
 */
public class MedicoDAO {

    public static int inserirMedico(MedicoDTO medicoDTO) throws NoSuchAlgorithmException, SQLException, ClassNotFoundException {

        Connection connection = null; //Conexão com o banco de dados

        PreparedStatement ps = null; //Interage com o banco de dados a partir de comandos SQL

        int retorno = 0; //Retorna o resultado da interação com o banco de dados

        String hex = null; // Senha criptografada

        //Código que criptografa a senha com SHA
        
        try {
            
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hash = md.digest(medicoDTO.getSenha().getBytes(StandardCharsets.UTF_8));

            BigInteger numero = new BigInteger(1, hash);

            StringBuilder hexString = new StringBuilder(numero.toString(16));

            while (hexString.length() < 64) {
                hexString.insert(0, '0');
            }

            hex = hexString.toString();

            //Estabelece a conexão com o banco de dados. 
            
            connection = DBConnector.getConexao();
            
            /*
            - A função prepareStatement recebe como parâmetro um código SQL que será executado.
            - ps se tornar a instância de prepareStatement que se relaciona com o banco de dados conectado.
            - Cada '?' é um campo subtstituído por um valor.
            */
            
            ps = connection.prepareStatement("INSERT INTO medico VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            
            //Executa a função SQL recebida como parâmetro.
            
            ps.setString(1, medicoDTO.getNome());
            ps.setString(2, hex);
            ps.setString(3, medicoDTO.getCpf());
            ps.setDate(4, medicoDTO.getDataNascimento());
            ps.setString(5, medicoDTO.getEndereco());
            ps.setString(6, medicoDTO.getTelefone());
            ps.setString(7, medicoDTO.getEmail());
            ps.setString(8, medicoDTO.getCrm());
            
            //Retorna 1 caso seja feita a operação com sucesso e 0 em caso de erro.

            retorno = ps.executeUpdate();
            
        } catch (NoSuchAlgorithmException ex) {

        } finally {
            DBConnector.fecharConexao(connection, ps); //A função fecha o banco de dados, independente de erros
        }

        return retorno;

    }
}
