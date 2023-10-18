package quickcheckmodel.dto;

import java.util.Date;

public class DoencaDTO {

    private int id;
    private String nome, gravidade, regiao, descricao, tratamento;
   
    public DoencaDTO() {
    }

    public DoencaDTO(int id, String nome, String gravidade, String regiao, String descricao, String tratamento) {
        this.id = id;
        this.nome = nome;
        this.gravidade = gravidade;
        this.regiao = regiao;
        this.descricao = descricao;
        this.tratamento = tratamento;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getGravidade() {
        return gravidade;
    }

    public String getRegiao() {
        return regiao;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTratamento() {
        return tratamento;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setGravidade(String gravidade) {
        this.gravidade = gravidade;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setTratamento(String tratamento) {
        this.tratamento = tratamento;
    }

}