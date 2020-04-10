package com.d2.pcu.login.sign_up.adapters_and_viewholders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.data.model.BaseProfileModel;

public abstract class ProfileBaseViewHolder extends RecyclerView.ViewHolder {

    public ProfileBaseViewHolder(@NonNull ViewDataBinding viewDataBinding) {
        super(viewDataBinding.getRoot());
    }

    public abstract void bind(BaseProfileModel dataModel);

    public abstract void onItemClick(View view);
}
