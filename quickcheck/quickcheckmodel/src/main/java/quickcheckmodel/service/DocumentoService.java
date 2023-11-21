package quickcheckmodel.service;

import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import com.amazonaws.services.s3.model.ObjectMetadata;
import quickcheckmodel.dao.DocumentoDAO;
import quickcheckmodel.dto.DocumentoDTO;
import quickcheckmodel.dto.PacienteDTO;

public class DocumentoService {
    public void inserirDocumento(DocumentoDTO documento, InputStream inputStream, ObjectMetadata metadata, PacienteDTO paciente) {
        DocumentoDAO.inserirDocumento(documento, inputStream, metadata, paciente);
    }


    public void removerDocumento(DocumentoDTO documento) {
        DocumentoDAO.removerDocumento(documento);
    }
    
    public List<DocumentoDTO> listar(String cpf) {
        return DocumentoDAO.listar(cpf);
    }

    public File baixarArquivo(String chave) {
        return DocumentoDAO.baixarArquivo(chave);
    }
}
