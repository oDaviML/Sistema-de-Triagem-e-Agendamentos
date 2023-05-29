/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.MedicoDAO;
import dto.MedicoDTO;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;

public class MedicoService {
    public static int cadastrarMedico(String nome, String senha, String endereco, String email, String crm, String cpf, String telefone,
    Date datanascimento) throws NoSuchAlgorithmException, SQLException, ClassNotFoundException {
        
       MedicoDTO medicoDTO = new MedicoDTO (nome, senha, endereco, email, crm, cpf, telefone, datanascimento);
      
       return MedicoDAO.inserirMedico(medicoDTO);
        
    }
}
