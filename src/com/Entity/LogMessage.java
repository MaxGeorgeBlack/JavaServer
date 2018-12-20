package com.Entity;

public class LogMessage {
    private String id;
    private String time;
    private String info;
    private String user_id;

    public String getId(){return this.id;}
    public void setId(String id){this.id = id;}

    public String getTime(){return this.time;}
    public void setTime(String time){this.time = time;}

    public String getInfo(){return this.info;}
    public void setInfo(String info){ this.info = info;}

    public String getUser_id(){return this.user_id;}
    public void setUser_id(String user_id){this.user_id = user_id;}
}
