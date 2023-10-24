package sistemadiagnostico.quickcheck.web;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import jakarta.faces.event.AjaxBehaviorEvent;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
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
import quickcheckmodel.dto.*;
import quickcheckmodel.service.ClinicaService;
import quickcheckmodel.service.ConsultaService;
import quickcheckmodel.service.DocumentoService;
import quickcheckmodel.service.EmailService;
import quickcheckmodel.service.PacienteService;

@SessionScoped
@ManagedBean
@Getter@Setter
public class PacienteBean {
    private PacienteDTO paciente = new PacienteDTO();
    private DocumentoDTO documento = new DocumentoDTO();
    private ConsultaDTO consulta = new ConsultaDTO();
    private ClinicaDTO clinica = new ClinicaDTO();
    private TriagemDTO triagem = new TriagemDTO();

    private PacienteService pacienteService = new PacienteService();
    private DocumentoService documentoService = new DocumentoService();
    private EmailService emailService = new EmailService();
    private ClinicaService clinicaService = new ClinicaService();
    private ConsultaService consultaService = new ConsultaService();

    private List<PacienteDTO> pacientes = new ArrayList<>();
    private List<DocumentoDTO> documentos = new ArrayList<>();
    private List<ClinicaDTO> clinicas = new ArrayList<>();
    private List<ConsultaDTO> consultasMedico = new ArrayList<>();
    private List<ConsultaDTO> consultas = new ArrayList<>();
    private String[] horariosArray = {"8:00", "8:30","9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00"};
    private List<String> horarios = new ArrayList<>();
    private String[] conveniosArray = {"Unimed", "Amil", "NotreDame", "Ipsemg", "Medsênior", "Particular"};
    private List<String> convenios = new ArrayList<>();

    private String coordenadaEndereco;
    private MapModel model;
    private Marker<ClinicaDTO> marker;
    private Date dataSelecionada;
    private Date dataAtual = new Date();


    public String resultadoTriagem () {
        System.out.println(triagem.toString());
        return "/loginPaciente.xhtml?faces-redirect=true";
    }

    public void removerConsulta(ConsultaDTO event) throws SQLException {
        consultaService.removerConsultaPaciente(event);
        carregarConsultas();
        emailService.cancelarConsulta(paciente, event);
        addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Consulta desmarcada");
    }

    public void carregarConsultas() {
        consultas = consultaService.listarConsultaPaciente(paciente.getCpf());
    }

    public void onMarkerSelect(@NotNull OverlaySelectEvent event) {
        this.marker = (Marker) event.getOverlay();
        this.clinica = (ClinicaDTO) marker.getData();
        convenios = Arrays.asList(conveniosArray);
        carregarConvenios();
    }

    public void onFiltroSelect(@NotNull AjaxBehaviorEvent event) {
        String especialidade = (String) event.getComponent().findComponent("especialidade").getAttributes().get("value");
        String convenio = (String) event.getComponent().findComponent("convenio").getAttributes().get("value");

        if (especialidade == null) {
            especialidade = "Todos";
        }
        if (convenio == null) {
            convenio = "Todos";
        }

        model = new DefaultMapModel<>();
        if (convenio.equals("Todos") && especialidade.equals("Todos")) {
            for (ClinicaDTO clinicaDTO : clinicas) {
                String[] coordenadas = clinicaDTO.getCoordenada().split(",");
                LatLng coord = new LatLng(Double.parseDouble(coordenadas[0]), Double.parseDouble(coordenadas[1]));
                Marker<ClinicaDTO> marker = new Marker<>(coord, clinicaDTO.getNome());
                marker.setData(clinicaDTO);
                model.addOverlay(marker);
            }
        } else {
            for (ClinicaDTO clinicaDTO : clinicas) {
                List<String> conveniosCopy = Arrays.asList(clinicaDTO.getConvenios());
                if ((convenio.equals("Todos") || conveniosCopy.contains(convenio)) &&
                        (especialidade.equals("Todos") || clinicaDTO.getEspecialidade().equals(especialidade))) {
                    String[] coordenadas = clinicaDTO.getCoordenada().split(",");
                    LatLng coord = new LatLng(Double.parseDouble(coordenadas[0]), Double.parseDouble(coordenadas[1]));
                    Marker<ClinicaDTO> marker = new Marker<>(coord, clinicaDTO.getNome());
                    marker.setData(clinicaDTO);
                    model.addOverlay(marker);
                }
            }
        }
    }

    public void onDateSelect(@NotNull SelectEvent<Date> event) {
        carregarHorarios(event.getObject());
    }

    public void carregarHorarios(Date data) {
        horarios = new ArrayList<>(Arrays.asList(horariosArray));
        consultasMedico = consultaService.listarConsultasMedicos(clinica.getCpfmedico());
        for (ConsultaDTO consulta : consultasMedico) {
            if (consulta.getData().equals(data)) {
                horarios.remove(consulta.getHorario());
            }
        }
    }

    public void carregarConvenios() {
        List<String> conveniosCopy = new ArrayList<>(convenios);
        conveniosCopy.retainAll(Arrays.asList(clinica.getConvenios()));
        convenios = conveniosCopy; 
    }

    public void cadastrarConsulta() throws ClassNotFoundException, SQLException {
        consulta.setCpfpaciente(paciente.getCpf());
        consulta.setCpfmedico(clinica.getCpfmedico());
        consulta.setEspecialidade(clinica.getEspecialidade());
        consultaService.inserirConsultaPaciente(consulta);
        consulta.setNome(clinica.getNomemedico());
        emailService.agendamento(paciente, consulta);
        carregarConsultas();
        carregarHorarios(consulta.getData());

        addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Consulta agendada");
    }

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
        emailService.confimarCadastro(paciente.getEmail(),paciente.getNome());
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
            carregarConsultas();
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

    public String getCoordenadaEndereco() {
        return pacienteService.obterCoordenada(paciente.getEndereco());
    }
}
