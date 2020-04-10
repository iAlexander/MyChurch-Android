package com.d2.pcu.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.d2.pcu.R;

public class DialogSelector extends LinearLayout {

    private AppCompatImageView dialogButton;
    private AppCompatTextView dialogSelectedText;

    public DialogSelector(Context context) {
        super(context);
        init(context);
    }

    public DialogSelector(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        TypedArray array = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.DialogSelector, 0, 0);

        dialogSelectedText.setText(array.getString(R.styleable.DialogSelector_DialogSelectedText));
    }

    public DialogSelector(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.layout_dialog_selector, this);
        setBackgroundResource(R.drawable.bg_rounded_blue);

        dialogButton = findViewById(R.id.selector_arrow_btn);
        dialogSelectedText = findViewById(R.id.selector_text_atv);
    }

    public void setDialogSelectedText(String text) {
        this.dialogSelectedText.setText(text);
    }

    public String getDialogSelectedText() {
        return this.dialogSelectedText.getText().toString();
    }

    public void hideDialogButton(boolean isHide) {
        dialogButton.setVisibility(isHide ? View.GONE : View.VISIBLE);
    }
}
