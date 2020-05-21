package com.d2.pcu.ui.error;

import com.d2.pcu.data.responses.OnMasterResponse;

public interface OnHTTPResult {

    void onSuccess(OnMasterResponse response);

    default void onSuccessString(String body){

    }

    void onFail(Throwable ex);
}
