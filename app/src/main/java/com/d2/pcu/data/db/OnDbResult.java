package com.d2.pcu.data.db;

public interface OnDbResult {

    void onSuccess(MasterDbModel dbModel);

    void onFail();
}
