/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quickcheckmodel.dto;

import java.util.Date;

/**
 *
 * @author Aluno
 */
public class ConsultaDTO {
   
    private String assunto, endereco, email, telefone;
    private Date data;
   
    public ConsultaDTO() {
    }

    public ConsultaDTO(String assunto, String endereco, String email, String telefone, Date data) {
        this.assunto = assunto;
        this.endereco = endereco;
        this.email = email;
        this.telefone = telefone;
        this.data = data;
    }
   
    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

}
