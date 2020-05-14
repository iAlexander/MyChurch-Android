package com.d2.pcu.fragments.map.temple;

import com.d2.pcu.App;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.fragments.BaseViewModel;

public class TempleViewModel extends BaseViewModel {

    private Repository repository;

    private Temple temple;

    public TempleViewModel() {
        repository = App.getInstance().getRepositoryInstance();
    }

    public void setTemple(Temple temple) {
        this.temple = temple;
    }

    public Temple getTemple() {
        return temple;
    }

}
