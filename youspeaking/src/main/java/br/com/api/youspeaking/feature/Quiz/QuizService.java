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

import br.com.api.youspeaking.data.entity.Feedback;
import br.com.api.youspeaking.data.entity.QuizResult;
import br.com.api.youspeaking.data.entity.User;
import br.com.api.youspeaking.data.repository.FeedbackRepository;
import br.com.api.youspeaking.data.repository.QuizResultRepository;
import br.com.api.youspeaking.data.repository.UserRepository;
import br.com.api.youspeaking.feature.Thirdparties.OpenAI.OpenApiService;
import br.com.api.youspeaking.utils.Utils;
import br.com.api.youspeaking.vo.ChatRequestVO;
import br.com.api.youspeaking.vo.MessageVO;

@Service
public class QuizService {

    @Autowired OpenApiService openAIservice;
    @Autowired QuizResultRepository quizResultRepository;
    @Autowired FeedbackRepository feedbackRepository;
    @Autowired UserRepository userRepository;

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

    public ObjectNode generateFeedback(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response     = Utils.genericJsonSuccess();

        ObjectNode objectJson = (ObjectNode) mapper.readTree(json);
        generateFeedback(objectJson);
        
        response.put("data", "Operação realizada com sucesso !");
        return response;
    }

    protected void generateFeedback(ObjectNode json) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        ChatRequestVO chatRequest = new ChatRequestVO();
        List<MessageVO> listaMensagens = new ArrayList();

        QuizResult quiz = quizResultRepository.findByLoginUser(json.get("user").asText());
        User user = userRepository.findByLogin(json.get("user").asText());

        String prompt  = "Responda somente um json: O Json contém 5 perguntas para medir o conhecimento de uma pessoa no idioma ${idiomaAprendizado} : ${jsonPerguntas}";
        String prompt2 = " um usuário acertou as perguntas correspondentes aos indices: ${perguntasCertas} | com base nos acertos gere um JSON em camelCase e retorne somente o json com os atributos de nivel indicando o nível de conhecimento do usuário e outro de feedback que vai explicar sobre o nível de idioma e dar algumas dicas de como melhorar a aprendizagem, retornar o campo feedback em html dentro de uma string ";
        
        prompt = prompt.replace("${idiomaAprendizado}", user.getLanguage());
        prompt = prompt.replace("${jsonPerguntas}", quiz.getQuestionsGenerated());

        prompt2 = prompt2.replace("${perguntasCertas}", json.get("questions").asText());

        MessageVO vo = new MessageVO(prompt + prompt2, "user");
        listaMensagens.add(vo);
        chatRequest.setMessages(listaMensagens);
        chatRequest.setModel("gpt-4o-mini");
        ObjectNode responseChat   = openAIservice.callOpenAI(chatRequest);

        String responseChatString = responseChat.get("choices").get(0).get("message").get("content").asText();
        JsonNode jsonNode = mapper.readTree(responseChatString.replaceAll("```|´´´", "").replace("json", ""));    

        saveFeedback(json.get("user").asText(), jsonNode);

        user.setLanguageLevel(jsonNode.get("nivel").asText());
        userRepository.save(user);
    }

    protected void saveFeedback(String login, JsonNode jsonNode){
        Feedback feedback = new Feedback();
        feedback.setFeedbackDate(new Date());
        feedback.setFeedbackUser(jsonNode.get("feedback").asText());
        feedback.setLanguageLevel(jsonNode.get("nivel").asText());
        feedback.setLogin(login);
        feedbackRepository.save(feedback);
    }

}
