package com.d2.pcu.fragments.map;

import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.data.model.map.Temple;
import com.d2.pcu.databinding.ItemMapTempleBinding;

public class TempleItemViewHolder extends RecyclerView.ViewHolder {

    private ItemMapTempleBinding binding;

    TempleItemViewHolder(ItemMapTempleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.binding.setHolder(this);
    }

    void bind(Temple temple) {
        binding.setTemple(temple);
    }
}
