package br.com.api.youspeaking.utils;

import org.springframework.beans.factory.annotation.Autowired;

import com.detectlanguage.errors.APIError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.api.youspeaking.feature.Translation.TranslationService;

public class Utils {

    @Autowired TranslationService service;

    public static ObjectNode createAccountSuccess(String message) throws APIError{
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonResponse = mapper.createObjectNode();
        jsonResponse.put("status", "success");
        jsonResponse.put("message", message);
        return jsonResponse;
    }

    public static ObjectNode createAccountError(){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonResponse = mapper.createObjectNode();
        jsonResponse.put("status", "error");
        return jsonResponse;
    }

    public static ObjectNode loginSuccess(String message) throws APIError{
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonResponse = mapper.createObjectNode();
        jsonResponse.put("status", "success");
        jsonResponse.put("message", message);
        return jsonResponse;
    }

    public static ObjectNode loginError() throws APIError{
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonResponse = mapper.createObjectNode();
        jsonResponse.put("status", "error");
        return jsonResponse;
    }

    public static ObjectNode genericJsonSuccess() throws APIError{
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonResponse = mapper.createObjectNode();
        jsonResponse.put("status", "success");
        return jsonResponse;
    }

    public static ObjectNode genericJsonError() throws APIError{
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonResponse = mapper.createObjectNode();
        jsonResponse.put("status", "error");
        return jsonResponse;
    }

    public static ObjectNode jsonGreetings(String message, String language) throws APIError{
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonResponse = mapper.createObjectNode();
        jsonResponse.put("status", "success");
        jsonResponse.put("message", message);
        jsonResponse.put("language", language);
        return jsonResponse;
    }

}
