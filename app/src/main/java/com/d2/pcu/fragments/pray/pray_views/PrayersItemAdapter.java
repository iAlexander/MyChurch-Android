package com.d2.pcu.fragments.pray.pray_views;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.R;
import com.d2.pcu.data.model.pray.Pray;
import com.d2.pcu.databinding.ViewItemPrayBinding;
import com.d2.pcu.fragments.pray.OnPrayItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class PrayersItemAdapter extends RecyclerView.Adapter<PrayItemViewHolder> {

    private List<Pray> prays;

    private String type;

    private OnPrayItemClickListener onPrayItemClickListener;

    PrayersItemAdapter(String type, OnPrayItemClickListener onPrayItemClickListener) {
        prays = new ArrayList<>();

        this.type = type;
        this.onPrayItemClickListener = onPrayItemClickListener;
        setHasStableIds(true);
    }

    void setPrays(List<Pray> prays) {
        this.prays.clear();
        this.prays.addAll(prays);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PrayItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ViewItemPrayBinding binding = DataBindingUtil.inflate(inflater, R.layout.view_item_pray, parent, false);

        return new PrayItemViewHolder(binding, onPrayItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PrayItemViewHolder holder, int position) {
        holder.bind(prays.get(position), position);
    }

    @Override
    public int getItemCount() {
        return prays == null ? 0 : prays.size();
    }

    @Override
    public long getItemId(int position) {
        return prays.get(position).getId();
    }
}
