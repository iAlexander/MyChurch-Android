package com.d2.pcu.fragments.map.temple.temple_views.temple_viewholders;

import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.databinding.FragmentExtTempleContactBinding;

public class TempleContactsViewHolder extends TempleViewHolder {

    private Temple temple;

    private FragmentExtTempleContactBinding binding;

    public TempleContactsViewHolder(FragmentExtTempleContactBinding binding) {
        super(binding);
        this.binding = binding;
    }

    @Override
    public void bind(Temple temple) {
        this.temple = temple;

        binding.templeContactsView.setTemple(temple);
    }
}
