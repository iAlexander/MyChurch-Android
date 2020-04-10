package com.d2.pcu.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.R;
import com.d2.pcu.databinding.ItemPhotoBinding;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoItemViewHolder> {

    private List<String> imageUrls = new ArrayList<>();

    public void setUrls(List<String> imageUrls) {
        this.imageUrls.clear();
        this.imageUrls.addAll(imageUrls);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ItemPhotoBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_photo, parent, false);

        return new PhotoItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoItemViewHolder holder, int position) {
        holder.bind(imageUrls.get(position));
    }

    @Override
    public int getItemCount() {
        return imageUrls == null ? 0 : imageUrls.size();
    }
}
