package com.d2.pcu.data.db;

import timber.log.Timber;

public interface OnDbResult {

    void onSuccess(MasterDbModel dbModel);

    default void onFail() {
        Timber.w("fail db");
    }
}
