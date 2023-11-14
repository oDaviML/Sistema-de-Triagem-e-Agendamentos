package sistemadiagnostico.quickcheck.web;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import quickcheckmodel.dto.*;
import quickcheckmodel.service.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@ManagedBean
@Getter
@Setter
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
        emailService.cancelarConsultaMedico(consulta, medico.getNome());
        carregarConsultas();
        addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Consulta desmarcada");
    }

    public void consultasMedico() throws IOException {
        carregarConsultas();
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath() + "/faces/consultaMedico.xhtml?faces-redirect=true");
    }

    public void carregarDocumentos(String cpf) {
        documentos = documentoService.listar(cpf);
    }

    public void inserirOuAtualizarClinica() {
        try {
            clinica.setCpfmedico(medico.getCpf());
            clinica.setNomemedico(medico.getNome());
            clinica = clinicaService.inserirOuAtualizarClinica(clinica);
            carregarMapa(clinica);
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Clinica atualizada");
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao atualizar clinica");
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
        try {
            medicoService.cadastrarMedico(medico);
            emailService.confimarCadastro(medico.getEmail(), medico.getNome());
            medico = new MedicoDTO();
            return "/lgnMedico.xhtml?faces-redirect=true";
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Usuário já existe");
            e.printStackTrace();
            return null;
        }
    }

    public void login() throws IOException, ClassNotFoundException, SQLException {
        medico = medicoService.login(medico.getCpf(), medico.getSenha());
        if (medico != null) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("usuario", medico);
            if (clinicaService.obterClinicaPorCPF(medico.getCpf()) != null) {
                clinica = clinicaService.obterClinicaPorCPF(medico.getCpf());
                carregarMapa(clinica);
                pacientes = documentoService.listarPacientes(medico.getCpf());
            } else {
                clinica = new ClinicaDTO("", "", "", new String[0], "", "", "", "");
            }
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath() + "/faces/inicioMedico.xhtml?faces-redirect=true");
        } else {
            medico = new MedicoDTO();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Usuário ou senha incorretos");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public void logout() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath() + "/faces/lgnMedico.xhtml?faces-redirect=true");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        this.marker = (Marker) event.getOverlay();
    }
}
