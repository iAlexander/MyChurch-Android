package com.d2.pcu.login.sign_up.adapters_and_viewholders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.data.model.BaseProfileModel;
import com.d2.pcu.data.model.map.temple.BaseTemple;
import com.d2.pcu.databinding.ViewItemSearchedTempleShortBinding;

public class ProfileTemplesDialogItemViewHolder extends RecyclerView.ViewHolder {

    private ViewItemSearchedTempleShortBinding binding;
    private OnDialogItemClickListener onDialogItemClickListener;

    private BaseTemple shortTemple;

    ProfileTemplesDialogItemViewHolder(@NonNull ViewItemSearchedTempleShortBinding binding,
            OnDialogItemClickListener onDialogItemClickListener
    ) {
        super(binding.getRoot());
        this.binding = binding;
        this.onDialogItemClickListener = onDialogItemClickListener;

        binding.setHolder(this);
    }

    public void bind(BaseTemple shortTemple) {
        binding.setTemple(shortTemple);
        this.shortTemple = shortTemple;
    }

    public void onItemClick(View view) {
        if (onDialogItemClickListener != null) {
            onDialogItemClickListener.onTempleItemClick(shortTemple);
        }
    }
}
