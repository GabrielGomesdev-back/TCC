package br.com.api.youspeaking.data.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.youspeaking.data.entity.Feedback;


@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String>{
    
    Feedback findByLogin(String login);
    
    ArrayList<Feedback> findAllByLoginOrderByFeedbackDateAsc(String login);

}
