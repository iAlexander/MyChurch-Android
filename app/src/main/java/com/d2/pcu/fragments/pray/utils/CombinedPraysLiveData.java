package com.d2.pcu.fragments.pray.utils;


import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.d2.pcu.data.model.pray.Pray;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CombinedPraysLiveData extends MediatorLiveData<Pair<List<Pray>, List<Pray>>> {

    private List<Pray> praysFromServer = Collections.emptyList();
    private List<Pray> praysFromDb = Collections.emptyList();

    public CombinedPraysLiveData(LiveData<List<Pray>> serverPrays, LiveData<List<Pray>> dbPrays) {
        setValue(Pair.create(praysFromServer, praysFromDb));

        addSource(serverPrays, (sPrays) -> {
            if (sPrays != null && !sPrays.isEmpty()) {

                for (Pray pray : praysFromDb) {

                    Iterator<Pray> iterator = sPrays.iterator();
                    while (iterator.hasNext()) {
                        if (pray.getId() == iterator.next().getId()) {
                            iterator.remove();
                        }
                    }
                }

                this.praysFromServer = sPrays;
            }
            setValue(Pair.create(praysFromServer, praysFromDb));
        });

        addSource(dbPrays, (dPrays) -> {
            if (dPrays != null && !dPrays.isEmpty()) {

                for (Pray pray : dPrays) {

                    Iterator<Pray> iterator = praysFromServer.iterator();
                    while (iterator.hasNext()) {
                        if (pray.getId() == iterator.next().getId()) {
                            iterator.remove();
                        }
                    }
                }

                this.praysFromDb = dPrays;
            }
            setValue(Pair.create(praysFromServer, praysFromDb));
        });
    }
}
