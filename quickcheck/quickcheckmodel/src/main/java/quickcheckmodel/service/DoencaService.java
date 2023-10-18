/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quickcheckmodel.service;

import java.sql.SQLException;
import java.util.List;

import quickcheckmodel.dao.DoencaDAO;
import quickcheckmodel.dto.DoencaDTO;

/**
 *
 * @author Usuário
 */
public class DoencaService {
    
    DoencaDAO doencaDAO = new DoencaDAO();
    
    public void inserirDoenca(DoencaDTO doencaDTO) throws ClassNotFoundException, SQLException{
        DoencaDAO.inserirDoenca(doencaDTO);
    }

    public void removerDoenca(DoencaDTO doencaDTO) throws SQLException {
        DoencaDAO.removerDoenca(doencaDTO);
    }
    
    public List<DoencaDTO> listarDoencas() throws SQLException, ClassNotFoundException {
        return DoencaDAO.listarDoencas();
    }
    
}
