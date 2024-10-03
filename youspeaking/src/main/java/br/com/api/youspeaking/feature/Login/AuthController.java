package br.com.api.youspeaking.feature.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.detectlanguage.errors.APIError;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.api.youspeaking.data.repository.UserRepository;
import br.com.api.youspeaking.feature.Translation.TranslationService;
import br.com.api.youspeaking.utils.Utils;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api/v1/FT003/auth")
public class AuthController {
    
    @Autowired AuthService service; 
    @Autowired UserRepository userRepository;
    @Autowired TranslationService translationService;

    private final String messageCreation = "Your account have been created with success";
    private final String messageLogin = "Your account have been logged with success";

    @GetMapping("/verify-login")
    public ObjectNode verifyLogin(@RequestParam String login) throws Exception {
        return service.verifyLogin(login);
    }
    
    @PostMapping("/user-login")
    public ObjectNode userLogin(@RequestBody @Valid ObjectNode json) throws APIError{
        String language = service.authLogin(json);
        if(language != null) {
        return Utils.loginSuccess(!"en".equals(language) ? translationService.translateText(messageLogin, language).get("responseData").get("translatedText").asText() : messageLogin);
        }  else { return Utils.loginError(); }
    }

    @PostMapping("/user-register")
    public ObjectNode userRegister(@RequestBody @Valid ObjectNode json){
        try {
            if(userRepository.findByLogin(json.get("login").asText()) != null ) return Utils.createAccountError();
            service.createUser(json);
            return Utils.createAccountSuccess(!"en".equals(json.get("language").asText()) ? translationService.translateText(messageCreation, json.get("language").asText()).get("responseData").get("translatedText").asText() : messageCreation);
        } catch(Exception e){ return Utils.createAccountError(); }
    }

    @PostMapping("/delete-user")
    public void deleteUser(@RequestParam String login){
        service.deleteUser(login);
    }


}
