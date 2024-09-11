package br.com.api.youspeaking.feature.ChatBot.EnglishLevel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.detectlanguage.errors.APIError;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.api.youspeaking.feature.Translation.TranslationService;

@Service
public class EnglishLevelService {

    @Autowired private TranslationService translationService;

    public ObjectNode getQuestionsFirstQuiz() throws APIError{
        return translationService.translateText("Hello", "FR");
    }
}
