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
import org.primefaces.event.RowEditEvent;
import quickcheckmodel.dto.DocumentoDTO;
import quickcheckmodel.dto.PacienteDTO;
import quickcheckmodel.service.DocumentoService;
import quickcheckmodel.service.PacienteService;

@SessionScoped
@ManagedBean
public class PacienteBean {
    private PacienteDTO paciente = new PacienteDTO();
    private DocumentoDTO documento = new DocumentoDTO();
    private PacienteService pacienteService = new PacienteService();
    private DocumentoService documentoService = new DocumentoService();

    private List<PacienteDTO> pacientes = new ArrayList<>();
    private List<DocumentoDTO> documentos = new ArrayList<>();

    public void inserirDocumento() {
        documento.setCpf(paciente.getCpf());
        documentoService.inserirDocumento(documento);
        documento = new DocumentoDTO();
        documentos = documentoService.listar(paciente.getCpf());
    }

    public void atualizarDocumento(RowEditEvent<DocumentoDTO> event) {
        DocumentoDTO documento = event.getObject();
        documentoService.atualizarDocumento(documento);
        documento = new DocumentoDTO();
        documentos = documentoService.listar(paciente.getCpf());
    }

    public void removerDocumento(DocumentoDTO event) {
        DocumentoDTO documento = event;

        documento.setCpf(paciente.getCpf());
        documentoService.removerDocumento(documento);
        documento = new DocumentoDTO();
        documentos = documentoService.listar(paciente.getCpf());
    }
    public String inserirPaciente() {
        pacienteService.cadastrarPaciente(paciente);
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
}
