package quickcheckmodel.service;

import quickcheckmodel.dao.TriagemDAO;
import quickcheckmodel.dto.PacienteDTO;
import quickcheckmodel.dto.TriagemDTO;

public class TriagemService {

    TriagemDAO triagemDAO = new TriagemDAO();
    public String resultadoTriagem(TriagemDTO triagem, PacienteDTO paciente) {

        return triagemDAO.resultadoTriagem(triagem, paciente);
    }
}
