package com.d2.pcu.data.model;

public abstract class BaseProfileModel {

    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public BaseProfileModel() {
        id = -1;
        name = "";
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
