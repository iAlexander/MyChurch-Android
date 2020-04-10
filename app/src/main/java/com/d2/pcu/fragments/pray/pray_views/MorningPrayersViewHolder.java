package com.d2.pcu.fragments.pray.pray_views;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.d2.pcu.data.model.pray.Pray;
import com.d2.pcu.databinding.ViewPrayersBinding;
import com.d2.pcu.fragments.pray.OnPrayItemClickListener;
import com.d2.pcu.fragments.pray.OnRefreshPraysListener;
import com.d2.pcu.utils.Constants;

import java.util.List;

public class MorningPrayersViewHolder extends PrayBaseViewHolder {

    private ViewPrayersBinding binding;
    private OnPrayItemClickListener onPrayItemClickListener;
    private OnRefreshPraysListener onRefreshPraysListener;

    public MorningPrayersViewHolder(
            ViewPrayersBinding binding,
            OnPrayItemClickListener onPrayItemClickListener,
            OnRefreshPraysListener onRefreshPraysListener) {
        super(binding);
        this.binding = binding;

        this.onPrayItemClickListener = onPrayItemClickListener;
        this.onRefreshPraysListener = onRefreshPraysListener;
    }

    @Override
    public void bind(List<Pray> morningPrays) {
        PrayersItemAdapter adapter = new PrayersItemAdapter(Constants.PRAY_MORNING, onPrayItemClickListener);
        binding.list.setAdapter(adapter);

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (onRefreshPraysListener != null) {
                    onRefreshPraysListener.update();
                    binding.swipeRefresh.setRefreshing(false);
                }
            }
        });

        adapter.setPrays(morningPrays);
    }
}
