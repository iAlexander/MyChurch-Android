package com.d2.pcu.fragments.pray;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.R;
import com.d2.pcu.databinding.ViewPrayersBinding;
import com.d2.pcu.fragments.pray.pray_views.EveningPrayersViewHolder;
import com.d2.pcu.fragments.pray.pray_views.FavoritePrayersViewHolder;
import com.d2.pcu.fragments.pray.pray_views.MorningPrayersViewHolder;
import com.d2.pcu.fragments.pray.pray_views.PrayBaseViewHolder;

import java.util.Arrays;
import java.util.List;

public class PrayersAdapter extends RecyclerView.Adapter<PrayBaseViewHolder> {

    private ViewPrayersBinding binding;

    private static final int VIEW_EVENING = 0;
    private static final int VIEW_MORNING = 1;
    private static final int VIEW_FAVORITE = 2;


    private List<Integer> prayers = Arrays.asList(VIEW_EVENING, VIEW_MORNING, VIEW_FAVORITE);

    PrayersAdapter() {
    }

    @NonNull
    @Override
    public PrayBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        binding = DataBindingUtil.inflate(inflater, R.layout.view_prayers, parent, false);

        PrayBaseViewHolder holder = null;

        switch (viewType) {
            case VIEW_EVENING: {
                holder = new EveningPrayersViewHolder(binding);
                break;
            }
            case VIEW_MORNING: {
                holder = new MorningPrayersViewHolder(binding);
                break;
            }
            case VIEW_FAVORITE: {
                holder = new FavoritePrayersViewHolder(binding);
            }
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PrayBaseViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_EVENING : {
                ((EveningPrayersViewHolder)holder).bind();
                break;
            }
            case VIEW_MORNING : {
                ((MorningPrayersViewHolder)holder).bind();
                break;
            }
            case VIEW_FAVORITE : {
                ((FavoritePrayersViewHolder)holder).bind();
            }
        }
    }

    @Override
    public int getItemCount() {
        return prayers == null ? 0 : prayers.size();
    }
}
