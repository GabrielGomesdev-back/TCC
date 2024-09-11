package br.com.api.youspeaking.thirdparties;

import com.fasterxml.jackson.databind.node.ObjectNode;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface TranslatorClient {

    @RequestLine("GET /get?q={q}&langpair={langpair}")
    @Headers("Content-type: application/json")
    public ObjectNode translatePrhase(
        @Param("q") String q,
        @Param("langpair") String langpair
        );

}
