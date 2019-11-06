package com.d2.pcu.fragments.map;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.R;
import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.databinding.ItemMapTempleBinding;

import java.util.ArrayList;
import java.util.List;

public class TempleAdapter extends RecyclerView.Adapter<TempleItemViewHolder> {

    private List<Temple> temples = new ArrayList<>();

    void setTemples(List<Temple> temples) {
        this.temples.clear();
        this.temples.addAll(temples);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TempleItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ItemMapTempleBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_map_temple, parent, false);

        return new TempleItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TempleItemViewHolder holder, int position) {
        holder.bind(temples.get(position));
    }

    @Override
    public int getItemCount() {
        return temples == null ? 0 : temples.size();
    }
}
