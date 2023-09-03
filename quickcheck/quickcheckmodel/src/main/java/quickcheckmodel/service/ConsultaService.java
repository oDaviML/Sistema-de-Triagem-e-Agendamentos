/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quickcheckmodel.service;

import java.sql.SQLException;
import java.util.List;

import quickcheckmodel.dao.ConsultaDAO;
import quickcheckmodel.dto.ConsultaDTO;

public class ConsultaService {
    public void inserirConsultaPaciente(ConsultaDTO consultaDTO) throws ClassNotFoundException, SQLException{
        ConsultaDAO.inserirConsultaPaciente(consultaDTO);
    }

    public void removerConsultaPaciente(ConsultaDTO consultaDTO) throws SQLException {
        ConsultaDAO.removerConsultaPaciente(consultaDTO);
    }

     public List<ConsultaDTO> listarConsultaPaciente(String cpf) {
        return ConsultaDAO.listarConsultaPaciente(cpf);
    }
     
    public List<ConsultaDTO> listarConsultaCadastradas(ConsultaDTO consultaDTO) {
        return ConsultaDAO.listarConsultaCadastradas(consultaDTO);
    } 
}

