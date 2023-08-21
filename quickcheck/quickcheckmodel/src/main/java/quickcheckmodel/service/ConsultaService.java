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
    public void cadastrarConsulta(ConsultaDTO consultaDTO) throws ClassNotFoundException, SQLException{
        ConsultaDAO.inserirConsulta(consultaDTO);
    }
   
    public void atualizarConsulta(ConsultaDTO consultaDTO) throws SQLException {
        ConsultaDAO.atualizarConsulta(consultaDTO);
    }

    public void removerConsulta(ConsultaDTO consultaDTO) throws SQLException {
        ConsultaDAO.removerConsulta(consultaDTO);
    }

     public List<ConsultaDTO> listar() {
        return ConsultaDAO.buscar();
    }
}

