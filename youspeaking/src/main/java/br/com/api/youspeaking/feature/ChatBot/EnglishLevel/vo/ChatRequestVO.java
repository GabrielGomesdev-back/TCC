package br.com.api.youspeaking.feature.ChatBot.EnglishLevel.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatRequestVO {

    private String model;
    private List<MessageVO> messages;

}
