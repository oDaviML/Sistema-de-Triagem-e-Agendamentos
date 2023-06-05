package sistemadiagnostico.quickcheck.web;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import model.dto.PacienteDTO;
import model.service.PacienteService;

import java.util.ArrayList;
import java.util.List;

@SessionScoped
@ManagedBean
public class PacienteBean {
    private PacienteDTO paciente = new PacienteDTO();
    private PacienteService pacienteService = new PacienteService();
    private String senha;

    private List<PacienteDTO> pacientes = new ArrayList<>();

    public String inserirPaciente() {
        pacienteService.cadastrarPaciente(paciente);
        paciente = new PacienteDTO();
        return "/login.xhtml?faces-redirect=true";

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
