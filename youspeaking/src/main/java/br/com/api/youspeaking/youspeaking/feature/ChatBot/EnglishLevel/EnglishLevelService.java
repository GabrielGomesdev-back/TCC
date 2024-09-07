package br.com.api.youspeaking.youspeaking.feature.ChatBot.EnglishLevel;

import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.api.youspeaking.youspeaking.data.Entity.DomainEntity;
import br.com.api.youspeaking.youspeaking.data.Repository.DomainRepository;
import br.com.api.youspeaking.youspeaking.feature.Translation.TranslationService;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class EnglishLevelService {

    @Autowired private DomainRepository repository;
    @Autowired private TranslationService translationService;
    @Autowired private HttpServletRequest request;

    public ObjectNode getQuestionsFirstQuiz( String language){
        ArrayList<String> listaPerguntas = new ArrayList();
        

        return null;
    }

    public ObjectNode insertQuestion(ObjectNode body){
        DomainEntity domain = new DomainEntity();
        domain.setDomainCode(body.get("domainCode").asLong());
        domain.setDomainValue(body.get("domainValue").toString());
        domain.setDomainOrder(body.get("domainOrder").asLong());
        domain.setDomainCodeDad(body.get("domainCodeDad").asLong());
        domain.setRelatedValue(body.get("relatedValue").asLong());
        domain.setDescription(body.get("description").toString());
        domain.setRegisterUser(request.getAttribute("login").toString());
        domain.setRegisterDate(Calendar.getInstance());
        return null;
    }
}
