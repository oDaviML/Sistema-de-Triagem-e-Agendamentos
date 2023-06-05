package quickcheckmodel.service;

import java.util.List;

import quickcheckmodel.dao.PacienteDAO;
import quickcheckmodel.dto.PacienteDTO;

public class PacienteService {
    public void cadastrarPaciente(PacienteDTO pacienteDTO) {
        PacienteDAO.inserirPaciente(pacienteDTO);
    }

    public List<PacienteDTO> listar() {
        return PacienteDAO.buscar();
    }
}
