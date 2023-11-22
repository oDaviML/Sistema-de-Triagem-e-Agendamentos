package quickcheckmodel.service;

import quickcheckmodel.dao.SenhaDAO;
import quickcheckmodel.dto.PacienteDTO;

public class SenhaService {

    SenhaDAO senhaDAO = new SenhaDAO();

    public String criptografar(String senha) {
        SenhaDAO encryptionStrategy = new SenhaDAO();
        
        return encryptionStrategy.encrypt(senha);
    }

    public String generateRandomKey() {
        return senhaDAO.generateRandomKey();
    }

    public Boolean verificar(String cpf, String senha, int medicooupaciente) {
        return senhaDAO.verificarSenha(cpf, senha, medicooupaciente);
    }
    
}