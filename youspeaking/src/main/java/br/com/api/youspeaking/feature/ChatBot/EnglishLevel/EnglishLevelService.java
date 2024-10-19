package br.com.api.youspeaking.feature.ChatBot.EnglishLevel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.detectlanguage.errors.APIError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.api.youspeaking.data.entity.LogMessage;
import br.com.api.youspeaking.data.repository.LogMessageRepository;
import br.com.api.youspeaking.feature.ChatBot.EnglishLevel.vo.ChatRequestVO;
import br.com.api.youspeaking.feature.ChatBot.EnglishLevel.vo.MessageVO;
import br.com.api.youspeaking.feature.Translation.TranslationService;
import br.com.api.youspeaking.thirdparties.JokesClient;
import feign.Feign;
import feign.form.spring.SpringFormEncoder;
import feign.jackson.JacksonDecoder;

@Service
public class EnglishLevelService {

    @Value("${you-speaking.url.jokes}") private String urlStringJokes;
    @Autowired LogMessageRepository logMessageRepository;
    @Autowired TranslationService translationService;

    public ObjectNode getQuestionsFirstQuiz(ObjectNode json) throws APIError{
        ObjectNode response = getResponse(json);
        ChatRequestVO vo = formJsonRequest(json);
        recordLogMessage(json, response);
        return response;
    }

    protected ChatRequestVO formJsonRequest(ObjectNode json){
        ChatRequestVO chatRequest = new ChatRequestVO();
        List<MessageVO> listaMensagens = new ArrayList();
        ArrayList<LogMessage> messages = logMessageRepository.findAllByLoginOrderByDateMessageAsc(json.get("login").toString());
        for(LogMessage message : messages){
            MessageVO voUser = new MessageVO(message.getMessage(), "user");
            listaMensagens.add(voUser);
            MessageVO voAssistaint = new MessageVO(message.getMessageResponse(), "assistant");
            listaMensagens.add(voAssistaint);
        }
        MessageVO vo = new MessageVO(json.get("message").toString(), "user");
        listaMensagens.add(vo);
        chatRequest.setModel("gpt-3.5-turbo");
        chatRequest.setMessages(listaMensagens);
        /* Request HERE and Logic for response*/
        return chatRequest;
    }

    protected ObjectNode getResponse (ObjectNode json) throws APIError{
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        response.put("text", callJokesApi(json.get("language").asText()));
        response.put("time", obterDataAtual());
        response.put("from", "SERVER");
        return response;
    }

    protected void recordLogMessage(ObjectNode jsonUsuario, ObjectNode jsonChat){
        LogMessage log = new LogMessage(
            jsonUsuario.get("login").toString(), 
            jsonUsuario.get("message").toString(),
            jsonChat.get("message").toString(),
            new Date()
        );
        logMessageRepository.saveAndFlush(log);
    }

    private String callJokesApi(String language) throws APIError{
        JokesClient jokesClient = Feign.builder().encoder(new SpringFormEncoder()).decoder(new JacksonDecoder()).target(JokesClient.class, urlStringJokes);
        ObjectNode node = jokesClient.generateJoke("en");
        return "en".equals(language) ? node.get("joke").asText() : translationService.translateText(node.get("joke").asText(), language).get("responseData").get("translatedText").asText() ;
    }

    private String obterDataAtual(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
