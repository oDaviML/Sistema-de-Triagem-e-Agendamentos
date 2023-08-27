package quickcheckmodel.service;

import quickcheckmodel.dao.ClinicaDAO;
import quickcheckmodel.dto.ClinicaDTO;

public class ClinicaService {
    public ClinicaDTO inserirOuAtualizarClinica(ClinicaDTO clinicaDTO) throws ClassNotFoundException {
        return ClinicaDAO.inserirOuAtualizarClinica(clinicaDTO);
    }

    public ClinicaDTO obterClinicaPorCPF(String cpf) throws ClassNotFoundException {
        return ClinicaDAO.obterClinicaPorCPF(cpf);
    }

}
