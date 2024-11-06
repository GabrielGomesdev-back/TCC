package br.com.api.youspeaking.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.youspeaking.data.entity.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String>{
    
}
