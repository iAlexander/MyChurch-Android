package com.d2.pcu.data.model.calendar;

public class Group {

    private int id;

    private String name;

    private String color;

    public Group() {
        id = -1;
        name = "";
        color = "FF000000";
    }

    public int getId() {
        return id;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
