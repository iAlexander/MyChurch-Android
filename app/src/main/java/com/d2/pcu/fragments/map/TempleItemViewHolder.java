package com.d2.pcu.fragments.map;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.databinding.ItemMapTempleBinding;

public class TempleItemViewHolder extends RecyclerView.ViewHolder {

    private ItemMapTempleBinding binding;

    private OnTempleClickListener onTempleClickListener;

    private Temple temple;

    TempleItemViewHolder(ItemMapTempleBinding binding, OnTempleClickListener onTempleClickListener) {
        super(binding.getRoot());
        this.binding = binding;
        this.binding.setHolder(this);

        this.onTempleClickListener = onTempleClickListener;
    }

    void bind(Temple temple) {
        binding.setTemple(temple);
        this.temple = temple;
    }

    public void onTempleClick(View view) {
        if (onTempleClickListener != null) {
            onTempleClickListener.onMoreTempleInfoClick(temple);
        }
    }
}
