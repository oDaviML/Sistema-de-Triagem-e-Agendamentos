package quickcheckmodel.service;

import java.sql.SQLException;
import java.util.List;

import quickcheckmodel.dao.MedicoDAO;
import quickcheckmodel.dto.MedicoDTO;

public class MedicoService {
    public void cadastrarMedico(MedicoDTO medicoDTO) throws ClassNotFoundException, SQLException{
        MedicoDAO.inserirMedico(medicoDTO);
    }

     public List<MedicoDTO> listar() {
        return MedicoDAO.buscar();
    }

    public Boolean login(String cpf, String senha) {
        return MedicoDAO.login(cpf, senha);
    }
}
