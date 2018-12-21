package com.Entity;

public class Token {
    private String token;
    private String user_id;
    private String expiration;

    public String getToken(){return this.token;}
    public String getUser_id(){return this.user_id;}
    public String getExpiration(){return this.expiration;}

    public void setToken(String token){this.token = token;}
    public void setUser_id(String user_id){this.user_id = user_id;}
    public void setExpiration(String expiration){this.expiration = expiration;}
}
