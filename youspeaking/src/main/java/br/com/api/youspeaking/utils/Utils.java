package br.com.api.youspeaking.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Utils {
    
    public static ObjectNode loginSuccess(){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonResponse = mapper.createObjectNode();
        jsonResponse.put("status", "success");
        return jsonResponse;
    }

    public static ObjectNode loginError(){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonResponse = mapper.createObjectNode();
        jsonResponse.put("status", "error");
        return jsonResponse;
    }
}
