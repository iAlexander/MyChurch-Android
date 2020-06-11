package com.d2.pcu.fragments.map.temple.temple_views.temple_viewholders;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;

import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.databinding.ViewTempleHistoryAndDescriptionBinding;
import com.d2.pcu.fragments.PhotoAdapter;

public class TempleHistoryAndDescriptionViewHolder extends TempleViewHolder {

    private Temple temple;
    private PhotoAdapter adapter;
    private Context context;

    private ViewTempleHistoryAndDescriptionBinding binding;

    public TempleHistoryAndDescriptionViewHolder(Context context, ViewTempleHistoryAndDescriptionBinding binding) {
        super(binding);
        this.binding = binding;
        this.context = context;

        adapter = new PhotoAdapter();
    }

    @Override
    public void bind(Temple temple) {
        this.temple = temple;
        binding.setTemple(temple);

        binding.templeContactsPhotoRv.setAdapter(adapter);
        binding.templeContactsPhotoRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        new LinearSnapHelper().attachToRecyclerView(binding.templeContactsPhotoRv);

        adapter.setUrls(temple.getImageUrlFromFiles());
    }
}
