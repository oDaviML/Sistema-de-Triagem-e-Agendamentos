package quickcheckmodel.service;

import java.util.List;

import quickcheckmodel.dao.ClinicaDAO;
import quickcheckmodel.dto.ClinicaDTO;

public class ClinicaService {
    public ClinicaDTO inserirOuAtualizarClinica(ClinicaDTO clinicaDTO) throws ClassNotFoundException {
        return ClinicaDAO.inserirOuAtualizarClinica(clinicaDTO);
    }

    public ClinicaDTO obterClinicaPorCPF(String cpf) throws ClassNotFoundException {
        return ClinicaDAO.obterClinicaPorCPF(cpf);
    }

    public List<ClinicaDTO> listarClinicas() throws ClassNotFoundException {
        return ClinicaDAO.listarClinicas();
    }

}
