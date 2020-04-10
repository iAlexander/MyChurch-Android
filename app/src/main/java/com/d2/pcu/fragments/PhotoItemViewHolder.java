package com.d2.pcu.fragments;

import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.databinding.ItemPhotoBinding;
import com.squareup.picasso.Picasso;

public class PhotoItemViewHolder extends RecyclerView.ViewHolder {

    private ItemPhotoBinding binding;

    public PhotoItemViewHolder(ItemPhotoBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(String url) {
        if (url != null && !url.isEmpty()) {
            Picasso.get().load(url).into(binding.itemIv);
        }
    }
}
