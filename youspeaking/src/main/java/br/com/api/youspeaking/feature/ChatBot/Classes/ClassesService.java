package br.com.api.youspeaking.feature.ChatBot.Classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.api.youspeaking.data.entity.LogMessage;
import br.com.api.youspeaking.data.entity.User;
import br.com.api.youspeaking.data.repository.LogMessageRepository;
import br.com.api.youspeaking.data.repository.UserRepository;
import br.com.api.youspeaking.feature.Thirdparties.OpenAI.OpenApiService;
import br.com.api.youspeaking.utils.Utils;
import br.com.api.youspeaking.vo.ChatRequestVO;
import br.com.api.youspeaking.vo.MessageVO;

@Service
public class ClassesService {

    @Autowired LogMessageRepository logMessageRepository;
    @Autowired OpenApiService openAIservice;
    @Autowired UserRepository repository;
    
    protected ObjectNode greetings (String login, String language) throws JsonProcessingException{
        ObjectNode response     = Utils.genericJsonSuccess();
        ObjectMapper mapper     = new ObjectMapper();
        ObjectNode jsonLog      = mapper.createObjectNode();
        ChatRequestVO chatRequest      = new ChatRequestVO();
        List<MessageVO> listaMensagens = new ArrayList();
        User user = repository.findByLogin(login);
        
        String prompt = "Retorne somente uma mensagem de apresentação para ser colocada em uma div HTML sem a tag div use <br> para quebra de linhas como se fosse um professor do idioma: ${idioma} com o nome de Speaking-bot | iniciando uma aula particular de um aluno chamado: ${nomeUsuario} de idioma matriz: ${idiomaMatriz} | do nível: ${nivel} | ensine algo novo sobre o idioma";
        prompt = prompt.replace("${idioma}", user.getLanguage());
        prompt = prompt.replace("${nomeUsuario}", user.getName());
        prompt = prompt.replace("${idiomaMatriz}", language);
        prompt = prompt.replace("${nivel}", user.getLanguageLevel());
        
        MessageVO vo = new MessageVO(prompt, "user");
        listaMensagens.add(vo);
        chatRequest.setMessages(listaMensagens);
        chatRequest.setModel("gpt-4o-mini");
        ObjectNode responseChat   = openAIservice.callOpenAI(chatRequest);
        String responseChatString = responseChat.get("choices").get(0).get("message").get("content").asText();

        jsonLog.put("login", login);
        jsonLog.put("message", prompt);
        
        this.recordLogMessage(jsonLog, responseChat);

        response.put("data", responseChatString.replaceAll("```|´´´", "").replace("html", "") + "<br> Envie 'Ok' quando estiver pronto para continuar a aula.");
        return response;
    }

    protected void recordLogMessage(ObjectNode jsonUsuario, ObjectNode jsonChat){
        LogMessage log = new LogMessage(
            jsonUsuario.get("login").asText(), 
            jsonUsuario.get("message").asText(),
            jsonChat.get("choices").get(0).get("message").get("content").asText(),
            new Date()
        );
        logMessageRepository.saveAndFlush(log);
    }
    
}
