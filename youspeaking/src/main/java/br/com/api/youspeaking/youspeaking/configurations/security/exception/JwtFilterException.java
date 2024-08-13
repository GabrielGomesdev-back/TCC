package br.com.api.youspeaking.youspeaking.configurations.security.exception;

public class JwtFilterException extends Exception{
    
    public JwtFilterException(String msg){
        super(msg);
    }

    public JwtFilterException(String msg, Throwable cause){
        super(msg, cause);
    }
}
