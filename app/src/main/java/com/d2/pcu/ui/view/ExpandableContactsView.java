package com.d2.pcu.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.d2.pcu.databinding.ExpandableContacsViewBinding;

public final class ExpandableContactsView extends FrameLayout {
    private boolean expanded = false;
    private ExpandableContacsViewBinding layout;
    private OnToggleListener listener;

    public interface OnToggleListener {
        void onToggle(boolean expanded);
    }

    public ExpandableContactsView(@NonNull Context context) {
        super(context);
        init();
    }

    public ExpandableContactsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExpandableContactsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        layout = ExpandableContacsViewBinding.inflate(layoutInflater, this, true);

        initExpanded();
        layout.flExpandable.setOnClickListener(v -> toggleExpanded());
        layout.llExpanded.setOnClickListener(v -> collapse());
    }

    public void setListener(OnToggleListener listener) {
        this.listener = listener;
    }

    public void collapse() {
        if (expanded) toggleExpanded();
    }

    private void initExpanded() {
        layout.ivSelector.setRotation(0f);
        layout.llExpanded.setVisibility(GONE);
    }

    private void toggleExpanded() {
        expanded = !expanded;
        if (listener != null) listener.onToggle(expanded);
        layout.ivSelector.setRotation(expanded ? 180f : 0f);
        layout.llExpanded.setVisibility(expanded ? VISIBLE : GONE);
    }
}
