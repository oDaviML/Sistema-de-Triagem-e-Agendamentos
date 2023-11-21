package quickcheckmodel.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor
public class EmailDTO {
    private String email, assunto, mensagem;

    public EmailDTO(String email, String assunto, String mensagem) {
        this.email = email;
        this.assunto = assunto;
        this.mensagem = mensagem;
    }
}
