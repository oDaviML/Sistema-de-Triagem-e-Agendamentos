package quickcheckmodel.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter@Setter
@NoArgsConstructor
public class ConsultaDTO {

    private int id;
    private String cpfpaciente, cpfmedico, especialidade, convenio, horario, nome;
    private Date data;

    public ConsultaDTO(int id, String cpfpaciente, String cpfmedico, String especialidade, String convenio, String horario, String nome, Date data) {
        this.id = id;
        this.cpfpaciente = cpfpaciente;
        this.cpfmedico = cpfmedico;
        this.especialidade = especialidade;
        this.convenio = convenio;
        this.horario = horario;
        this.nome = nome;
        this.data = data;
    }
    public ConsultaDTO(int id, String cpfpaciente, String cpfmedico, String especialidade, String convenio, Date data, String horario) {
        this.id = id;
        this.cpfpaciente = cpfpaciente;
        this.cpfmedico = cpfmedico;
        this.especialidade = especialidade;
        this.convenio = convenio;
        this.data = data;
        this.horario = horario;
    }
}