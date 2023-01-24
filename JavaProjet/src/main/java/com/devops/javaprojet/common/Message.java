package com.devops.javaprojet.common;

import java.io.Serializable;

public class Message implements Serializable {
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private String sender;
    private String content;
    private int type;

    public Message(String sender,String content,int type){
        this.sender = sender;
        this.content = content;
        this.type = type;
    }
}
