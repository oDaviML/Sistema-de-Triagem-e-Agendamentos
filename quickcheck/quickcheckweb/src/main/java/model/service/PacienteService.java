package model.service;

import java.util.List;

import model.dao.PacienteDAO;
import model.dto.PacienteDTO;

public class PacienteService {
    public void cadastrarPaciente(PacienteDTO pacienteDTO) {
        PacienteDAO.inserirPaciente(pacienteDTO);
    }

    public List<PacienteDTO> listar() {
        return PacienteDAO.buscar();
    }
}
