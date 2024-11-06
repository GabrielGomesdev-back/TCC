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
@Table(name = "TB_FEEDBACK_USER")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "idFeedback")
public class Feedback {
    
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private String idFeedback;
    private String login;
    private String feedbackUser;
    private String languageLevel;
    private Date   feedbackDate;
    
}
