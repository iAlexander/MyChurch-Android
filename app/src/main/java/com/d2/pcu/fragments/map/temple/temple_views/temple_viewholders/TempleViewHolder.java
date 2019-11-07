package com.d2.pcu.fragments.map.temple.temple_views.temple_viewholders;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.data.model.map.temple.Temple;

public abstract class TempleViewHolder extends RecyclerView.ViewHolder {

    TempleViewHolder(ViewDataBinding dataBinding) {
        super(dataBinding.getRoot());
    }

    public abstract void bind(Temple temple);
}
