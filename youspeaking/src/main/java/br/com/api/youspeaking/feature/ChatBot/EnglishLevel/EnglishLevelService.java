package br.com.api.youspeaking.feature.ChatBot.EnglishLevel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.detectlanguage.errors.APIError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.api.youspeaking.feature.Translation.TranslationService;

@Service
public class EnglishLevelService {

    @Autowired private TranslationService translationService;

    public ObjectNode getQuestionsFirstQuiz(ObjectNode json) throws APIError{
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        response.put("text", callTranslation(json.get("text").asText()));
        response.put("time", obterDataAtual());
        response.put("from", "SERVER");
        return response;
    }

    private String callTranslation(String message) throws APIError{
        return translationService.translateText(message, "FR").get("responseData").get("translatedText").asText();
    }

    private String obterDataAtual(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
