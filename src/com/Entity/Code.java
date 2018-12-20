package com.Entity;

public class Code {
    private String id;
    private String user_id;
    private String code;
    private String send_time;
    private String dead_time;

    public String getId(){return this.id;}
    public String getUser_id(){return this.user_id;}
    public String getCode(){return this.code;}
    public String getSend_time(){return this.send_time;}
    public String getDead_time(){return this.dead_time;}

    public void setId(String id){this.id = id;}
    public void setUser_id(String user_id){this.user_id = user_id;}
    public void setCode(String code){this.code = code;}
    public void setSend_time(String send_time){this.send_time = send_time;}
    public void setDead_time(String dead_time){this.dead_time = dead_time;}

}
