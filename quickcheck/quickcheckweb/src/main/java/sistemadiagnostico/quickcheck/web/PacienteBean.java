package sistemadiagnostico.quickcheck.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;
import quickcheckmodel.dto.PacienteDTO;
import quickcheckmodel.service.PacienteService;

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
        return "/loginPaciente.xhtml?faces-redirect=true";
    }

    public void listar() {
        pacientes = pacienteService.listar();
    }
    
    public void login() throws IOException {
        if (pacienteService.login(paciente.getCpf(), paciente.getSenha()) == true) {
            HttpSession session = (HttpSession)FacesContext.getCurrentInstance( ).getExternalContext().getSession(false);
            session.setAttribute("usuario", paciente);
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath() + "/faces/inicioPaciente.xhtml?faces-redirect=true");
        }
        else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Usuário ou senha incorretos");
            FacesContext.getCurrentInstance().addMessage(null,message);
        }
    }
    
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/faces/loginPaciente.xhtml?faces-redirect=true";
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
