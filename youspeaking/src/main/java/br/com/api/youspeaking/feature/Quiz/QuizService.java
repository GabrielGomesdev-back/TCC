package br.com.api.youspeaking.feature.Quiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.api.youspeaking.data.entity.QuizResult;
import br.com.api.youspeaking.data.repository.QuizResultRepository;
import br.com.api.youspeaking.feature.Thirdparties.OpenAI.OpenApiService;
import br.com.api.youspeaking.utils.Utils;
import br.com.api.youspeaking.vo.ChatRequestVO;
import br.com.api.youspeaking.vo.MessageVO;

@Service
public class QuizService {

    @Autowired OpenApiService openAIservice;
    @Autowired QuizResultRepository quizResultRepository;

    public ObjectNode generateQuestions(String login, String language) throws Exception {
        ObjectNode response     = Utils.genericJsonSuccess();
        response.put("data", getChatResponse(login, language) );
        return response;
    }

    public JsonNode getChatResponse(String login, String language) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ChatRequestVO chatRequest = new ChatRequestVO();
        List<MessageVO> listaMensagens = new ArrayList();

        String perguntas = "Retorne somente um json que contenha 5 perguntas de alternativas do básico ao avançado para medir o nível de ${idiomaAprendizado} de uma pessoa de língua matriz ${idiomaMae}";
        String prompt = " com retorno no formato JSON em camel case retornando somente o array de perguntas sem um objeto perguntas, indicando se a alternativa está certa ou errada no próprio objeto de alternativa sem indicar a letra da resposta, com o campo do enunciado da pergunta com o nome de texto, com os objetos de alternativas com o atributo resposta e correta como true ou false para indicar se a resposta é a certa";
        perguntas = perguntas.replace("${idiomaAprendizado}", "EN");
        perguntas = perguntas.replace("${idiomaMae}", language);

        MessageVO vo = new MessageVO(perguntas + prompt, "user");
        listaMensagens.add(vo);
        chatRequest.setMessages(listaMensagens);
        chatRequest.setModel("gpt-4o-mini");
        ObjectNode responseChat   = openAIservice.callOpenAI(chatRequest);

        String responseChatString = responseChat.get("choices").get(0).get("message").get("content").asText();
        try{
            JsonNode jsonNode = mapper.readTree(responseChatString.replaceAll("```|´´´", "").replace("json", ""));    
            this.saveQuestions(jsonNode, login);
            return jsonNode;
        } catch (Exception e) {
            return null;
        }

    }

    protected void saveQuestions (JsonNode json, String login) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        QuizResult quiz = new QuizResult();
        quiz.setLoginUser(login);
        quiz.setQuestionsGenerated(mapper.writeValueAsString(json));
        quiz.setDateResult(new Date());
        quizResultRepository.save(quiz);
    }

    public ObjectNode generateFeedbackQuiz(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response     = Utils.genericJsonSuccess();

        ObjectNode nodeResponse = (ObjectNode) mapper.readTree(json);
        
        response.put("data", mapper.writeValueAsString(nodeResponse));
        return response;
    }

}
