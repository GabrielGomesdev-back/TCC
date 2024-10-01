package br.com.api.youspeaking.feature.ChatBot.EnglishLevel;

import java.util.Calendar;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {
    private String from;
    private String text;
    private Calendar time;
}
