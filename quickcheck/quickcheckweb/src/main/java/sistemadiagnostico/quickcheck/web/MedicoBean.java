package sistemadiagnostico.quickcheck.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;
import quickcheckmodel.dto.ClinicaDTO;
import quickcheckmodel.dto.MedicoDTO;
import quickcheckmodel.service.ClinicaService;
import quickcheckmodel.service.EmailService;
import quickcheckmodel.service.MedicoService;

@SessionScoped
@ManagedBean
public class MedicoBean {
    private MedicoDTO medico = new MedicoDTO();
    private MedicoService medicoService = new MedicoService();

    private ClinicaDTO clinica = new ClinicaDTO();
    private ClinicaService clinicaService = new ClinicaService();

    private EmailService emailService = new EmailService(); 
    private String senha;

    private List<MedicoDTO> medicos = new ArrayList<>();

    public void inserirOuAtualizarClinica() throws ClassNotFoundException {
        clinica.setCpfmedico(medico.getCpf());
        clinicaService.inserirOuAtualizarClinica(clinica);
    }

    public String inserirMedico() throws ClassNotFoundException, SQLException {
        medicoService.cadastrarMedico(medico);
        emailService.confimarCadastro(medico.getEmail(), medico.getNome());
        medico = new MedicoDTO();
        return "/loginFunc.xhtml?faces-redirect=true";
    }

    public void listar() {
        medicos = medicoService.listar();
    }
    
    public void login() throws IOException, ClassNotFoundException {
        medico = medicoService.login(medico.getCpf(), medico.getSenha());
        if (medico != null) {
            HttpSession session = (HttpSession)FacesContext.getCurrentInstance( ).getExternalContext().getSession(false);
            session.setAttribute("usuario", medico);
            if (clinicaService.obterClinicaPorCPF(medico.getCpf()) != null) {
                clinica = clinicaService.obterClinicaPorCPF(medico.getCpf());
            }
            else {
                clinica = new ClinicaDTO("", "", "", new String[0], "", "", "");
            }
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath() + "/faces/inicioMedico.xhtml?faces-redirect=true");
        }
        else {
            medico = new MedicoDTO();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Usuário ou senha incorretos");
            FacesContext.getCurrentInstance().addMessage(null,message);
        }
    }
    
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }
    
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "./loginFunc.xhtml?faces-redirect=true";
    }

    public MedicoDTO getMedico() {
        return medico;
    }

    public void setMedico(MedicoDTO medico) {
        this.medico = medico;
    }

    public List<MedicoDTO> getMedicos() {
        return medicos;
    }

    public void setMedicos(List<MedicoDTO> medicos) {
        this.medicos = medicos;
    }

    public ClinicaDTO getClinica(){
        return clinica;
    }

    public void setClinica(ClinicaDTO clinica){
        this.clinica = clinica;
    }

}
