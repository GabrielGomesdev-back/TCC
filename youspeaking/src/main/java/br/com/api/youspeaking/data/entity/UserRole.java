package br.com.api.youspeaking.data.entity;

public enum UserRole {
    
    ADMIN("youspeakingAdm"),
    USER("user");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
