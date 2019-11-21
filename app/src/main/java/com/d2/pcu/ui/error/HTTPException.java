package com.d2.pcu.ui.error;

public class HTTPException extends Exception {

    public HTTPException(final HTTPCode code) {
        super(code.getMessage());
    }
}
