package com.d2.pcu.fragments.pray.pray_views;

import com.d2.pcu.data.model.pray.Pray;
import com.d2.pcu.databinding.ViewPrayersBinding;

import java.util.List;

public class FavoritePrayersViewHolder extends PrayBaseViewHolder {

    private ViewPrayersBinding binding;

    public FavoritePrayersViewHolder(ViewPrayersBinding binding) {
        super(binding);
        this.binding = binding;
    }

    @Override
    public void bind(List<Pray> favorites) {

    }
}
