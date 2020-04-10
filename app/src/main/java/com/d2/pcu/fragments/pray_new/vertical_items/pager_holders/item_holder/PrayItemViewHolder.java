package com.d2.pcu.fragments.pray_new.vertical_items.pager_holders.item_holder;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.data.model.pray.Pray;
import com.d2.pcu.databinding.ViewItemPrayBinding;
import com.d2.pcu.fragments.pray.OnPrayItemClickListener;

public class PrayItemViewHolder extends RecyclerView.ViewHolder {

    private ViewItemPrayBinding binding;
    private OnPrayItemClickListener onPrayItemClickListener;
    private Pray pray;

    private int position;

    PrayItemViewHolder(ViewItemPrayBinding binding, OnPrayItemClickListener onPrayItemClickListener) {
        super(binding.getRoot());
        this.binding = binding;
        this.onPrayItemClickListener = onPrayItemClickListener;

        binding.setHolder(this);
    }

    void bind(Pray pray, int position) {
        this.binding.setPray(pray);
        this.pray = pray;
        this.position = position;
    }

    public void onItemPressed(View view) {
        if (onPrayItemClickListener != null) {
            onPrayItemClickListener.onPrayClick(pray, position);
        }
    }
}
