package quickcheckmodel.service;

import quickcheckmodel.dao.ResultadoTriagemDAO;
import quickcheckmodel.dto.PacienteDTO;
import quickcheckmodel.dto.ResultadoTriagemDTO;

import java.util.List;

public class ResultadoTriagemService {
    public List<ResultadoTriagemDTO> listar(PacienteDTO pacienteDTO) {
        return ResultadoTriagemDAO.listarTriagens(pacienteDTO);
    }
}
