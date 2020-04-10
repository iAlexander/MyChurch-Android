package com.d2.pcu.ui.utils;

import android.app.Activity;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.d2.pcu.R;

public class UIUtils {

    public static AlertDialog assembleModeratingDialog(Activity activity, String msg, String btnTitle) {
        if (activity != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_check_profile, null);

            final AppCompatTextView policyTextView = dialogView.findViewById(R.id.moderating_tv);
            if (msg != null && !msg.isEmpty()) {
                policyTextView.setText(msg);
            }

            final AppCompatButton applyBtn = dialogView.findViewById(R.id.apply_moderating_btn);
            if (btnTitle != null && !btnTitle.isEmpty()) {
                applyBtn.setText(btnTitle);
            }

            builder.setView(dialogView);

            final AlertDialog dialog = builder.create();

            applyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.setCanceledOnTouchOutside(false);

            return dialog;
        }
        return null;
    }

    public static AlertDialog assembleModeratingDialog(Activity activity, CharSequence msg, String btnTitle) {
        if (activity != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_check_profile, null);

            final AppCompatTextView policyTextView = dialogView.findViewById(R.id.moderating_tv);
            if (msg != null && msg.length() != 0) {
                policyTextView.setText(msg);
            }

            final AppCompatButton applyBtn = dialogView.findViewById(R.id.apply_moderating_btn);
            if (btnTitle != null && !btnTitle.isEmpty()) {
                applyBtn.setText(btnTitle);
            }

            builder.setView(dialogView);

            final AlertDialog dialog = builder.create();

            applyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.setCanceledOnTouchOutside(false);

            return dialog;
        }
        return null;
    }
}
