package quickcheckmodel.dto;

import java.util.List;

public class ClinicaDTO {
    private String nome, endereco, telefone, cpfmedico, especialidade, coordenada;
    private String[] convenios;

    public ClinicaDTO() {
        
    }

    public ClinicaDTO(String nome, String endereco, String telefone, String[] convenios, String cpfmedico, String especialidade, String coordenada) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.convenios = convenios;
        this.cpfmedico = cpfmedico;
        this.especialidade = especialidade;
        this.coordenada = coordenada;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String[] getConvenios() {
        return convenios;
    }
    public void setConvenios(String[] convenios) {
        this.convenios = convenios;
    }

    public String getCpfmedico() {
        return cpfmedico;
    }

    public void setCpfmedico(String cpfmedico) {
        this.cpfmedico = cpfmedico;
    }

    public String getEspecialidade() {
        return especialidade;
    }
    
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getCoordenada() {
        return coordenada;    
    }

    public void setCoordenada(String coordenada) {
        this.coordenada = coordenada;
    }
}
