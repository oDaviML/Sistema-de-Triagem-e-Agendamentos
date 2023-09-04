package quickcheckmodel.dto;

import java.util.Date;

public class ConsultaDTO {

    private int id;
    private String cpfpaciente, cpfmedico, especialdiade, convenio, horario;
    private Date data;
   
    public ConsultaDTO() {
    }

    public ConsultaDTO(int id,String cpfpaciente, String cpfmedico, String especialdiade, String convenio, Date data, String horario) {
        this.cpfpaciente = cpfpaciente;
        this.cpfmedico = cpfmedico;
        this.especialdiade = especialdiade;
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

    public String getEspecialdiade() {
        return especialdiade;
    }

    public void setEspecialdiade(String especialdiade) {
        this.especialdiade = especialdiade;
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

    
}