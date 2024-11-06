package br.com.api.youspeaking.feature.ChatBot.EnglishLevel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.detectlanguage.errors.APIError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.api.youspeaking.data.entity.LogMessage;
import br.com.api.youspeaking.data.repository.LogMessageRepository;
import br.com.api.youspeaking.feature.Thirdparties.OpenAI.OpenApiService;
import br.com.api.youspeaking.vo.ChatRequestVO;
import br.com.api.youspeaking.vo.MessageVO;

@Service
public class EnglishLevelService {

    @Autowired LogMessageRepository logMessageRepository;
    @Autowired OpenApiService openApiService;

    public ObjectNode getQuestionsFirstQuiz(ObjectNode json) throws APIError, JsonProcessingException{
        ObjectNode response = formJsonRequest(json);
        recordLogMessage(json, response);
        return response;
    }

    protected ObjectNode formJsonRequest(ObjectNode json) throws JsonProcessingException{
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
        chatRequest.setModel("gpt-4o-mini");
        chatRequest.setMessages(listaMensagens);
        ObjectNode response = openApiService.callOpenAI(chatRequest);
        return response;
    }

    protected void recordLogMessage(ObjectNode jsonUsuario, ObjectNode jsonChat){
        LogMessage log = new LogMessage(
            jsonUsuario.get("login").toString(), 
            jsonUsuario.get("message").toString(),
            jsonChat.get("message").get("choices").get(0).get("message").get("content").asText(),
            new Date()
        );
        logMessageRepository.saveAndFlush(log);
    }

    private String obterDataAtual(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
