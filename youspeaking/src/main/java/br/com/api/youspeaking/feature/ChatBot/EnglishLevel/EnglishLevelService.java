package br.com.api.youspeaking.feature.ChatBot.EnglishLevel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
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
public class EnglishLevelService {

    @Autowired LogMessageRepository logMessageRepository;
    @Autowired OpenApiService openApiService;
    @Autowired UserRepository repository;

    public ObjectNode getQuestionsFirstQuiz(ObjectNode json) throws JsonProcessingException{
        ObjectNode response     = Utils.genericJsonSuccess();
        response.put("data", formJsonRequest(json));
        recordLogMessage(json, response);
        return response;
    }

    protected String formJsonRequest(ObjectNode json) throws JsonProcessingException{
        ChatRequestVO chatRequest = new ChatRequestVO();
        List<MessageVO> listaMensagens = new ArrayList();
        User user = repository.findByLogin(json.get("login").asText());
        ArrayList<LogMessage> messages = logMessageRepository.findAllByLoginOrderByDateMessageAsc(json.get("login").asText());
        for(LogMessage message : messages){
            MessageVO voUser = new MessageVO(message.getMessage(), "user");
            listaMensagens.add(voUser);
            MessageVO voAssistaint = new MessageVO(message.getMessageResponse(), "assistant");
            listaMensagens.add(voAssistaint);
        }
        String prompt = "Responda como professor de ${idioma} dando aulas para um aluno de nível ${nivel} e gere um exercício que possa ser resolvido por conversação ou por texto de exemplo e corrija-o formate-os em HTML para serem adicionados a uma div quebre as linhas usando <br> escreva o enunciado das perguntas usando <h5> na próxima interação com o usuário, após a correção continue a conversa gerando novas perguntas:";
        prompt = prompt.replace("${idioma}", user.getLanguage());
        prompt = prompt.replace("${nivel}", user.getLanguageLevel());
        MessageVO vo = new MessageVO(prompt + json.get("message").toString(), "user");
        listaMensagens.add(vo);
        chatRequest.setModel("gpt-4o-mini");
        chatRequest.setMessages(listaMensagens);
        ObjectNode response = openApiService.callOpenAI(chatRequest);
        return response.get("choices").get(0).get("message").get("content").asText().replaceAll("```|´´´", "").replace("html", "");
    }

    public void recordLogMessage(ObjectNode jsonUsuario, ObjectNode jsonChat){
        LogMessage log = new LogMessage(
            jsonUsuario.get("login").toString(), 
            jsonUsuario.get("message").toString(),
            jsonChat.get("data").asText(),
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
