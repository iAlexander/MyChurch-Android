package com.d2.pcu.fragments.pray;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.R;
import com.d2.pcu.data.model.pray.Pray;
import com.d2.pcu.databinding.ViewPrayersBinding;
import com.d2.pcu.fragments.pray.pray_views.EveningPrayersViewHolder;
import com.d2.pcu.fragments.pray.pray_views.MorningPrayersViewHolder;
import com.d2.pcu.fragments.pray.pray_views.PrayBaseViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrayersAdapter extends RecyclerView.Adapter<PrayBaseViewHolder> {

    private static final int VIEW_EVENING = 0;
    private static final int VIEW_MORNING = 1;

    private OnPrayItemClickListener onPrayItemClickListener;
    private OnRefreshPraysListener onRefreshPraysListener;

    private List<Integer> prayers = Arrays.asList(VIEW_MORNING, VIEW_EVENING);

    private List<Pray> morningPrays;
    private List<Pray> eveningPrays;

    PrayersAdapter(
            OnPrayItemClickListener onPrayItemClickListener,
            OnRefreshPraysListener onRefreshPraysListener) {
        morningPrays = new ArrayList<>();
        eveningPrays = new ArrayList<>();

        setHasStableIds(true);

        this.onPrayItemClickListener = onPrayItemClickListener;
        this.onRefreshPraysListener = onRefreshPraysListener;
    }

    void setMorningPrays(List<Pray> morningPrays) {
        this.morningPrays = morningPrays;
        notifyItemChanged(0);
    }

    void setEveningPrays(List<Pray> eveningPrays) {
        this.eveningPrays = eveningPrays;
        notifyItemChanged(1);
    }

    @Override
    public int getItemViewType(int position) {
        return prayers.get(position);
    }

    @NonNull
    @Override
    public PrayBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ViewPrayersBinding binding = DataBindingUtil.inflate(inflater, R.layout.view_prayers, parent, false);

        PrayBaseViewHolder holder = null;

        switch (viewType) {
            case VIEW_MORNING: {
                holder = new MorningPrayersViewHolder(
                        binding, onPrayItemClickListener, onRefreshPraysListener
                );
                break;
            }
            case VIEW_EVENING: {
                holder = new EveningPrayersViewHolder(
                        binding, onPrayItemClickListener, onRefreshPraysListener)
                ;
                break;
            }
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PrayBaseViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_MORNING : {
                holder.bind(morningPrays);
                break;
            }
            case VIEW_EVENING : {
                holder.bind(eveningPrays);
                break;
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return prayers.get(position);
    }

    @Override
    public int getItemCount() {
        return prayers == null ? 0 : prayers.size();
    }
}
