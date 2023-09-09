package quickcheckmodel.service;

import quickcheckmodel.dao.ConsultaDAO;
import quickcheckmodel.dao.EmailDAO;
import quickcheckmodel.dto.ConsultaDTO;
import quickcheckmodel.dto.EmailDTO;
import quickcheckmodel.dto.PacienteDTO;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class EmailService {
    EmailDTO emailDTO;
    public void confimarCadastro(String email, String nome) {
        String assunto, mensagem;
        assunto = "Cadastro Realizado - QuickCheck";
        mensagem = "<!DOCTYPE html>"
            + "<html>"
            + "<head>"
            + "<style>"
            + "body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }"
            + ".container { max-width: 600px; margin: 0 auto; padding: 20px; }"
            + ".logo { text-align: center; }"
            + "img { max-width: 150px; height: auto; }"
            + ".message { background-color: #ffffff; padding: 20px; border-radius: 5px; box-shadow: 0px 10px 15px -3px rgba(0,0,0,0.1); }"
            + "</style>"
            + "</head>"
            + "<body>"
            + "<div class='container'>"
            + "<div class='logo'><img src='https://i.imgur.com/kmygeVq.png' alt='Logo QuickCheck'></div>"
            + "<div class='message'>"
            + "<h2>Olá, " + nome + "!</h2>"
            + "<p>Agradecemos por se cadastrar no QuickCheck</p>"
            + "<p>Estamos comprometidos em oferecer um processo eficiente e seguro para atender às suas necessidades.</p>"
            + "<p>Fique à vontade para explorar todos os recursos que oferecemos.</p>"
            + "<p>Atenciosamente,</p>"
            + "<p>A equipe QuickCheck</p>"
            + "</div>"
            + "</div>"
            + "</body>"
            + "</html>";
        emailDTO = new EmailDTO(email, assunto, mensagem);
        EmailDAO.enviarEmail(emailDTO);
    }
    
    public void agendamento(PacienteDTO paciente, ConsultaDTO consulta) {

        String assunto, mensagem;
        assunto = "Dia de Consulta - QuickCheck";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = dateFormat.format(consulta.getData());
        mensagem = "<!DOCTYPE html>"
            + "<html>"
            + "<head>"
            + "<style>"
            + "body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }"
            + ".container { max-width: 600px; margin: 0 auto; padding: 20px; }"
            + ".logo { text-align: center; }"
            + "img { max-width: 150px; height: auto; }"
            + ".message { background-color: #ffffff; padding: 20px; border-radius: 5px; box-shadow: 0px 10px 15px -3px rgba(0,0,0,0.1); }"
            + "</style>"
            + "</head>"
            + "<body>"
            + "<div class='container'>"
            + "<div class='logo'><img src='https://i.imgur.com/kmygeVq.png' alt='Logo QuickCheck'></div>"
            + "<div class='message'>"
            + "<h2>Olá, " + paciente.getNome() + "!</h2>"
            + "<p>A sua consulta para " + consulta.getEspecialidade() + " está agendada para o dia de " + formattedDate + " às " + consulta.getHorario() + " com " + consulta.getNome() + ".</p>"
            + "<p>Para obter mais informações, acesse novamente nosso site QuickCheck.</p>"
            + "<p>Desejamos uma boa consulta!</p>"
            + "<p>Atenciosamente,</p>"
            + "<p>A equipe QuickCheck</p>"
            + "</div>"
            + "</div>"
            + "</body>"
            + "</html>";
        emailDTO = new EmailDTO(paciente.getEmail(), assunto, mensagem);
        EmailDAO.enviarEmail(emailDTO);
    }

    public void cancelarConsulta(PacienteDTO pacienteDTO, ConsultaDTO consultaDTO) {
        String assunto = "Cancelamento de Consulta - QuickCheck";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = dateFormat.format(consultaDTO.getData());
        String mensagem = "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<style>"
                + "body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }"
                + ".container { max-width: 600px; margin: 0 auto; padding: 20px; }"
                + ".logo { text-align: center; }"
                + "img { max-width: 150px; height: auto; }"
                + ".message { background-color: #ffffff; padding: 20px; border-radius: 5px; box-shadow: 0px 10px 15px -3px rgba(0,0,0,0.1); }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class='container'>"
                + "<div class='logo'><img src='https://i.imgur.com/kmygeVq.png' alt='Logo QuickCheck'></div>"
                + "<div class='message'>"
                + "<h2>Cancelamento de Consulta</h2>"
                + "<h2>Olá, " + pacienteDTO.getNome() + "!</h2>"
                + "<p>Sua consulta agendada para o dia " + formattedDate + " às " + consultaDTO.getHorario() + " com  " + consultaDTO.getNome() + " na especialidade de " + consultaDTO.getEspecialidade() + " foi cancelada com sucesso.</p>"
                + "<p>Para obter mais informações ou reagendar a consulta, acesse novamente.</p>"
                + "<p>Atenciosamente,</p>"
                + "<p>A equipe QuickCheck</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
        emailDTO = new EmailDTO(pacienteDTO.getEmail(), assunto, mensagem);
        EmailDAO.enviarEmail(emailDTO);
    }

    public void cancelarConsultaMedico(ConsultaDTO consultaDTO, String nomeMedico) {
        String assunto = "Cancelamento de Consulta - QuickCheck";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = dateFormat.format(consultaDTO.getData());
        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO = ConsultaDAO.getPacientePorConsulta(consultaDTO);
        String mensagem = "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<style>"
                + "body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }"
                + ".container { max-width: 600px; margin: 0 auto; padding: 20px; }"
                + ".logo { text-align: center; }"
                + "img { max-width: 150px; height: auto; }"
                + ".message { background-color: #ffffff; padding: 20px; border-radius: 5px; box-shadow: 0px 10px 15px -3px rgba(0,0,0,0.1); }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class='container'>"
                + "<div class='logo'><img src='https://i.imgur.com/kmygeVq.png' alt='Logo QuickCheck'></div>"
                + "<div class='message'>"
                + "<h2>Cancelamento de Consulta</h2>"
                + "<h2>Olá, " + consultaDTO.getNome() + "!</h2>"
                + "<p>Infelizmente, precisamos informar que sua consulta agendada para o dia " + formattedDate + " às " + consultaDTO.getHorario() + " com " + nomeMedico + " na especialidade de " + consultaDTO.getEspecialidade() + " teve que ser cancelada pelo médico.</p>"
                + "<p>Para obter mais informações, entre acesse novamente o nosso site.</p>"
                + "<p>Atenciosamente,</p>"
                + "<p>A equipe QuickCheck</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
        emailDTO = new EmailDTO(pacienteDTO.getEmail(), assunto, mensagem);
        EmailDAO.enviarEmail(emailDTO);
    }
}
