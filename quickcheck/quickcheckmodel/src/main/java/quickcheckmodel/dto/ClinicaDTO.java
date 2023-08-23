package quickcheckmodel.dto;

import java.util.List;

public class ClinicaDTO {
    private String nome, endereco, telefone, cpfmedico;
    private List<String> convenios;

    public ClinicaDTO() {
        
    }

    public ClinicaDTO(String nome, String endereco, String telefone, List<String> convenios, String cpfmedico) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.convenios = convenios;
        this.cpfmedico = cpfmedico;
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
    public List<String> getConvenios() {
        return convenios;
    }
    public void setConvenios(List<String> convenios) {
        this.convenios = convenios;
    }

    public String getCpfmedico() {
        return cpfmedico;
    }

    public void setCpfmedico(String cpfmedico) {
        this.cpfmedico = cpfmedico;
    }

    
}
