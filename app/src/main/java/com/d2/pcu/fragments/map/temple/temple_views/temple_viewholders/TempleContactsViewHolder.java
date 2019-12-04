package com.d2.pcu.fragments.map.temple.temple_views.temple_viewholders;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;

import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.databinding.ViewTempleContactBinding;
import com.d2.pcu.fragments.map.temple.temple_views.TemplePhotoAdapter;

import java.util.Arrays;

public class TempleContactsViewHolder extends TempleViewHolder {

    private Temple temple;

    private ViewTempleContactBinding binding;
    private TemplePhotoAdapter adapter;
    private Context context;

    public TempleContactsViewHolder(Context context, ViewTempleContactBinding binding) {
        super(binding);

        this.context = context;
        this.binding = binding;
        this.binding.setHolder(this);

        adapter = new TemplePhotoAdapter();
    }

    @Override
    public void bind(Temple temple) {
        this.temple = temple;

        binding.setTemple(temple);

        binding.templeContactsPhotoRv.setAdapter(adapter);
        binding.templeContactsPhotoRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        new LinearSnapHelper().attachToRecyclerView(binding.templeContactsPhotoRv);

        adapter.setUrls(Arrays.asList(temple.getImageUrl()));
    }

    public void onGetDirectionsClick(View view) {

    }
}
