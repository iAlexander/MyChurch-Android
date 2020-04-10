package com.d2.pcu.data.responses;

public class BoolResponse extends OnMasterResponse {

    private boolean ok;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
