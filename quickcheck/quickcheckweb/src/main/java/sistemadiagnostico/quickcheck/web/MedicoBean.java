package sistemadiagnostico.quickcheck.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;
import quickcheckmodel.dto.ClinicaDTO;
import quickcheckmodel.dto.DocumentoDTO;
import quickcheckmodel.dto.MedicoDTO;
import quickcheckmodel.dto.PacienteDTO;
import quickcheckmodel.service.ClinicaService;
import quickcheckmodel.service.DocumentoService;
import quickcheckmodel.service.EmailService;
import quickcheckmodel.service.MedicoService;
import quickcheckmodel.service.PacienteService;
@SessionScoped
@ManagedBean
public class MedicoBean {
    private MedicoDTO medico = new MedicoDTO();
    private MedicoService medicoService = new MedicoService();

    private ClinicaDTO clinica = new ClinicaDTO();
    private ClinicaService clinicaService = new ClinicaService();

  
    private DocumentoService documentoService = new DocumentoService();

    private EmailService emailService = new EmailService();
    private PacienteService pacienteService = new PacienteService();
    private String senha;

    private List<MedicoDTO> medicos = new ArrayList<>();
    private List<PacienteDTO> pacientes = new ArrayList<>();
    private List<DocumentoDTO> documentos = new ArrayList<>();

    private MapModel model;
    private Marker<String> marker;

    public void carregarDocumentos(String cpf) {
        documentos = documentoService.listar(cpf);
    }

    public void inserirOuAtualizarClinica() throws ClassNotFoundException {
        try {
            clinica.setCpfmedico(medico.getCpf());
            clinica = clinicaService.inserirOuAtualizarClinica(clinica);
            carregarMapa(clinica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void carregarMapa(ClinicaDTO clinica) {
        model = new DefaultMapModel<>();
        String[] coordenadas = clinica.getCoordenada().split(",");
        LatLng coord = new LatLng(Double.parseDouble(coordenadas[0]), Double.parseDouble(coordenadas[1]));
        model.addOverlay(new Marker<>(coord, clinica.getNome(), clinica.getEndereco()));
    }

    public String inserirMedico() throws ClassNotFoundException, SQLException {
        medicoService.cadastrarMedico(medico);
        //emailService.confimarCadastro(medico.getEmail(), medico.getNome());
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
                carregarMapa(clinica);
                pacientes = pacienteService.listar();
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
    
    public void logout() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath() + "/faces/loginFunc.xhtml?faces-redirect=true");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public MapModel getModel() { 
        return model; 
    }
    public Marker getMarker() { 
        return marker; 
    }
    public void onMarkerSelect(OverlaySelectEvent event) {
        this.marker = (Marker) event.getOverlay();
    }

    public List<PacienteDTO> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<PacienteDTO> pacientes) {
        this.pacientes = pacientes;
    }

    public List<DocumentoDTO> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentoDTO> documentos) {
        this.documentos = documentos;
    }
}
