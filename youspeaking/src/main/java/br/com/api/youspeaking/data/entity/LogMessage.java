package br.com.api.youspeaking.data.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TB_LOG_MESSAGE")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "idLogMessage")
public class LogMessage {

    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private String idLogMessage;
    private String login;
    @Lob private String message;
    @Lob private String messageResponse;
    private Date   dateMessage;
    
    public LogMessage(String login, String message, String messageResponse,Date dateMessage){
        this.login           = login;
        this.message         = message;
        this.messageResponse = messageResponse;
        this.dateMessage     = dateMessage;
    }

    public LogMessage(){
        super();
    }
}
