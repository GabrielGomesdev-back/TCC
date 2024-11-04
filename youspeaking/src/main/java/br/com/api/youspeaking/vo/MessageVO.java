package br.com.api.youspeaking.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageVO {

    private String role;
    private String content;

    public MessageVO(String content, String role){
        this.content = content;
        this.role    = role;
    }
    
}
