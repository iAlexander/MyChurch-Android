package com.d2.pcu.fragments.pray_new.prayers_horizontal;

import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.data.model.pray.Pray;
import com.d2.pcu.databinding.FragmentItemPrayBinding;

public class PrayHorizontalViewHolder extends RecyclerView.ViewHolder {

    private FragmentItemPrayBinding binding;

    public PrayHorizontalViewHolder(FragmentItemPrayBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    void bind(Pray pray) {
        binding.setPray(pray);
    }
}
