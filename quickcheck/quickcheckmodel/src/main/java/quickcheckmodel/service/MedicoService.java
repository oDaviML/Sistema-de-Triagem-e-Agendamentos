package quickcheckmodel.service;

import java.sql.SQLException;
import java.util.List;

import quickcheckmodel.dao.MedicoDAO;
import quickcheckmodel.dto.MedicoDTO;

public class MedicoService {

    MedicoDAO medicoDAO = new MedicoDAO();
    public void cadastrarMedico(MedicoDTO medicoDTO) throws ClassNotFoundException, SQLException{
        medicoDAO.inserir(medicoDTO);
        medicoDAO.inserirSenha(medicoDTO);
    }

    public List<MedicoDTO> listar() {
        return medicoDAO.listar();
    }

    public MedicoDTO login(String cpf, String senha) {
        return medicoDAO.login(cpf, senha);
    }
}
