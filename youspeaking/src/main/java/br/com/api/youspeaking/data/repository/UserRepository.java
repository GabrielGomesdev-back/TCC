package br.com.api.youspeaking.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.api.youspeaking.data.entity.User;

public interface UserRepository extends JpaRepository<User, String >{
    
    UserDetails findByLogin(String Login);
}
