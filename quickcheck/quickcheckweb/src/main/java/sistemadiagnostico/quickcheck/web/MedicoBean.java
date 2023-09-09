package sistemadiagnostico.quickcheck.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
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
import quickcheckmodel.dto.*;
import quickcheckmodel.service.*;

@SessionScoped
@ManagedBean
public class MedicoBean {
    private MedicoDTO medico = new MedicoDTO();
    private MedicoService medicoService = new MedicoService();
    private ClinicaDTO clinica = new ClinicaDTO();
    private ClinicaService clinicaService = new ClinicaService();
    private ConsultaDTO consulta = new ConsultaDTO();
    private ConsultaService consultaService = new ConsultaService();
    private DocumentoService documentoService = new DocumentoService();
    private EmailService emailService = new EmailService();
    private PacienteService pacienteService = new PacienteService();

    private List<MedicoDTO> medicos = new ArrayList<>();
    private List<PacienteDTO> pacientes = new ArrayList<>();
    private List<DocumentoDTO> documentos = new ArrayList<>();
    private List<ConsultaDTO> consultas = new ArrayList<>();

    private MapModel model;
    private Marker<String> marker;
    private List<ConsultaDTO> consultasFiltradas;

    public void removerConsulta(ConsultaDTO consulta) {
        consultaService.removerConsultaMedico(consulta);
        carregarConsultas();
        emailService.cancelarConsultaMedico(consulta, medico.getNome());
        addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Consulta agendada");
    }

    public void consultasMedico() throws IOException {
        carregarConsultas();
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath() + "/faces/consultaMedico.xhtml?faces-redirect=true");
    }

    public void carregarDocumentos(String cpf) {
        documentos = documentoService.listar(cpf);
    }

    public void inserirOuAtualizarClinica() throws ClassNotFoundException {
        try {
            clinica.setCpfmedico(medico.getCpf());
            clinica.setNomemedico(medico.getNome());
            clinica = clinicaService.inserirOuAtualizarClinica(clinica);
            carregarMapa(clinica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void carregarConsultas() {
        consultas = consultaService.listarConsultasMedicos(medico.getCpf());
    }

    public void carregarMapa(ClinicaDTO clinica) {
        model = new DefaultMapModel<>();
        String[] coordenadas = clinica.getCoordenada().split(",");
        LatLng coord = new LatLng(Double.parseDouble(coordenadas[0]), Double.parseDouble(coordenadas[1]));
        model.addOverlay(new Marker<>(coord, clinica.getNome(), clinica.getEndereco()));
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
    
    public void login() throws IOException, ClassNotFoundException, SQLException {
        medico = medicoService.login(medico.getCpf(), medico.getSenha());
        if (medico != null) {
            HttpSession session = (HttpSession)FacesContext.getCurrentInstance( ).getExternalContext().getSession(false);
            session.setAttribute("usuario", medico);
            if (clinicaService.obterClinicaPorCPF(medico.getCpf()) != null) {
                clinica = clinicaService.obterClinicaPorCPF(medico.getCpf());
                carregarMapa(clinica);
                pacientes = documentoService.listarPacientes(medico.getCpf());
            }
            else {
                clinica = new ClinicaDTO("", "", "", new String[0], "", "", "", "");
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

    public List<ConsultaDTO> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<ConsultaDTO> consultas) {
        this.consultas = consultas;
    }

    public List<ConsultaDTO> getConsultasFiltradas() {
        return consultasFiltradas;
    }

    public void setConsultasFiltradas(List<ConsultaDTO> consultasFiltradas) {
        this.consultasFiltradas = consultasFiltradas;
    }
}
