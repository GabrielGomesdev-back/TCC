package br.com.api.youspeaking.feature.Translation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.detectlanguage.DetectLanguage;
import com.detectlanguage.errors.APIError;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.api.youspeaking.thirdparties.TranslatorClient;
import feign.Feign;
import feign.form.spring.SpringFormEncoder;
import feign.jackson.JacksonDecoder;
// import jakarta.transaction.Transactional;

// @Transactional
@Service
public class TranslationService {

    @Value("${you-speaking.url.translator}")
    private String urlStringTranslator;

    @Value("${you-speaking.url.api-key}")
    private String apiKeyString;

    public ObjectNode translateText(String text, String target) throws APIError {
        TranslatorClient translateClient = Feign.builder().encoder(new SpringFormEncoder()).decoder(new JacksonDecoder()).target(TranslatorClient.class, urlStringTranslator);
        ObjectNode node = translateClient.translatePrhase(text, this.langDetecString(text) + "|" + target);
        return node;
    }

    protected String langDetecString(String text) throws APIError{
        DetectLanguage.apiKey = apiKeyString;
        System.out.println(DetectLanguage.simpleDetect(text));
        return DetectLanguage.simpleDetect(text);
    }
}
