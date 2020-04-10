package com.d2.pcu.ui.error;

import androidx.annotation.Nullable;

public class HTTPException extends Exception {

    public HTTPException(final HTTPCode code) {
        super(code.getMessage());
    }

    public HTTPException(String message) {
        super(message);
    }
}
