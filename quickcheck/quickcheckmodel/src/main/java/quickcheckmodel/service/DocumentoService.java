package quickcheckmodel.service;

import java.util.List;

import quickcheckmodel.dao.DocumentoDAO;
import quickcheckmodel.dto.DocumentoDTO;

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
}
