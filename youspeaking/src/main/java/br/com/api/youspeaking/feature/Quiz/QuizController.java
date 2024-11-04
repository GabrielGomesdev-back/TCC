package br.com.api.youspeaking.feature.Quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("api/v1/FT005/quiz")
public class QuizController {

    @Autowired QuizService service;

    @GetMapping("/generate-question")
    public ObjectNode generateQuestion(@RequestParam String login, @RequestParam String language) throws Exception {
        return service.generateQuestion(login, language);
    }
    
}
