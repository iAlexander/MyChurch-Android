package com.d2.pcu.ui.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public final class ClearErrorTextWatcher {

    private TextInputLayout view;
    private EditText et;
    private TextWatcher tw;


    public ClearErrorTextWatcher(TextInputLayout view) {
        this.view = view;
        tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                view.setError(null);
                view.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    public void startTrack() {
        et = view.getEditText();
        if (et != null) {
            et.addTextChangedListener(tw);
        }
    }

    public void stopTrack() {
        et = view.getEditText();
        if (et != null) {
            et.removeTextChangedListener(tw);
        }
    }
}
