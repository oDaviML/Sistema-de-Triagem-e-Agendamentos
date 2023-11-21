package quickcheckmodel.service;

import java.sql.SQLException;
import java.util.List;

import quickcheckmodel.dao.MedicoDAO;
import quickcheckmodel.dto.MedicoDTO;
import quickcheckmodel.dto.PacienteDTO;

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

    public void alterarSenha(MedicoDTO medicoDTO){
        medicoDAO.alterarSenha(medicoDTO);
    }

    public void editar(MedicoDTO medicoDTO){
        medicoDAO.editar(medicoDTO);
    }

    public MedicoDTO verificar(MedicoDTO medico) {
        return medicoDAO.verificar(medico);
    }
}
