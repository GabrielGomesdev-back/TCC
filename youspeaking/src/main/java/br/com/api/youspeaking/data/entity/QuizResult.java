package br.com.api.youspeaking.data.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_QUIZ")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "quizId")
public class QuizResult {
    
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private String quizId;
    private String loginUser;
    @Lob private String questionsGenerated;
    private String englishLevelResult;
    private Date   dateResult;

}
