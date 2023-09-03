/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quickcheckmodel.dao;

/*import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import quickcheckmodel.db.DBConnector;
import quickcheckmodel.dto.DocumentoDTO;

// Interface Command
interface Command {

    void execute();
}

// Receiver
class DocumentoReceiver {

    public static void inserirDocumento(DocumentoDTO documentoDTO) {
        try (Connection connection = DBConnector.getConexao()) {

            String sql = "INSERT INTO documentospacientes (cpfpaciente, tipo, nome, link) VALUES (?, ?, ?, ?)";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, documentoDTO.getCpf());
            preparedStatement.setString(2, documentoDTO.getTipo());
            preparedStatement.setString(3, documentoDTO.getNome());
            preparedStatement.setString(4, documentoDTO.getLink());

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void excluirDocumento(DocumentoDTO documentoDTO) {
        try (Connection connection = DBConnector.getConexao()) {

            String sql = "DELETE FROM documentospacientes WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, documentoDTO.getId());

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void atualizarDocumento(DocumentoDTO documentoDTO) {
        try (Connection connection = DBConnector.getConexao()) {

            String sql = "UPDATE documentospacientes SET tipo = ?, nome = ?, link = ? WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, documentoDTO.getTipo());
            preparedStatement.setString(2, documentoDTO.getNome());
            preparedStatement.setString(3, documentoDTO.getLink());
            preparedStatement.setInt(4, documentoDTO.getId());

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static List<DocumentoDTO> listarDocumento(String cpf) {
        try (Connection connection = DBConnector.getConexao()) {
            String sql = "SELECT d.* FROM documentospacientes d JOIN paciente p ON p.cpf = d.cpfpaciente WHERE p.cpf = '"+cpf+"';";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<DocumentoDTO> documentos = new ArrayList<>();
            while (resultSet.next()) {
                DocumentoDTO documentoDTO = new DocumentoDTO();
                documentoDTO.setLink(resultSet.getString("link"));
                documentoDTO.setNome(resultSet.getString("nome"));
                documentoDTO.setTipo(resultSet.getString("tipo"));

                Timestamp timestamp = resultSet.getTimestamp("data");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String formattedDate = dateFormat.format(timestamp);
                documentoDTO.setData(formattedDate);
                documentoDTO.setId(resultSet.getInt("id"));

                documentos.add(documentoDTO);
            }
            return documentos;
        
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

// ConcreteCommand - InserirCommand
class InserirCommand implements Command {

    private DocumentoDTO documento;
    private DocumentoReceiver receiver;

    public InserirCommand(DocumentoDTO documento, DocumentoReceiver receiver) {
        this.documento = documento;
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        DocumentoReceiver.inserirDocumento(documento);
    }
}

// ConcreteCommand - ExcluirCommand
class ExcluirCommand implements Command {

    private DocumentoDTO documento;
    private DocumentoReceiver receiver;

    public ExcluirCommand(DocumentoDTO documento, DocumentoReceiver receiver) {
        this.documento = documento;
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        DocumentoReceiver.excluirDocumento(documento);
    }
}

// ConcreteCommand - AtualizarCommand
class AtualizarCommand implements Command {

    private DocumentoDTO documento;
    private DocumentoReceiver receiver;

    public AtualizarCommand(DocumentoDTO documento, DocumentoReceiver receiver) {
        this.documento = documento;
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        DocumentoReceiver.atualizarDocumento(documento);
    }
}

// ConcreteCommand - ListarCommand
class ListarCommand implements Command {

    private DocumentoDTO documento;
    private DocumentoReceiver receiver;
    private final String cpf;

    public ListarCommand(String cpf, DocumentoReceiver receiver) {
        this.cpf = cpf;
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        DocumentoReceiver.listarDocumento(cpf);
    }
}

// Invoker
class DocumentoInvoker {

    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void executeCommand() {
        command.execute();
    }
}

// Client

public class CommandDocumentoDAO {
    
    // Criação do objeto Receiver
    DocumentoReceiver receiver = new DocumentoReceiver();

    // Criação dos objetos DTO
    DocumentoDTO documentoDTO = new DocumentoDTO();

    // Criação dos objetos ConcreteCommand
    
    Command inserirCommand = new InserirCommand(documentoDTO, receiver);
    Command excluirCommand = new ExcluirCommand(documentoDTO, receiver);
    Command atualizarCommand = new AtualizarCommand(documentoDTO, receiver);
    Command listarCommand = new ListarCommand(documentoDTO.getCpf(), receiver);

    DocumentoInvoker teste = new DocumentoInvoker();
    
    // Criação do invoker
    DocumentoInvoker invoker = new DocumentoInvoker();  
    
    
    // Configura o comando de salvar para o invoker
    invoker.setCommand(inserirCommand);
    // Executa o comando de salvar
    invoker.executeCommand();

    // Configura o comando de excluir para o invoker
    invoker.setCommand(excluirCommand);
    // Executa o comando de excluir
    invoker.executeCommand();
    
    // Configura o comando de excluir para o invoker
    invoker.setCommand(atualizarCommand);
    // Executa o comando de excluir
    invoker.executeCommand();
    
    // Configura o comando de excluir para o invoker
    invoker.setCommand(listarCommand);
    // Executa o comando de excluir
    invoker.executeCommand();
}*/
