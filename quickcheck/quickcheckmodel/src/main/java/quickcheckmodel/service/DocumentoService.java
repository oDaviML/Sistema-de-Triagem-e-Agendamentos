package quickcheckmodel.service;

import java.sql.SQLException;
import java.util.List;

import quickcheckmodel.dao.DocumentoDAO;
import quickcheckmodel.dto.DocumentoDTO;
import quickcheckmodel.dto.PacienteDTO;

public class DocumentoService {
    public void inserirDocumento(DocumentoDTO documento) {
        DocumentoDAO.inserirDocumento(documento);
    }

    public void atualizarDocumento(DocumentoDTO documento) {
        DocumentoDAO.atualizarDocumento(documento);
    }

    public void removerDocumento(DocumentoDTO documento) {
        DocumentoDAO.removerDocumento(documento);
    }
    
    public List<DocumentoDTO> listar(String cpf) {
        return DocumentoDAO.listar(cpf);
    }

    public List<PacienteDTO> listarPacientes(String cpf) throws ClassNotFoundException, SQLException {
        return DocumentoDAO.listarPacientes(cpf);
    }

}
