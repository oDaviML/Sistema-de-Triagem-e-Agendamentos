package quickcheckmodel.dao;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import quickcheckmodel.db.DBConnector;
import quickcheckmodel.dto.DocumentoDTO;
import quickcheckmodel.dto.PacienteDTO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DocumentoDAO {

    private static AmazonS3 s3;
    private static DocumentoDAO instance;
    private static String bucketName = "quickcheck-documentospacientes";

    public DocumentoDAO () {
        s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_2)
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials("", "retirei para não excluirem")
                        )
                )
                .build();
    }

    public static DocumentoDAO getInstance() {
        if (instance == null) {
            instance = new DocumentoDAO();
        }
        return instance;
    }
    
    public static void inserirDocumento(DocumentoDTO documentoDTO, InputStream inputStream, ObjectMetadata metadata, PacienteDTO paciente) {
        String nome = documentoDTO.getNomeArquivo();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        String timestamp = dateFormat.format(new Date());

        int lastDotIndex = nome.lastIndexOf(".");
        String fileExtension = nome.substring(lastDotIndex);
        String fileNameWithoutExtension = nome.substring(0, lastDotIndex);
        String newFileName = paciente.getNome() + "-" + fileNameWithoutExtension + "-" + timestamp + fileExtension;

        adicionarAquivo(newFileName, inputStream, metadata);

        if (documentoDTO.getTamanho() > 1024 * 1024) {
            double tamanho = documentoDTO.getTamanho() / (1024 * 1024);
            String tamanhoFormated = String.format("%.2f", tamanho) + " MB";
            documentoDTO.setTamanhoFormatado(tamanhoFormated);
        } else {
            double tamanho = documentoDTO.getTamanho() / 1024;
            String tamanhoFormated = String.format("%.2f", tamanho) + " KB";
            documentoDTO.setTamanhoFormatado(tamanhoFormated);
        }

        try (Connection connection = DBConnector.getConexao()) {

            String sql = "INSERT INTO documentospacientes (cpfpaciente, tipo, nome, nomereal, data, tamanho) VALUES (?, ?, ?, ?, ?, ?)";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, documentoDTO.getCpf());
            preparedStatement.setString(2, fileExtension.toUpperCase());
            preparedStatement.setString(3, documentoDTO.getNome());
            preparedStatement.setString(4, newFileName);
            preparedStatement.setTimestamp(5, new Timestamp(new Date().getTime()));
            preparedStatement.setString(6, documentoDTO.getTamanhoFormatado());

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void removerDocumento(DocumentoDTO documentoDTO) {
        deletarArquivo(documentoDTO.getNomeArquivo());
        try (Connection connection = DBConnector.getConexao()) {

            String sql = "DELETE FROM documentospacientes WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, documentoDTO.getId());

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void adicionarAquivo(String fileName, InputStream inputStream, ObjectMetadata metadata) {
        DocumentoDAO documentoDAO = DocumentoDAO.getInstance();
        s3.putObject(bucketName, fileName, inputStream, metadata);
    }

    public static void deletarArquivo(String chave) {
        DocumentoDAO documentoDAO = DocumentoDAO.getInstance();
        s3.deleteObject(bucketName, chave);
    }

    public static List<DocumentoDTO> listar(String cpf) {
        try (Connection connection = DBConnector.getConexao()) {
            String sql = "SELECT d.* FROM documentospacientes d JOIN paciente p ON p.cpf = d.cpfpaciente WHERE p.cpf = '"+cpf+"';";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<DocumentoDTO> documentos = new ArrayList<>();
            while (resultSet.next()) {
                DocumentoDTO documentoDTO = new DocumentoDTO();
                documentoDTO.setNome(resultSet.getString("nome"));
                documentoDTO.setTipo(resultSet.getString("tipo"));
                documentoDTO.setNomeArquivo(resultSet.getString("nomereal"));
                documentoDTO.setId(resultSet.getInt("id"));
                documentoDTO.setCpf(resultSet.getString("cpfpaciente"));
                Timestamp timestamp = resultSet.getTimestamp("data");
                java.util.Date date = new java.util.Date(timestamp.getTime());
                TimeZone timeZone = TimeZone.getTimeZone("America/Sao_Paulo");
                date.setTime(date.getTime() + timeZone.getRawOffset());
                documentoDTO.setData(date);
                documentoDTO.setTamanhoFormatado(resultSet.getString("tamanho"));

                documentos.add(documentoDTO);
            }
            return documentos;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static File baixarArquivo(String chave) {
        DocumentoDAO documentoDAO = DocumentoDAO.getInstance();
        S3Object s3Object = s3.getObject(bucketName, chave);

        InputStream inputStream = s3Object.getObjectContent();
        String fileName = s3Object.getKey();

        File file = new File(fileName);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
