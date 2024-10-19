package br.com.api.youspeaking.data.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TB_USERS")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "userId")
public class User {
    
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private String userId;
    private String name;
    private String login;
    private String email;
    private String password;
    private String role;
    private String language;
    private Long   numClasses;
    private Date   registerDate;
    private String flgLogged;
    
    public User(String login, String email, String password, String role, String language, Long numClasses, String name){
        this.login        = login;
        this.email        = email;
        this.password     = password;
        this.role         = role;
        this.language     = language;
        this.numClasses   = numClasses;
        this.name         = name;
        this.registerDate = new Date();
        this.flgLogged    = "S";
    }

    public User(){
        super();
    }
}
