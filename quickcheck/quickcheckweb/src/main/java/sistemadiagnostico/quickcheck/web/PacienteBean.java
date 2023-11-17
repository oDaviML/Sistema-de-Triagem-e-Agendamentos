package sistemadiagnostico.quickcheck.web;

import com.amazonaws.services.s3.model.ObjectMetadata;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import quickcheckmodel.dto.*;
import quickcheckmodel.service.*;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SessionScoped
@ManagedBean
@Getter
@Setter
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
    private TriagemService triagemService = new TriagemService();
    private ResultadoTriagemService resultadoTriagemService = new ResultadoTriagemService();

    private List<PacienteDTO> pacientes = new ArrayList<>();
    private List<DocumentoDTO> documentos = new ArrayList<>();
    private List<ClinicaDTO> clinicas = new ArrayList<>();
    private List<ConsultaDTO> consultasMedico = new ArrayList<>();
    private List<ConsultaDTO> consultas = new ArrayList<>();
    private final String[] horariosArray = {"8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00"};
    private List<String> horarios = new ArrayList<>();
    private final String[] conveniosArray = {"Unimed", "Amil", "NotreDame", "Ipsemg", "Medsênior", "Particular"};
    private List<String> convenios = new ArrayList<>();
    private List<ResultadoTriagemDTO> resultadosTriagem = new ArrayList<>();


    private String coordenadaEndereco;
    private MapModel model;
    private Marker<ClinicaDTO> marker;
    private Date dataSelecionada;
    private Date dataAtual = new Date();
    private int progresso = 0;
    private UploadedFile file;
    private String resultadoTriagemstr;

    public void resetarTriagem() {
        resultadoTriagemstr = "";
        progresso = 0;
    }

    public void resultadoTriagem() {
        resultadoTriagemstr = triagemService.resultadoTriagem(triagem, paciente);
        progresso = 100;
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
        try {
            if (file != null) {
                documento.setNome(file.getFileName());
                documento.setCpf(paciente.getCpf());
                documento.setTamanho(file.getSize());
                InputStream inputStream = file.getInputStream();

                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(file.getContentType());
                metadata.setContentLength(file.getSize());
                metadata.setHeader("filename", file.getFileName());

                documentoService.inserirDocumento(documento, inputStream, metadata, paciente);
                documentos = documentoService.listar(paciente.getCpf());
                addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Documento inserido");
            }
            else {
                throw new Exception();
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Selecione um arquivo");
            e.printStackTrace();
        }
    }

    public void removerDocumento(DocumentoDTO documento) {
        documentoService.removerDocumento(documento);
        documentos = documentoService.listar(paciente.getCpf());
        addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Documento removido");
    }

    public StreamedContent baixarArquivo(DocumentoDTO documento) throws FileNotFoundException {
        File file = documentoService.baixarArquivo(documento.getNomeArquivo());
        InputStream inputStream = new FileInputStream(file);
        String fileName = file.getName();
        return DefaultStreamedContent.builder().name(fileName).stream(() -> inputStream).build();
    }


    public String inserirPaciente() {
        try {
            pacienteService.cadastrarPaciente(paciente);
            emailService.confimarCadastro(paciente.getEmail(), paciente.getNome());
            paciente = new PacienteDTO();
            return "/lgnPaciente.xhtml?faces-redirect=true";
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Usuario ja existe");
            e.printStackTrace();
            return null;
        }
    }

    public void listar() {
        pacientes = pacienteService.listar();
    }

    public void login() throws IOException {
        paciente = pacienteService.login(paciente.getCpf(), paciente.getSenha());
        if (paciente != null) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("usuario", paciente);
            documentos = documentoService.listar(paciente.getCpf());
            resultadosTriagem = resultadoTriagemService.listar(paciente);
            carregarConsultas();
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath() + "/faces/inicioPaciente.xhtml?faces-redirect=true");
        } else {
            paciente = new PacienteDTO();
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
            context.redirect(context.getRequestContextPath() + "/faces/lgnPaciente.xhtml?faces-redirect=true");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCoordenadaEndereco() {
        return pacienteService.obterCoordenada(paciente.getEndereco());
    }
}
