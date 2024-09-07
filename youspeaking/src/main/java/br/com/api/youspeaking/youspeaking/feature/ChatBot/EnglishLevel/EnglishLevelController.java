package br.com.api.youspeaking.youspeaking.feature.ChatBot.EnglishLevel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;


@RestController
@RequestMapping("api/v1/FT002/quiz")
public class EnglishLevelController {
        
    @Autowired private EnglishLevelService service;

    @GetMapping(value = "/first-quiz")
    public ObjectNode getQuestionsFirstQuiz(@RequestParam String language){
        return service.getQuestionsFirstQuiz(language);
    }

    @PostMapping(value = "/insert-question")
    public ObjectNode insertQuestion(@RequestBody ObjectNode body){
        return service.insertQuestion(body);
    }
}
