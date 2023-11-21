package quickcheckmodel.dto;

import lombok.*;

import java.util.Date;


@Getter@Setter
@AllArgsConstructor@NoArgsConstructor
public class ResultadoTriagemDTO {
    private String resultado;
    private Date data;
}
