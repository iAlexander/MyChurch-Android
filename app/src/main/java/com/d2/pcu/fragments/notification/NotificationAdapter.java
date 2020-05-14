package com.d2.pcu.fragments.notification;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.d2.pcu.data.model.profile.NotificationHistoryItem;
import com.d2.pcu.databinding.NotificationItemViewBinding;
import com.d2.pcu.fragments.BaseAdapter;
import com.d2.pcu.fragments.BaseViewHolder;

public class NotificationAdapter extends BaseAdapter<NotificationHistoryItem, BaseViewHolder<NotificationHistoryItem>> {

    private BaseViewHolder.OnItemClickListener onItemClickListener;

    NotificationAdapter() {

        setHasStableIds(true);
    }

    public NotificationAdapter setOnItemClickListener(BaseViewHolder.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    @NonNull
    @Override
    public BaseViewHolder<NotificationHistoryItem> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        NotificationItemViewBinding binding = NotificationItemViewBinding.inflate(inflater);
        //DataBindingUtil.inflate(inflater, R.layout.news_item_vertical_view, parent, false);

        return new NotificationItemViewHolder(binding).setOnItemClickListener(onItemClickListener);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }
}
