package br.com.api.youspeaking.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.youspeaking.data.entity.QuizResult;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, String>  {
    
    List<QuizResult> findByLoginUserOrderByDateResultAsc(String loginUser);

}
