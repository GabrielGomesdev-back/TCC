package br.com.api.youspeaking.feature.Translation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.detectlanguage.errors.APIError;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("api/v1/FT001/translation")
public class TranslationController {
    
    @Autowired private TranslationService service;

    @GetMapping(value = "/translate-text")
    public ObjectNode translateText(@RequestParam String text, @RequestParam String target) throws APIError {
        return service.translateText(text, target);
    }
}
