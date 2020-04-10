package com.d2.pcu.fragments.pray_new.vertical_items.pager_holders;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.data.model.pray.Pray;

import java.util.List;

public abstract class PrayBaseViewHolder extends RecyclerView.ViewHolder {

    PrayBaseViewHolder(ViewDataBinding dataBinding) {
        super(dataBinding.getRoot());
    }

    public abstract void bind(List<Pray> prays);
}
