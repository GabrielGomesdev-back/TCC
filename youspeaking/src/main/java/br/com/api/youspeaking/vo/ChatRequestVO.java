package br.com.api.youspeaking.vo;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ChatRequestVO {

    private String model;
    private List<MessageVO> messages;   

}
