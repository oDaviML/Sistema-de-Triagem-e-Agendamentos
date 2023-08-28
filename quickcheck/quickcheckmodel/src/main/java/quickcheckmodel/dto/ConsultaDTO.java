/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quickcheckmodel.dto;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author Aluno
 */
public class ConsultaDTO {
   private Integer id, idClinica;
   private String cpfpaciente, especialidade, nomeMedico, sexoMedico, cidade, emailContato, telefoneContato;
   private Date dataInicial, dataFinal, data;
   private Time horario;
   
    public ConsultaDTO() {
    }

    public ConsultaDTO(Integer idClinica, String cpfpaciente, String especialidade, String nomeMedico, String sexoMedico, String cidade, String emailContato, String telefoneContato, Date dataInicial, Date dataFinal, Date data, Time horario) {
        this.id = id;
        this.idClinica = idClinica;
        this.cpfpaciente = cpfpaciente;
        this.especialidade = especialidade;
        this.nomeMedico = nomeMedico;
        this.sexoMedico = sexoMedico;
        this.cidade = cidade;
        this.emailContato = emailContato;
        this.telefoneContato = telefoneContato;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.data = data;
        this.horario = horario;
    }

    public Integer getId() {
        return id;
    }
    
    public Integer getIdClinica() {
        return idClinica;
    }

    public String getCpfpaciente() {
        return cpfpaciente;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public String getSexoMedico() {
        return sexoMedico;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEmailContato() {
        return emailContato;
    }

    public String getTelefoneContato() {
        return telefoneContato;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }
    
    public Date getData() {
        return data;
    }
    
    public Time getHorario() {
        return horario;
    }

    public Integer setId() {
        return id;
    }
    
    public void setIdClinica(Integer idClinica) {
        this.idClinica = idClinica;
    }

    public void setCpfpaciente(String cpfpaciente) {
        this.cpfpaciente = cpfpaciente;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public void setSexoMedico(String sexoMedico) {
        this.sexoMedico = sexoMedico;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setEmailContato(String emailContato) {
        this.emailContato = emailContato;
    }

    public void setTelefoneContato(String telefoneContato) {
        this.telefoneContato = telefoneContato;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }
 
    public void setData(Date data) {
        this.dataFinal = data;
    }
    
    public void setHorario(Time horario) {
        this.horario = horario;
    }
}