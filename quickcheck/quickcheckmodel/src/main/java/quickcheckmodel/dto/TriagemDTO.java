package quickcheckmodel.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;

@Setter@Getter
@NoArgsConstructor@AllArgsConstructor
public class TriagemDTO {
    private String[] cabeca, respiratorio, gastrointestinal, pelve, visual, muscular;
    private ArrayList<String> sintomas;
    private String[] condicoesExistentes;
    private Integer inicioSintomas;
    private String historicoViagem, contatoInfectados, atividadeFisica, mudancaDieta, alergias, cirugiaAnterior, metodoContraceptivoHormonio;
    private Boolean historicoDoencasCardiacas, historicoCancer, historicoDoencasRenais, tabagismoalcool, transtornosPsiquiatricos,
        exerciciosRegulares;

    @Override
    public String toString() {
        return "\nsintomas=" + getSintomas().toString() +
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
                "\nexerciciosRegulares=" + exerciciosRegulares;
    }

    public ArrayList<String> getSintomas() {
        sintomas=new ArrayList<>();
        sintomas.addAll(Arrays.asList(cabeca));
        sintomas.addAll(Arrays.asList(respiratorio));
        sintomas.addAll(Arrays.asList(gastrointestinal));
        sintomas.addAll(Arrays.asList(pelve));
        sintomas.addAll(Arrays.asList(visual));
        sintomas.addAll(Arrays.asList(muscular));
        return sintomas;
    }
}
