package br.com.api.youspeaking.data.entity;


import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
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
@EqualsAndHashCode(of = "id")
public class User implements UserDetails{
    
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private String   id;
    private String login;
    private String email;
    private String password;
    private UserRole role;
    private String language;
    
    public User(String login, String email, String password, UserRole role, String language){
        this.login    = login;
        this.email    = email;
        this.password = password;
        this.role     = role;
        this.language = language;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role.equals(UserRole.ADMIN)) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getUsername() {
        return login;
    }

}
