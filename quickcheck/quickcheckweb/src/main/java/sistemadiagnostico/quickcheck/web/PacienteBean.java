package sistemadiagnostico.quickcheck.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.event.RowEditEvent;
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
import quickcheckmodel.dto.PacienteDTO;
import quickcheckmodel.service.ClinicaService;
import quickcheckmodel.service.DocumentoService;
import quickcheckmodel.service.EmailService;
import quickcheckmodel.service.PacienteService;

@SessionScoped
@ManagedBean
public class PacienteBean {
    private PacienteDTO paciente = new PacienteDTO();
    private DocumentoDTO documento = new DocumentoDTO();
    private PacienteService pacienteService = new PacienteService();
    private DocumentoService documentoService = new DocumentoService();
    private EmailService emailService = new EmailService();
    private ClinicaService clinicaService = new ClinicaService();

    private List<PacienteDTO> pacientes = new ArrayList<>();
    private List<DocumentoDTO> documentos = new ArrayList<>();
    private List<ClinicaDTO> clinicas = new ArrayList<>();

    private String coordenadaEndereco;

    private MapModel model;
    private Marker<ClinicaDTO> marker;

    public void carregarClinicas() throws ClassNotFoundException, NumberFormatException, IOException {
        clinicas = clinicaService.listarClinicas();
        model = new DefaultMapModel<>();
        for (ClinicaDTO clinicaDTO : clinicas) {
            String[] coordenadas = clinicaDTO.getCoordenada().split(",");
            LatLng coord = new LatLng(Double.parseDouble(coordenadas[0]), Double.parseDouble(coordenadas[1]));
            marker = new Marker<>(coord, clinicaDTO.getNome());
            marker.setData(clinicaDTO);
            model.addOverlay(marker);
        }
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath() + "/faces/consultaPaciente.xhtml?faces-redirect=true");
    }

    public void inserirDocumento() {
        documento.setCpf(paciente.getCpf());
 
        documentoService.inserirDocumento(documento);
        documento = new DocumentoDTO();
        documentos = documentoService.listar(paciente.getCpf());
        addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Documento inserido");
    }

    public void atualizarDocumento(RowEditEvent<DocumentoDTO> event) {
        DocumentoDTO documento = event.getObject();
        documentoService.atualizarDocumento(documento);
        documento = new DocumentoDTO();
        documentos = documentoService.listar(paciente.getCpf());
        addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Documento atualizado");
    }

    public void removerDocumento(DocumentoDTO event) {
        DocumentoDTO documento = event;

        documento.setCpf(paciente.getCpf());
        documentoService.removerDocumento(documento);
        documento = new DocumentoDTO();
        documentos = documentoService.listar(paciente.getCpf());
        addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Documento removido");
    }
    public String inserirPaciente() {
        pacienteService.cadastrarPaciente(paciente);
        //emailService.confimarCadastro(paciente.getEmail(),paciente.getNome());
        paciente = new PacienteDTO();
        return "/loginPaciente.xhtml?faces-redirect=true";
    }

    public void listar() {
        pacientes = pacienteService.listar();
    }
    
    public void login() throws IOException {
        paciente = pacienteService.login(paciente.getCpf(), paciente.getSenha());
        if (paciente != null) {
            HttpSession session = (HttpSession)FacesContext.getCurrentInstance( ).getExternalContext().getSession(false);
            session.setAttribute("usuario", paciente);
            documentos = documentoService.listar(paciente.getCpf());
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath() + "/faces/inicioPaciente.xhtml?faces-redirect=true");
        }
        else {
            paciente = new PacienteDTO();
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
            context.redirect(context.getRequestContextPath() + "/faces/loginPaciente.xhtml?faces-redirect=true");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PacienteDTO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDTO paciente) {
        this.paciente = paciente;
    }

    public DocumentoDTO getDocumento() {
        return documento;
    }
    public void setDocumento(DocumentoDTO documento) {
        this.documento = documento;
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

    public String getCoordenadaEndereco() {
        return pacienteService.obterCoordenada(paciente.getEndereco());
    }

    public void setCoordenadaEndereco(String coordenadaEndereco) {
        this.coordenadaEndereco = coordenadaEndereco;
    }

    public MapModel getModel() { 
        return model; 
    }
    public Marker<ClinicaDTO> getMarker() { 
        return marker; 
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        this.marker = (Marker) event.getOverlay();
    }
}
