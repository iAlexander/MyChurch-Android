package com.d2.pcu.data.model.pray;

import com.d2.pcu.data.db.MasterDbModel;

import java.util.List;

public class PraysList extends MasterDbModel {

    private List<Pray> prays;

    public List<Pray> getPrays() {
        return prays;
    }

    public void setPrays(List<Pray> prays) {
        this.prays = prays;
    }
}
