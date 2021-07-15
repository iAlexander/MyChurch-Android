package com.d2.pcu.fragments.map.temple.temple_views;

import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.databinding.ItemTemplePhotoBinding;
import com.squareup.picasso.Picasso;

public class TemplePhotoItemViewHolder extends RecyclerView.ViewHolder {

    private ItemTemplePhotoBinding binding;

    public TemplePhotoItemViewHolder(ItemTemplePhotoBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(String url) {
        if (url != null && !url.isEmpty()) {
            Picasso.get().load(url).into(binding.itemIv);
        }
    }
}
