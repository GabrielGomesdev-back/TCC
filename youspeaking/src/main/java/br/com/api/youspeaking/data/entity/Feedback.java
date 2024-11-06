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
@Table(name = "TB_FEEDBACK_USER")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "idFeedback")
public class Feedback {
    
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private String idFeedback;
    private String login;
    @Lob private String feedbackUser;
    private String languageLevel;
    private Date   feedbackDate;
    
}
