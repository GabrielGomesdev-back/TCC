package br.com.api.youspeaking.feature.ChatBot.EnglishLevel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


@Controller
public class EnglishLevelController {
        
    @Autowired private EnglishLevelService service;

    @MessageMapping("/send")
    @SendTo("/topic/message")
    public ObjectNode getQuestionsFirstQuiz(String jsonMessage) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = (ObjectNode) mapper.readTree(jsonMessage);
        return service.getQuestionsFirstQuiz(message);
    }
}
