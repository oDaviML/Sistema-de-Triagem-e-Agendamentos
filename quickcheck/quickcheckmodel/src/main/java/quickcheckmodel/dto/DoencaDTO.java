package quickcheckmodel.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class DoencaDTO {

    private String nome, gravidade, regiao, descricao, tratamento, cpfMedico, nomeMedico;
    private String[] sintomasCabecaPescoso, sintomasRespiratorio, sintomasGastrointestinal, sintomasPelve, sintomasMuscular, sintomasVisual;
    
    
    public DoencaDTO() {
    }

    public DoencaDTO(String nome, String gravidade, String regiao, String descricao, String tratamento, String cpfMedico, String nomeMedico, String[] sintomasCabecaPescoso, String[] sintomasRespiratorio, String[] sintomasGastrointestinal, String[] sintomasPelve, String[] sintomasMuscular, String[] sintomasVisual) {
        this.nome = nome;
        this.gravidade = gravidade;
        this.regiao = regiao;
        this.descricao = descricao;
        this.tratamento = tratamento;
        this.cpfMedico = cpfMedico;
        this.nomeMedico = nomeMedico;
        this.sintomasCabecaPescoso = sintomasCabecaPescoso;
        this.sintomasRespiratorio = sintomasRespiratorio;
        this.sintomasGastrointestinal = sintomasGastrointestinal;
        this.sintomasPelve = sintomasPelve;
        this.sintomasMuscular = sintomasMuscular;
        this.sintomasVisual = sintomasVisual;
    }



}