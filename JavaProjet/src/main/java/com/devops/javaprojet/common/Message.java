package com.devops.javaprojet.common;

import java.io.Serializable;
import java.util.Map;

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

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    private Map<String, String> map;

    public Message(String sender, String content, int type) {
        this.sender = sender;
        this.content = content;
        this.type = type;
    }

    public Message(String sender, Map<String, String> map, int type) {
        this.sender = sender;
        this.map = map;
        this.type = type;
    }
}
