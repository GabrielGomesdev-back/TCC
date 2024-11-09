package br.com.api.youspeaking.feature.ChatBot.Classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("api/v1/FT004/classes")
public class ClassesController {
    
    @Autowired ClassesService service;

    @GetMapping("/greetings")
    public ObjectNode greetings(@RequestParam String login, @RequestParam String language) throws Exception {
        return service.greetings(login, language);
    }
}
