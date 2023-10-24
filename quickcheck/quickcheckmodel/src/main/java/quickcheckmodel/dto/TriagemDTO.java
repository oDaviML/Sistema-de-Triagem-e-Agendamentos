package quickcheckmodel.dto;

import lombok.*;

import java.util.Arrays;

@Setter@Getter
@NoArgsConstructor@AllArgsConstructor
public class TriagemDTO {
    private String[] sintomas;
    private String[] condicoesExistentes;
    private Integer inicioSintomas;
    private String historicoViagem, contatoInfectados, atividadeFisica, mudancaDieta, alergias, cirugiaAnterior, metodoContraceptivoHormonio;
    private Boolean historicoDoencasCardiacas, historicoCancer, historicoDoencasRenais, tabagismoalcool, transtornosPsiquiatricos,
        exerciciosRegulares;

    @Override
    public String toString() {
        return "TriagemDTO{" +
                "\nsintomas=" + Arrays.toString(sintomas) +
                "\ncondicoesExistentes=" + Arrays.toString(condicoesExistentes) +
                "\ninicioSintomas=" + inicioSintomas +
                "\nhistoricoViagem='" + historicoViagem + '\'' +
                "\ncontatoInfectados='" + contatoInfectados + '\'' +
                "\natividadeFisica='" + atividadeFisica + '\'' +
                "\nmudancaDieta='" + mudancaDieta + '\'' +
                "\nalergias='" + alergias + '\'' +
                "\ncirugiaAnterior='" + cirugiaAnterior + '\'' +
                "\nmetodoContraceptivoHormonio='" + metodoContraceptivoHormonio + '\'' +
                "\nhistoricoDoencasCardiacas=" + historicoDoencasCardiacas +
                "\nhistoricoCancer=" + historicoCancer +
                "\nhistoricoDoencasRenais=" + historicoDoencasRenais +
                "\ntabagismoalcool=" + tabagismoalcool +
                "\ntranstornosPsiquiatricos=" + transtornosPsiquiatricos +
                "\nexerciciosRegulares=" + exerciciosRegulares +
                "\n}";
    }
}
