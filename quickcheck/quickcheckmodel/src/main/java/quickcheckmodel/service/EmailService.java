package quickcheckmodel.service;

import quickcheckmodel.dao.EmailDAO;

public class EmailService {
    public void confimarCadastro(String email, String nome) {
        System.out.println(nome);
        System.out.println(email);
        String assunto, mensagem;
        assunto = "Cadastro Realizado - QuickCheck";
        String logoUrl = "https://i.imgur.com/kmygeVq.png"; // Substitua pela URL da logo da aplicação
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
            + "<div class='logo'><img src='" + logoUrl + "' alt='Logo QuickCheck'></div>"
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
        EmailDAO.enviarEmail(email, assunto, mensagem);
    }
    
    public void horarioConsulta(String email, String nome, String assuntoConsulta, String dataConsulta, String enderecoConsulta, String emailConsulta, String telefoneConsulta) {
        System.out.println(nome);
        System.out.println(email);
        String assunto, mensagem;
        assunto = "Dia de Consulta - QuickCheck";
        String logoUrl = "https://i.imgur.com/kmygeVq.png"; // Substitua pela URL da logo da aplicação
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
            + "<div class='logo'><img src='" + logoUrl + "' alt='Logo QuickCheck'></div>"
            + "<div class='message'>"
            + "<h2>Olá, " + nome + "!</h2>"
            + "<p>A data da sua consulta chegou!</p>"
            + "<p>A sua consulta de" + assuntoConsulta + " está agendada para o dia de hoje" + dataConsulta + ". O endereço do local é" + enderecoConsulta + " com o email de contato" + emailConsulta + " e telefone "+ telefoneConsulta +". </p>"
            + "<p>Desejamos uma boa consulta!</p>"
            + "<p>Atenciosamente,</p>"
            + "<p>A equipe QuickCheck</p>"
            + "</div>"
            + "</div>"
            + "</body>"
            + "</html>";
        EmailDAO.enviarEmail(email, assunto, mensagem);
    }
}
