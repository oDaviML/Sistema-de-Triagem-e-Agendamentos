package quickcheckmodel.dao;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import quickcheckmodel.dto.PacienteDTO;
import quickcheckmodel.dto.TriagemDTO;

import java.util.Arrays;
import java.util.Date;

public class TriagemDAO {

    private static final String api = "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";
    public String resultadoTriagem(TriagemDTO triagem, PacienteDTO paciente) {

        Integer idade = new Date().getYear() - paciente.getDatanascimento().getYear();

        String prompt = "Atue como um sistema de triagem e com base no banco de dados de doenças a seguir:" +
                "A partir disso você deve estimar qual possa ser a enfermidade, com base na entrada do paciente. Você deve escrever apenas o nome da doença, em uma única linha e nada além disso. " +
                "Estou ciente que um diagnóstico preciso só pode ser feito por um profissional de saúde após uma avaliação clínica adequada. Lembrando retorne apenas o nome da doença com base nesse banco de dados que lhe foi informado" +
                "Idade:"+ idade +
                "Sintomas:" + Arrays.toString(triagem.getSintomas()) +
                "Tempo de Início dos Sintomas (dias):" + triagem.getInicioSintomas() +
                "Histórico de Viagem Recentes:" + triagem.getHistoricoViagem() +
                "Teve Contato com Pessoas Infectadas:" + triagem.getContatoInfectados() +
                "Realizou alguma atividade física intensa nas últimas semanas:" + triagem.getAtividadeFisica() +
                "Fez alguma mudança significativa em sua dieta recentemente:" + triagem.getMudancaDieta() +
                "Condições Pré-Existentes:" + Arrays.toString(triagem.getCondicoesExistentes()) +
                "Histórico de Doenças Cardiacas:" + triagem.getHistoricoDoencasCardiacas() +
                "Histórico de Cancer:" + triagem.getHistoricoCancer() +
                "Histórico de Doenças Renais:" + triagem.getHistoricoDoencasRenais() +
                "Possui histórico de Tabagismo/Alcool:" + triagem.getTabagismoalcool() +
                "Possui algum transtorno Psiquiátricos:" + triagem.getTranstornosPsiquiatricos() +
                "Pratica exercícios Regulares:" + triagem.getExerciciosRegulares() +
                "Alergias:" + triagem.getAlergias() +
                "Realizou alguma cirurgia Anterior:" + triagem.getCirugiaAnterior() +
                "Faz o uso de algum metodo Contraceptivo/Hormonio:" + triagem.getMetodoContraceptivoHormonio();

        OpenAiService openAiService = new OpenAiService(api);
        CompletionRequest request = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt(prompt)
                .maxTokens(100)
                .temperature(0.5)
                .build();
        String resposta = openAiService.createCompletion(request).getChoices().get(0).getText();
        return resposta;
    }
}
