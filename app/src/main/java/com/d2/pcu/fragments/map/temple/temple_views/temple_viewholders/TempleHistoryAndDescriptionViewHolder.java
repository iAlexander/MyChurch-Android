package com.d2.pcu.fragments.map.temple.temple_views.temple_viewholders;

import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.databinding.ViewTempleHistoryAndDescriptionBinding;

public class TempleHistoryAndDescriptionViewHolder extends TempleViewHolder {

    private Temple temple;

    private ViewTempleHistoryAndDescriptionBinding binding;

    public TempleHistoryAndDescriptionViewHolder(ViewTempleHistoryAndDescriptionBinding binding) {
        super(binding);
        this.binding = binding;
    }

    @Override
    public void bind(Temple temple) {
        this.temple = temple;
    }
}
