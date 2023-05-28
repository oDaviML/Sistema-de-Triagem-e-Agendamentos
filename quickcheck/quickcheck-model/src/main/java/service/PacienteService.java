package service;

import java.sql.Date;

import dao.PacienteDAO;
import dto.PacienteDTO;

public class PacienteService {
    public static PacienteDTO cadastrarPaciente(String nome, String endereco, String email, String convenio, String cpf, String telefone,
    Date datanascimento) {
        return PacienteDAO.inserirPaciente(nome, endereco, email, convenio, cpf, telefone, datanascimento);
    }
}
