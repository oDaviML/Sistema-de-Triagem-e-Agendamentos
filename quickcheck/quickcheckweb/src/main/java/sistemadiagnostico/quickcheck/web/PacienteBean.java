package sistemadiagnostico.quickcheck.web;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

import model.db.DBConnector;
import model.dto.PacienteDTO;
import model.service.PacienteService;

@SessionScoped
@ManagedBean
public class PacienteBean {
    private PacienteDTO paciente = new PacienteDTO();
    private PacienteService pacienteService = new PacienteService();

    private List<PacienteDTO> pacientes = new ArrayList<>();

    public void inserirPaciente() {
        pacienteService.cadastrarPaciente(paciente);
        pacientes = pacienteService.listar();
        paciente = new PacienteDTO();
    }

    public void listar() {
        pacientes = pacienteService.listar();
    }

    public PacienteDTO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDTO paciente) {
        this.paciente = paciente;
    }

    public List<PacienteDTO> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<PacienteDTO> pacientes) {
        this.pacientes = pacientes;
    }

}
