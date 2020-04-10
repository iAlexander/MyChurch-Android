package com.d2.pcu.listeners;

import android.view.View;

public interface InfoDialogListener {

    void showInfoDialog(String msg, String btnTitle);

    void showInfoDialog(int msgId, int btnTitleId);

    default void showInfoDialog(String msg, String btnTitle, View.OnClickListener onClickListener) {
    }
}
