package com.d2.pcu.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.d2.pcu.R;
import com.d2.pcu.databinding.ButtonWithArrowBinding;

public final class ButtonWithArrow extends FrameLayout {

    public ButtonWithArrow(@NonNull Context context) {
        super(context);
        init(null);
    }

    public ButtonWithArrow(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ButtonWithArrow(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        ButtonWithArrowBinding layout = ButtonWithArrowBinding.inflate(layoutInflater, this, true);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ButtonWithArrow);
        int text;
        int description;
        try {
            text = a.getResourceId(R.styleable.ButtonWithArrow_bwaText, R.id.resource_unset);
            description = a.getResourceId(R.styleable.ButtonWithArrow_bwaDescription, R.id.resource_unset);
        } finally {
            a.recycle();
        }
        if (text == R.id.resource_unset) {
            layout.tvBtn.setText(null);
        } else {
            layout.tvBtn.setText(text);
        }

        if (description == R.id.resource_unset) {
            layout.tvDescription.setVisibility(GONE);
            layout.tvDescription.setText(null);
        } else {
            layout.tvDescription.setText(description);
            layout.tvDescription.setVisibility(VISIBLE);
        }
    }
}
