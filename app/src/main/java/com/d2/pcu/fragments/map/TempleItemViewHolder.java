package com.d2.pcu.fragments.map;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.data.model.map.temple.BaseTemple;
import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.databinding.ItemMapTempleBinding;

public class TempleItemViewHolder extends RecyclerView.ViewHolder {

    private ItemMapTempleBinding binding;

    private OnTempleClickListener onTempleClickListener;
    private OnGetRouteClickListener onGetRouteClickListener;

    private BaseTemple temple;

    TempleItemViewHolder(ItemMapTempleBinding binding,
                         OnTempleClickListener onTempleClickListener,
                         OnGetRouteClickListener onGetRouteClickListener) {
        super(binding.getRoot());
        this.binding = binding;
        this.binding.setHolder(this);

        this.onTempleClickListener = onTempleClickListener;
        this.onGetRouteClickListener = onGetRouteClickListener;
    }

    void bind(BaseTemple temple) {
        binding.setTemple(temple);
        this.temple = temple;
    }

    public void onTempleClick(View view) {
        if (onTempleClickListener != null) {
            onTempleClickListener.onMoreTempleInfoClick(temple);
        }
    }

    public void onGetRouteClick(View view) {
        if (onGetRouteClickListener != null) {
            onGetRouteClickListener.getRoute(temple.getLatLng());
        }
    }
}
