package quickcheckmodel.dao;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import quickcheckmodel.db.DBConnector;
import quickcheckmodel.dto.PacienteDTO;
import quickcheckmodel.dto.TriagemDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

public class TriagemDAO {

    private static final String api = "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";
    public String resultadoTriagem(TriagemDTO triagem, PacienteDTO paciente) {

        Integer idade = LocalDate.now().getYear() - paciente.getDatanascimento().getYear();
        System.out.println(paciente.getDatanascimento().getYear());
        System.out.println(LocalDate.now().getYear());
        System.out.println(idade);

        String prompt = "Atue como um sistema de triagem e com base no banco de dados de doenças a seguir:" +
                "Gripe (Influenza), " +
                "Resfriado comum, " +
                "Dor de cabeça tensional, " +
                "Infecção do trato urinário (ITU), " +
                "Gastrite, " +
                "Refluxo gastroesofágico (DRGE), " +
                "Sinusite, " +
                "Conjuntivite, " +
                "Bronquite aguda, " +
                "Anemia ferropriva, " +
                "Virose, " +
                "Asma, " +
                "Dermatite de contato, " +
                "Apendicite, " +
                "Constipação, " +
                "Enxaqueca, " +
                "Infecção de ouvido (otite), " +
                "Doença de Crohn, " +
                "Endometriose, " +
                "Doença do refluxo gastroesofágico (DRGE), " +
                "Hipertensão arterial, " +
                "Diabetes tipo 2, " +
                "Infecção por fungos nas unhas (onicomicose), " +
                "Rosácea, " +
                "Hipertireoidismo, " +
                "Hipotireoidismo, " +
                "Artrite reumatoide, " +
                "Síndrome do intestino irritável (SII), " +
                "Infecção por herpes simplex, " +
                "Infecção por salmonela, " +
                "Infecção por E. coli, " +
                "Candidíase, " +
                "Conjuntivite viral, " +
                "Varizes, " +
                "Urticária, " +
                "Pedras nos rins (cálculos renais), " +
                "Eczema, " +
                "Hemorroidas, " +
                "Pneumonia, " +
                "Cálculo biliar, " +
                "Úlcera péptica, " +
                "Candidíase oral (sapinho), " +
                "Herpes labial, " +
                "Infecção do trato respiratório superior ," +
                "Impetigo, " +
                "Herpes z Gota, " +
                "Doença de Parkinson, " +
                "Câncer de pele (melanoma) " +
                "A partir disso você deve estimar qual possa ser a enfermidade, com base na entrada do paciente. Você deve escrever apenas o nome da doença, em uma única linha e nada além disso. " +
                "Estou ciente que um diagnóstico preciso só pode ser feito por um profissional de saúde após uma avaliação clínica adequada. Lembrando retorne apenas o nome da doença com base nesse banco de dados que lhe foi informado" +
                "Idade:"+ idade + "\n" +
                triagem.toString();

/*        OpenAiService openAiService = new OpenAiService(api);
        CompletionRequest request = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt(prompt)
                .maxTokens(100)
                .temperature(0.5)
                .build();
        String resposta = openAiService.createCompletion(request).getChoices().get(0).getText();
        salvarTriagem(resposta, paciente);
        return resposta;*/
        return null;
    }

    public void salvarTriagem(String resultado, PacienteDTO paciente) {
        try (Connection connection = DBConnector.getConexao()) {
            String sql = "INSERT INTO triagem (cpfpaciente, resultado) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, paciente.getCpf());
                preparedStatement.setString(2, resultado);
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
