package com.d2.pcu.data.model.diocese;


import androidx.annotation.NonNull;

public class Diocese {

    private int id;

    private String name;

    public Diocese() {
        this.id = -1;
        this.name = "";
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

    @NonNull
    @Override
    public String toString() {
        return "Diocese{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
