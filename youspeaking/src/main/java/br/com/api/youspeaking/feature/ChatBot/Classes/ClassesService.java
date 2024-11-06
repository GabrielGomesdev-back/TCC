package br.com.api.youspeaking.feature.ChatBot.Classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.api.youspeaking.data.entity.User;
import br.com.api.youspeaking.data.repository.UserRepository;
import br.com.api.youspeaking.feature.Translation.TranslationService;
import br.com.api.youspeaking.utils.Utils;

@Service
public class ClassesService {

    @Autowired TranslationService translationService;
    @Autowired UserRepository repository;
    
    protected ObjectNode greetings (String login) throws JsonProcessingException{
        User user = repository.findByLogin(login);
        String greeting = "Hello I'm the 123, and I Will teach you a new language !!!\n In our first class we will work with reading, I will tell you some jokes and you will translate to your main language OK ?";
        String firstMessage = !"EN".equals(user.getLanguage()) ? translationService.translateText(greeting, user.getLanguage()).get("responseData").get("translatedText").asText() : greeting;
        return Utils.jsonGreetings(firstMessage.replaceAll("123", "speaking-bot"), user.getLanguage());
    }
    
}
