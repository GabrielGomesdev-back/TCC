package br.com.api.youspeaking.feature.Thirdparties.OpenAI;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import feign.HeaderMap;
import feign.Headers;
import feign.RequestLine;

public interface OpenApiClient {

    @RequestLine("POST v1/chat/completions")
    @Headers("Content-type: application/json")
    public String callOpenApi( @HeaderMap Map<String, String> headers, @RequestBody String json );

    @RequestLine("POST v1/audio/speech")
    @Headers("Content-type: application/json")
    public ResponseEntity<byte[]> callSpeechOpenApi ( @HeaderMap Map<String, String> headers, @RequestBody String json );
}
