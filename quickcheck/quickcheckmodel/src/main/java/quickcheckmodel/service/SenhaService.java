package quickcheckmodel.service;

import quickcheckmodel.dao.SenhaDAO;

public class SenhaService {

    public String criptografar(String senha) {
        SenhaDAO encryptionStrategy = new SenhaDAO();
        
        return encryptionStrategy.encrypt(senha);
    }
    
}