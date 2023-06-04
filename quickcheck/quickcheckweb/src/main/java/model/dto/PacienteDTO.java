package model.dto;

import java.util.Date;

public class PacienteDTO {
    private String nome, endereco, email, convenio, cpf, telefone;
    private Date datanascimento;

    public PacienteDTO() {
    }
   
    public PacienteDTO(String nome, String endereco, String email, String convenio, String cpf, String telefone, Date datanascimento) {
        this.nome = nome;
        this.endereco = endereco;
        this.email = email;
        this.convenio = convenio;
        this.cpf = cpf;
        this.telefone = telefone;
        this.datanascimento = datanascimento;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getDatanascimento() {
        return datanascimento;
    }

    public void setDatanascimento(Date datanascimento) {
        this.datanascimento = datanascimento;
    }

}
