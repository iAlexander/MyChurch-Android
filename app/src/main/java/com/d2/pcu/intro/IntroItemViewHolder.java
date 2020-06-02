package com.d2.pcu.intro;

import android.graphics.drawable.Drawable;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.databinding.ViewIntroItemBinding;

public class IntroItemViewHolder extends RecyclerView.ViewHolder {

    private ViewIntroItemBinding binding;

    private int position;

    private OnItemSetCheckedListener onItemSetCheckedListener;

    IntroItemViewHolder(ViewIntroItemBinding binding, OnItemSetCheckedListener onItemSetCheckedListener) {
        super(binding.getRoot());
        this.binding = binding;

        this.onItemSetCheckedListener = onItemSetCheckedListener;
    }

    void bind(String title, Drawable icon, boolean isSet, int position) {
        binding.introItemIv.setImageDrawable(icon);
        binding.introItemTv.setText(title);
        binding.introSwitcher.setChecked(isSet);

        this.position = position;

        binding.introSwitcher.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (onItemSetCheckedListener != null) {
                    onItemSetCheckedListener.onItemSetChecked(position);
                }
            }
        });
    }



}
