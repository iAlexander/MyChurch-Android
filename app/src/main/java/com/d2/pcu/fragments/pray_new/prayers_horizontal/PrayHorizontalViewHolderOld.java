package com.d2.pcu.fragments.pray_new.prayers_horizontal;

import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.App;
import com.d2.pcu.data.model.pray.Pray;
import com.d2.pcu.databinding.FragmentItemPrayBinding;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;

public class PrayHorizontalViewHolderOld extends RecyclerView.ViewHolder {

    private FragmentItemPrayBinding binding;

    public PrayHorizontalViewHolderOld(FragmentItemPrayBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    void bind(Pray pray) {
        binding.setPray(pray);

    }

}
