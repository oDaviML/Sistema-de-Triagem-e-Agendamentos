package quickcheckmodel.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter@Setter
@NoArgsConstructor
public class MedicoDTO {
    private String nome, senha, endereco, email, crm, cpf, telefone;
    private Date dataNascimento;
    private String dataFormatada;

    public MedicoDTO(String nome, String senha, String endereco, String email, String crm, String cpf, String telefone, Date datanascimento) {
        this.nome = nome;
        this.senha = senha;
        this.endereco = endereco;
        this.email = email;
        this.crm = crm;
        this.cpf = cpf;
        this.telefone = telefone;
        this.dataNascimento = datanascimento;
    }

    public String getDataFormatada() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(dataNascimento);
    }
}

