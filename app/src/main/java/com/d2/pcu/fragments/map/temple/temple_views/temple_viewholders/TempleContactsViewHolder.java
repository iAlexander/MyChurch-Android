package com.d2.pcu.fragments.map.temple.temple_views.temple_viewholders;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;

import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.databinding.ViewTempleContactBinding;
import com.d2.pcu.fragments.PhotoAdapter;
import com.d2.pcu.listeners.OnAdditionalFuncMapListener;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

public class TempleContactsViewHolder extends TempleViewHolder {

    private Temple temple;

    private ViewTempleContactBinding binding;
    private PhotoAdapter adapter;
    private Context context;
    private OnAdditionalFuncMapListener onAdditionalFuncMapListener;

    public TempleContactsViewHolder(
            Context context,
            ViewTempleContactBinding binding,
            OnAdditionalFuncMapListener onAdditionalFuncMapListener) {

        super(binding);

        this.context = context;
        this.binding = binding;
        this.binding.setHolder(this);
        this.onAdditionalFuncMapListener = onAdditionalFuncMapListener;

        adapter = new PhotoAdapter();
    }

    @Override
    public void bind(Temple temple) {
        this.temple = temple;

        binding.setTemple(temple);
        checkTime();

        binding.templeContactsPhotoRv.setAdapter(adapter);
        binding.templeContactsPhotoRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        new LinearSnapHelper().attachToRecyclerView(binding.templeContactsPhotoRv);

        adapter.setUrls(temple.getImageUrls());
    }

    public void onGetDirectionsClick(View view) {
        if (onAdditionalFuncMapListener != null) {
            onAdditionalFuncMapListener.getDirectionsOnMap(new LatLng(temple.getLt(), temple.getLg()));
        }
    }

    private void checkTime() {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.HOUR_OF_DAY) < 19 && calendar.get(Calendar.HOUR_OF_DAY) >= 9) {
           binding.setIsOpen(true);
        } else {
            binding.setIsOpen(false);
        }
    }
}
