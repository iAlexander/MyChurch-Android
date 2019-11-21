package com.d2.pcu.fragments.map.temple.temple_views;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.R;
import com.d2.pcu.databinding.ItemTemplePhotoBinding;

import java.util.ArrayList;
import java.util.List;

public class TemplePhotoAdapter extends RecyclerView.Adapter<TemplePhotoItemViewHolder> {

    private List<String> imageUrls = new ArrayList<>();

    public void setUrls(List<String> imageUrls) {
        this.imageUrls.clear();
        this.imageUrls.addAll(imageUrls);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TemplePhotoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ItemTemplePhotoBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_temple_photo, parent, false);

        return new TemplePhotoItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TemplePhotoItemViewHolder holder, int position) {
        holder.bind(imageUrls.get(position));
    }

    @Override
    public int getItemCount() {
        return imageUrls == null ? 0 : imageUrls.size();
    }
}
