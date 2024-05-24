package com.example.entity;

import java.util.List;

public class NodeEntity {
    private long id;
    private String text;
    private String state = "closed";

    private List<NodeEntity> children;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public List<NodeEntity> getChildren() {
        return children;
    }
    public void setChildren(List<NodeEntity> children) {
        this.children = children;
    }
}