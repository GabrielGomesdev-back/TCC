package br.com.api.youspeaking.feature.ChatBot.EnglishLevel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.detectlanguage.errors.APIError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.api.youspeaking.feature.Translation.TranslationService;
import br.com.api.youspeaking.thirdparties.JokesClient;
import br.com.api.youspeaking.thirdparties.TranslatorClient;
import feign.Feign;
import feign.form.spring.SpringFormEncoder;
import feign.jackson.JacksonDecoder;

@Service
public class EnglishLevelService {

    @Value("${you-speaking.url.jokes}")
    private String urlStringJokes;
    @Autowired private TranslationService translationService;

    public ObjectNode getQuestionsFirstQuiz(ObjectNode json) throws APIError{
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        response.put("text", callJokesApi(json.get("language").asText()));
        response.put("time", obterDataAtual());
        response.put("from", "SERVER");
        return response;
    }

    private String callJokesApi(String language){
        JokesClient jokesClient = Feign.builder().encoder(new SpringFormEncoder()).decoder(new JacksonDecoder()).target(JokesClient.class, urlStringJokes);
        ObjectNode node = jokesClient.generateJoke(language);
        return node.get("joke").asText();
    }

    private String obterDataAtual(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
