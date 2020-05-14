package com.d2.pcu.fragments.pray_new.prayers_horizontal;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.R;
import com.d2.pcu.data.model.pray.Pray;
import com.d2.pcu.databinding.FragmentItemPrayBinding;

import java.util.ArrayList;
import java.util.List;

public class PrayHorizontalAdapter extends RecyclerView.Adapter<PrayHorizontalAdapter.PrayHorizontalViewHolder> {

    private List<Pray> prays;

    PrayHorizontalAdapter() {
        prays = new ArrayList<>();
    }

    public void setPrays(List<Pray> prays) {
        this.prays.clear();
        this.prays.addAll(prays);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PrayHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        FragmentItemPrayBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_item_pray, parent, false
        );

        return new PrayHorizontalViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PrayHorizontalViewHolder holder, int position) {
        holder.bind(prays.get(position));
    }

    @Override
    public int getItemCount() {
        return prays == null ? 0 : prays.size();
    }

    public Pray getItemByPosition(int index) {
        if (prays != null && prays.size() > index) {
            return prays.get(index);
        } else {
            return new Pray();
        }
    }

    static class PrayHorizontalViewHolder extends RecyclerView.ViewHolder {

        private FragmentItemPrayBinding binding;

        PrayHorizontalViewHolder(FragmentItemPrayBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Pray pray) {
            binding.setPray(pray);
        }
    }
}


