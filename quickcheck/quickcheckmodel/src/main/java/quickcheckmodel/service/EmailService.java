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
}
