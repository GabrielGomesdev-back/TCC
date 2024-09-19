package br.com.api.youspeaking.feature.ChatBot.EnglishLevel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.detectlanguage.errors.APIError;
import com.fasterxml.jackson.databind.node.ObjectNode;


@RestController
@RequestMapping("api/v1/FT002/quiz")
public class EnglishLevelController {
        
    @Autowired private EnglishLevelService service;

    @GetMapping(value = "/first-quiz")
    @SendTo("/question/quiz")
    public ObjectNode getQuestionsFirstQuiz() throws APIError{
        return service.getQuestionsFirstQuiz();
    }
}
