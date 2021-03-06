package com.d2.pcu.fragments.pray_new.vertical_items.pager_holders;

import com.d2.pcu.data.model.pray.Pray;
import com.d2.pcu.databinding.ViewPrayersBinding;
import com.d2.pcu.fragments.pray.OnPlayClickListener;
import com.d2.pcu.fragments.pray.OnPrayItemClickListener;
import com.d2.pcu.fragments.pray.OnRefreshPraysListener;
import com.d2.pcu.fragments.pray_new.vertical_items.pager_holders.item_holder.PrayersItemAdapter;
import com.d2.pcu.utils.Constants;

import java.util.List;

public class MorningPrayersViewHolder extends PrayBaseViewHolder {

    private ViewPrayersBinding binding;
    private OnPrayItemClickListener onPrayItemClickListener;
    private OnRefreshPraysListener onRefreshPraysListener;
    private OnPlayClickListener onPlayClickListener;

    public MorningPrayersViewHolder(
            ViewPrayersBinding binding,
            OnPrayItemClickListener onPrayItemClickListener,
            OnRefreshPraysListener onRefreshPraysListener,
            OnPlayClickListener onPlayClickListener) {
        super(binding);
        this.binding = binding;

        this.onPrayItemClickListener = onPrayItemClickListener;
        this.onRefreshPraysListener = onRefreshPraysListener;
        this.onPlayClickListener = onPlayClickListener;
    }

    @Override
    public void bind(List<Pray> morningPrays) {
        PrayersItemAdapter adapter = new PrayersItemAdapter(Constants.PRAY_MORNING, onPrayItemClickListener)
                .setOnPlayClickListener(onPlayClickListener);
        binding.list.setAdapter(adapter);

        binding.swipeRefresh.setOnRefreshListener(() -> {
            if (onRefreshPraysListener != null) {
                onRefreshPraysListener.update();
                binding.swipeRefresh.setRefreshing(false);
            }
        });

        adapter.setPrays(morningPrays);
    }
}
