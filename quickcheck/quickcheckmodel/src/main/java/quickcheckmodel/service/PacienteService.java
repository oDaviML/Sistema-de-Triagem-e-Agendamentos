package quickcheckmodel.service;

import java.util.List;

import quickcheckmodel.dao.PacienteDAO;
import quickcheckmodel.dto.PacienteDTO;

public class PacienteService {
    PacienteDAO pacienteDAO = new PacienteDAO();
    public void cadastrarPaciente(PacienteDTO pacienteDTO) {
        pacienteDAO.inserir(pacienteDTO);
        pacienteDAO.inserirSenha(pacienteDTO);
    }

    public List<PacienteDTO> listar() {
        return pacienteDAO.listar();
    }

    public PacienteDTO login(String cpf, String senha) {
        return pacienteDAO.login(cpf, senha);
    }

    public String obterCoordenada(String endereco) {
        return pacienteDAO.obterCoordenada(endereco);
    }

    public List<PacienteDTO> listarPacientes(String cpf){
        return pacienteDAO.listarPacientes(cpf);
    }
}
