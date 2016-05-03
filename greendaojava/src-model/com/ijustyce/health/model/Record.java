package com.ijustyce.news.model;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "RECORD".
 */
public class Record {

    private Long id;
    private String desc;
    private Integer userId;
    private Integer ownerId;
    private Integer type;
    private Boolean delete;
    private Integer status;

    public Record() {
    }

    public Record(Long id) {
        this.id = id;
    }

    public Record(Long id, String desc, Integer userId, Integer ownerId, Integer type, Boolean delete, Integer status) {
        this.id = id;
        this.desc = desc;
        this.userId = userId;
        this.ownerId = ownerId;
        this.type = type;
        this.delete = delete;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
