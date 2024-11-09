package br.com.api.youspeaking.feature.Login;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.api.youspeaking.data.entity.User;
import br.com.api.youspeaking.data.repository.UserRepository;
import br.com.api.youspeaking.utils.EncryptionUtils;
import br.com.api.youspeaking.utils.Utils;

@Service
public class AuthService {

    @Autowired UserRepository repository;
    
    protected ObjectNode verifyLogin(String login) throws Exception{
        User user = repository.findByLogin(login);
        if(user == null){
            return Utils.genericJsonSuccess();
        } 
        return Utils.genericJsonError();
    }

    protected String authLogin(ObjectNode json){
        try {
            User user = repository.findByLogin(json.get("login").asText());
            String encryptedPassword = Base64.getEncoder().encodeToString(json.get("password").asText().getBytes());
            if(user.getLogin().equals(json.get("login").asText()) && user.getPassword().equals(encryptedPassword)){
                Long classes = user.getNumClasses();
                user.setNumClasses(classes + 1L);
                user.setFlgLogged("S");
                repository.save(user);
                return user.getLanguage();
            } else {
                return null;
            }
        } catch (Exception e){
            return null;
        }
    }

    protected void createUser(ObjectNode json) throws Exception{
        if(json.has("role")){
            adminRegistration(json);
        } else {
            userRegistration(json);
        }
    }

    protected void userRegistration(ObjectNode json) throws Exception{
        try {
            User user = repository.findByLogin(json.get("login").asText());
            if(user == null){
                String encriptedPassword = Base64.getEncoder().encodeToString(json.get("password").asText().getBytes());
                User newUser = new User(
                    json.get("login").asText(), 
                    json.get("email").asText(), 
                    encriptedPassword, 
                    "USER", 
                    json.get("language").asText(),
                    1L,
                    json.get("name").asText());
                repository.save(newUser);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    protected void adminRegistration(ObjectNode json) throws Exception{
        try {
            User user = repository.findByLogin(json.get("login").asText());
            if(user == null){
                String encriptedPassword = EncryptionUtils.encrypt(json.get("password").asText());
                User newUser = new User(
                    json.get("login").asText(), 
                    encriptedPassword, 
                    json.get("email").asText(), 
                    json.get("role").asText(), 
                    json.get("language").asText(),
                    1L,
                    json.get("name").asText());
                repository.save(newUser);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    protected void deleteUser(String login){
        User user = repository.findByLogin(login);
        if(user != null){
            repository.delete(user);
        }
    }
}
