package br.com.api.youspeaking.data.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.youspeaking.data.entity.LogMessage;

@Repository
public interface LogMessageRepository  extends JpaRepository<LogMessage, String> {
    
    ArrayList<LogMessage> findAllByLoginOrderByDateMessageAsc(String login);

}
