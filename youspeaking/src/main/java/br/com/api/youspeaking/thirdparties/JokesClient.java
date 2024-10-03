package br.com.api.youspeaking.thirdparties;

import com.fasterxml.jackson.databind.node.ObjectNode;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface JokesClient {
    
    @RequestLine("GET joke/Any?lang={lang}&type=single")
    @Headers("Content-type: application/json")
    public ObjectNode generateJoke(
        @Param("lang") String lang
        );
}
