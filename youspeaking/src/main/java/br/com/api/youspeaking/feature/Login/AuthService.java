package br.com.api.youspeaking.feature.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.api.youspeaking.data.entity.User;
import br.com.api.youspeaking.data.entity.UserRole;
import br.com.api.youspeaking.data.repository.UserRepository;
import br.com.api.youspeaking.utils.EncryptionUtils;

@Service
public class AuthService {

    @Value("${aes.key}") private String aesKey;
    @Autowired private UserRepository repository;
    private AuthenticationManager authenticationManager;
    
    protected Boolean authLogin(ObjectNode json){
        try {
            var userPassword = new UsernamePasswordAuthenticationToken(json.get("login").asText(), json.get("password").asText());
            var auth = authenticationManager.authenticate(userPassword);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    protected void createUser(ObjectNode json) throws Exception{
        if(json.get("role").asText().equals("ADMIN")){
            adminRegistration(json);
        } else {
            userRegistration(json);
        }
    }

    protected void userRegistration(ObjectNode json) throws Exception{
        try {
            String encriptedPassword = EncryptionUtils.encrypt(json.get("password").asText());
            User newUser = new User(
                json.get("login").asText(), 
                encriptedPassword, 
                json.get("email").asText(), 
                UserRole.USER, 
                json.get("language").asText());
            repository.save(newUser);
        } catch (Exception e) {
            throw e;
        }
    }

    protected void adminRegistration(ObjectNode json) throws Exception{
        try {
            String encriptedPassword = EncryptionUtils.encrypt(json.get("password").asText());
            User newUser = new User(
                json.get("login").asText(), 
                encriptedPassword, 
                json.get("email").asText(), 
                UserRole.ADMIN, 
                json.get("language").asText());
            repository.save(newUser);
        } catch (Exception e) {
            throw e;
        }
    }
}
