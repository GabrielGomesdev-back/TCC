package br.com.api.youspeaking.feature.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.api.youspeaking.data.repository.UserRepository;
import br.com.api.youspeaking.utils.Utils;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/FT003/auth")
public class AuthController {
    
    @Autowired AuthService service; 
    @Autowired UserRepository userRepository;

    @PostMapping("/user-login")
    public ObjectNode userLogin(@RequestBody @Valid ObjectNode json){
        return service.authLogin(json) == true ? Utils.loginSuccess() : Utils.loginError();
    }

    @PostMapping("/user-register")
    public ObjectNode userRegister(@RequestBody @Valid ObjectNode json){
        if(userRepository.findByLogin(json.get("login").asText()) != null ) return Utils.loginError();
        return Utils.loginSuccess();
    }


}
