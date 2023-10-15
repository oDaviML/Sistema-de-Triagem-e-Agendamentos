package quickcheckmodel.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter@Setter
@NoArgsConstructor
public class PacienteDTO {
    private String nome, endereco, email, convenio, cpf, telefone, senha;
    private Date datanascimento;

    public PacienteDTO(String nome, String endereco, String email, String convenio, String cpf, String telefone,
            String senha, Date datanascimento) {
        this.nome = nome;
        this.endereco = endereco;
        this.email = email;
        this.convenio = convenio;
        this.cpf = cpf;
        this.telefone = telefone;
        this.senha = senha;
        this.datanascimento = datanascimento;
    }
}
