package br.com.api.youspeaking.feature.Thirdparties.OpenAI;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.api.youspeaking.vo.ChatRequestVO;
import feign.Feign;

@Service
public class OpenApiService {
    
    @Value("${you-speaking.url.open.api}") private String url;
    @Value("${you-speaking.url.open.api-key}") private String token;

    public ObjectNode callOpenAI (ChatRequestVO requestInfo) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        headers.put("Content-Type", "application/json");

        OpenApiClient openApiClient = Feign.builder().target(OpenApiClient.class, url);
        String responseApi = openApiClient.callOpenApi(headers, mapper.writeValueAsString(requestInfo));
        return (ObjectNode) mapper.readTree(responseApi);
    }

    public ResponseEntity<byte[]> callOpenAISpeech (String text) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode requestBody = mapper.createObjectNode();

        requestBody.put("model", "tts-1");
        requestBody.put("input", text);
        requestBody.put("voice", "alloy");

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        headers.put("Content-Type", "application/json");

        OpenApiClient openApiClient = Feign.builder().target(OpenApiClient.class, url);
        return openApiClient.callSpeechOpenApi(headers, mapper.writeValueAsString(requestBody));
    }

}
