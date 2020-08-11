package com.d2.pcu.data.responses.profile;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class History<T> {
    @SerializedName("list")
    private List<T> list;

    public List<T> getList() {
        if (list == null)
            list = new ArrayList<>();
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
