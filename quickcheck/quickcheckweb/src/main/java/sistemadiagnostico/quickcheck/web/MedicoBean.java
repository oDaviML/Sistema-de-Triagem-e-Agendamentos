package sistemadiagnostico.quickcheck.web;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import quickcheckmodel.dto.*;
import quickcheckmodel.service.*;

import java.io.*;
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
    private SenhaService senhaService = new SenhaService();
    private DoencaDTO doenca = new DoencaDTO();
    private DoencaService doencaService = new DoencaService();

    private List<MedicoDTO> medicos = new ArrayList<>();
    private List<PacienteDTO> pacientes = new ArrayList<>();
    private List<DocumentoDTO> documentos = new ArrayList<>();
    private List<ConsultaDTO> consultas = new ArrayList<>();
    private List<DoencaDTO> doencas = new ArrayList<>();

    private String senhaAntiga, novaSenha, key;
    private String[] inputsRecuperarSenha = new String[6];
    private MapModel model;
    private Marker<String> marker;
    private List<ConsultaDTO> consultasFiltradas;
    private Boolean edit = false;
    private Boolean editSenha = false;

    public void recuperarSenha() throws IOException {
        medicoService.alterarSenha(medico);
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath() + "/faces/lgnMedico.xhtml?faces-redirect=true");
        emailService.alterarSenha(medico.getEmail(), medico.getNome());
        addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Senha alterada");
        medico = new MedicoDTO();
    }

    public void verificarCodigo() {
        String recuperarSenhaString = String.join("", inputsRecuperarSenha);
        if (recuperarSenhaString.equals(key)) {
            PrimeFaces.current().executeScript("alterarVisibilidade();");
        }
        else {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Código inválido");
        }
    }

    public void enviarEmailRecuperarSenha() {
        medico = medicoService.verificar(medico);
        if (medico != null) {
            key = senhaService.generateRandomKey();
            emailService.recuperarSenha(medico.getEmail(), key);
            PrimeFaces.current().executeScript("setupCodeInputs();");
            PrimeFaces.current().executeScript("alterarVisibilidade();");
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Email enviado");
        } else {
            medico = new MedicoDTO();
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Usuario inexistente");
        }
    }


    public void editarSenha() {
        if (senhaService.verificar(medico.getCpf(), senhaAntiga, 1)) {
            try {
                medico.setSenha(novaSenha);
                medicoService.alterarSenha(medico);
                editSenha = !editSenha;
                emailService.alterarSenha(medico.getEmail(), medico.getNome());
                addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Senha atualizada");
            } catch (Exception e) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao atualizar senha");
                e.printStackTrace();
            }
        } else {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Senha antiga inválida");
        }
    }

    public void editarPerfil() {
        try {
            medicoService.editar(medico);
            edit = !edit;
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Informações atualizadas");
        }catch (Exception e){
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao atualizar informações");
            e.printStackTrace();
        }
    }

    public void habilitarEdicaoSenha() {
        editSenha = !editSenha;
    }

    public void habilitarEdicao() {
        edit = !edit;
        PrimeFaces.current().executeScript("completarEndereço();");
    }

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

    public StreamedContent baixarArquivo(DocumentoDTO documento) throws FileNotFoundException {
        File file = documentoService.baixarArquivo(documento.getNomeArquivo());
        InputStream inputStream = new FileInputStream(file);
        String fileName = file.getName();
        return DefaultStreamedContent.builder().name(fileName).stream(() -> inputStream).build();
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
                pacientes = pacienteService.listarPacientes(medico.getCpf());
                carregarDoencas();
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
    
    public void inserirDoenca() {
        try {
            doenca.setCpfMedico(medico.getCpf());
            doenca.setNomeMedico(medico.getNome());
            doenca = doencaService.inserirDoenca(doenca);
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Doença cadastrada");
            carregarDoencas();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao cadastrar doença");
        }
    }
    
    public void removerDoenca(DoencaDTO event) throws SQLException, ClassNotFoundException {
        doencaService.removerDoenca(event);
        addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Doença removida");
        carregarDoencas();
    }
    
    public void carregarDoencas() throws ClassNotFoundException {
        doencas = doencaService.listarDoencas(medico.getCpf());
    }
    
    
    
}
