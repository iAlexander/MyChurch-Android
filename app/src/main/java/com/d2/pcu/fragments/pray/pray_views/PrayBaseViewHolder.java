package com.d2.pcu.fragments.pray.pray_views;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PrayBaseViewHolder extends RecyclerView.ViewHolder {

    public PrayBaseViewHolder(ViewDataBinding dataBinding) {
        super(dataBinding.getRoot());
    }

    public abstract void bind();
}
