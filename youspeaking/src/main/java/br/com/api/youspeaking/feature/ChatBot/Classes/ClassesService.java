package br.com.api.youspeaking.feature.ChatBot.Classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.detectlanguage.errors.APIError;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.api.youspeaking.data.entity.User;
import br.com.api.youspeaking.data.repository.UserRepository;
import br.com.api.youspeaking.feature.Translation.TranslationService;
import br.com.api.youspeaking.thirdparties.JokesClient;
import br.com.api.youspeaking.utils.Utils;
import feign.Feign;
import feign.form.spring.SpringFormEncoder;
import feign.jackson.JacksonDecoder;

@Service
public class ClassesService {

    @Value("${you-speaking.url.jokes}")
    private String urlStringJokes;
    @Autowired TranslationService translationService;
    @Autowired UserRepository repository;
    
    protected ObjectNode greetings (String login) throws APIError{
        User user = repository.findByLogin(login);
        String greeting = "Hello I'm the 123, and I Will teach you a new language !!!\n In our first class we will work with reading, I will tell you some jokes and you will translate to your main language OK ?";
        String firstMessage = !"en".equals(user.getLanguage()) ? translationService.translateText(greeting, user.getLanguage()).get("responseData").get("translatedText").asText() : greeting;
        return Utils.jsonGreetings(firstMessage.replaceAll("123", "speaking-bot"), user.getLanguage());
    }

    protected String generateJokes (String login) throws APIError{
        User user = repository.findByLogin(login);
        return callJokesApi(user.getLanguage());
    }
        
    private String callJokesApi(String language){
        JokesClient jokesClient = Feign.builder().encoder(new SpringFormEncoder()).decoder(new JacksonDecoder()).target(JokesClient.class, urlStringJokes);
        ObjectNode node = jokesClient.generateJoke(language);
        return node.get("joke").asText();
    }
}
