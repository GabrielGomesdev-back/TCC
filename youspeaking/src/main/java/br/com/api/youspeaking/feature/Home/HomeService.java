package br.com.api.youspeaking.feature.Home;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.api.youspeaking.data.entity.Feedback;
import br.com.api.youspeaking.data.entity.User;
import br.com.api.youspeaking.data.repository.FeedbackRepository;
import br.com.api.youspeaking.data.repository.UserRepository;
import br.com.api.youspeaking.feature.Quiz.QuizService;
import br.com.api.youspeaking.utils.Utils;

@Service
public class HomeService {

    @Autowired QuizService service;
    @Autowired FeedbackRepository repository;
    @Autowired UserRepository userRepository;

    public ObjectNode getAnalysisUser(String login) throws Exception {
        ObjectNode response = Utils.genericJsonSuccess();
        response.put("data", this.getAllAnalysis(login));
        return response;
    }

    protected ArrayNode getAllAnalysis(String login){
        ArrayList<Feedback> listFeedback = repository.findAllByLoginOrderByFeedbackDateAsc(login);
        return formatFeedbacks(listFeedback);
    }

    protected ArrayNode formatFeedbacks(ArrayList<Feedback> feedbackList){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ObjectMapper mapper = new ObjectMapper();
        JsonNodeFactory factory = JsonNodeFactory.instance;
        ArrayNode arrayNode = factory.arrayNode();
        for(Feedback object : feedbackList){
            ObjectNode objectNode = factory.objectNode();
            objectNode = mapper.valueToTree(object);
            objectNode.put("feedbackDate", sdf.format(object.getFeedbackDate()));
            arrayNode.add(objectNode);
        }
        return arrayNode;
    }
    
    public ObjectNode getUserInfo(String login) throws Exception {
        ObjectNode response = Utils.genericJsonSuccess();
        response.put("data", this.findUserInfo(login));
        return response;
    }

    protected ObjectNode findUserInfo(String login){
        ObjectMapper objectMapper = new ObjectMapper();
        User user = userRepository.findByLogin(login);
        return objectMapper.valueToTree(user);
    }

    public ObjectNode logout(String login) throws Exception {
        ObjectNode response = Utils.genericJsonSuccess();
        try {
            this.updateLogout(login);
            service.generateFeedbackClass(login);
            response.put("data", "Operação realizada com sucesso !");
            return response;
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            ObjectNode responseError = Utils.genericJsonError();
            response.put("data", "Operação não realizada. ");
            response.put("error", sw.toString());
            return responseError;
        }
    }

    protected void updateLogout(String login){
        User user = userRepository.findByLogin(login);
        user.setFlgLogged("N");
        userRepository.save(user);
    }

}
