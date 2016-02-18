package com.ijustyce.contacts.model;

/**
 * Created by yc on 16-2-8. 短信记录列表
 */
public class MsgModel {

    private String name;
    private String message;
    private String head;

    public MsgModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
