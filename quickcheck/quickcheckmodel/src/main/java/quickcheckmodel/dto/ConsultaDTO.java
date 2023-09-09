package quickcheckmodel.dto;

import java.util.Date;

public class ConsultaDTO {

    private int id;
    private String cpfpaciente, cpfmedico, especialidade, convenio, horario, nomemedico;
    private Date data;
   
    public ConsultaDTO() {
    }

    public ConsultaDTO(String cpfpaciente, String cpfmedico, String especialidade, String convenio, Date data, String horario, String nomemedico) {
        this.cpfpaciente = cpfpaciente;
        this.cpfmedico = cpfmedico;
        this.especialidade = especialidade;
        this.convenio = convenio;
        this.data = data;
        this.horario = horario;
        this.nomemedico = nomemedico;
    }

    public ConsultaDTO(int id,String cpfpaciente, String cpfmedico, String especialidade, String convenio, Date data, String horario) {
        this.cpfpaciente = cpfpaciente;
        this.cpfmedico = cpfmedico;
        this.especialidade = especialidade;
        this.convenio = convenio;
        this.data = data;
        this.horario = horario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpfpaciente() {
        return cpfpaciente;
    }

    public void setCpfpaciente(String cpfpaciente) {
        this.cpfpaciente = cpfpaciente;
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

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getNomemedico() {
        return nomemedico;
    }

    public void setNomemedico(String nomemedico) {
        this.nomemedico = nomemedico;
    }
    
}