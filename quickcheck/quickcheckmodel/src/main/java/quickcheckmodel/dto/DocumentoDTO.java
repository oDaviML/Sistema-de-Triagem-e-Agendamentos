package quickcheckmodel.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor
public class DocumentoDTO {
    private Integer id;
    private String tipo, nome, link, cpf, data;

    public DocumentoDTO(String tipo, String nome, String link, String cpf, String data, Integer id) {
        this.tipo = tipo;
        this.nome = nome;
        this.link = link;
        this.cpf = cpf;
        this.data = data;
        this.id = id;
    }
}
