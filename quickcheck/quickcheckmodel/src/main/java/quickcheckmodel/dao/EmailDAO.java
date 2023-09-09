package quickcheckmodel.dao;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import quickcheckmodel.dto.EmailDTO;

import java.io.IOException;

public class EmailDAO {
    public static void enviarEmail(EmailDTO emailDTO) {
        new Thread(() -> {
            HttpClient httpClient = HttpClients.createDefault();

            HttpPost httpPost = new HttpPost("http://localhost:3000/enviaremail");
            httpPost.setHeader("Content-Type", "application/json");

            String requestBody = String.format("{\"email\": \"%s\", \"subject\": \"%s\", \"html\": \"%s\"}", emailDTO.getEmail(), emailDTO.getAssunto(), emailDTO.getMensagem());
            httpPost.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));

            try {
                HttpResponse response = httpClient.execute(httpPost);
                int statusCode = response.getStatusLine().getStatusCode();
                String responseBody = EntityUtils.toString(response.getEntity());

                if (statusCode == 200) {
                    System.out.println("Email enviado com sucesso");
                } else {
                    System.out.println("Erro ao enviar email: " + responseBody);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
