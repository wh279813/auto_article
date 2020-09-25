package com.hackathon.autoarticle.entity;

public class Category {
    private long id;
    private long parent_id;
    private String name;
    private int leve;

    public Category(long id, long parent_id, String name, int leve) {
        this.id = id;
        this.parent_id = parent_id;
        this.name = name;
        this.leve = leve;
    }
    public Category(long id) {
        this.id = id;
    }
    public Category() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParent_id() {
        return parent_id;
    }

    public void setParent_id(long parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLeve() {
        return leve;
    }

    public void setLeve(int leve) {
        this.leve = leve;
    }
}
