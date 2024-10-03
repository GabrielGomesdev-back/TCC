package br.com.api.youspeaking.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.api.youspeaking.data.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    
    User findByLogin(String login);
}
