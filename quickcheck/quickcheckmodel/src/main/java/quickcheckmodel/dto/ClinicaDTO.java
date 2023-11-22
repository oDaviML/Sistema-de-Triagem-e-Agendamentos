package quickcheckmodel.dto;

import lombok.*;

@Getter@Setter
@NoArgsConstructor
public class ClinicaDTO {
    private String nome, endereco, telefone, cpfmedico, especialidade, coordenada, nomemedico;
    private String[] convenios;

    public ClinicaDTO(String nome, String endereco, String telefone, String[] convenios, String cpfmedico, String especialidade, String coordenada, String nomemedico) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.convenios = convenios;
        this.cpfmedico = cpfmedico;
        this.especialidade = especialidade;
        this.coordenada = coordenada;
        this.nomemedico = nomemedico;
    }
}
