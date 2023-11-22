package quickcheckmodel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoDTO {
    private Integer id;
    private String tipo, nome, nomeArquivo, cpf, tamanhoFormatado;
    private long tamanho;
    private Date data;
}
