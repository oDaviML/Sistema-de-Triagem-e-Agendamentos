package model.service;

import java.util.Scanner;

import model.dao.SenhaDAO;

public class SenhaService {

    public String criptografar(String senha) {
        SenhaDAO encryptionStrategy = new SenhaDAO();
        
        return encryptionStrategy.encrypt(senha);
    }
    
}