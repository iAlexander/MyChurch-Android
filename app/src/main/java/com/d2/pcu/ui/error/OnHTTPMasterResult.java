package com.d2.pcu.ui.error;

import com.d2.pcu.data.responses.OnMasterResponse;

public interface OnHTTPMasterResult<T extends OnMasterResponse> {

    void onSuccess(T response);

    void onFail(Throwable ex);
}
