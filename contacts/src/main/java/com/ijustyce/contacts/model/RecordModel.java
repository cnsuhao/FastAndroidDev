package com.ijustyce.contacts.model;

/**
 * Created by yc on 16-2-8. 通话记录列表model
 */
public class RecordModel {

    private String name;
    private String date;
    private int type;
    private String head;

    public RecordModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
