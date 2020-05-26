package com.d2.pcu.fragments.notification;

import android.view.View;

import com.d2.pcu.R;
import com.d2.pcu.data.model.profile.NotificationHistoryItem;
import com.d2.pcu.databinding.NotificationItemViewBinding;
import com.d2.pcu.fragments.BaseViewHolder;

public class NotificationItemViewHolder extends BaseViewHolder<NotificationHistoryItem> {

    private NotificationItemViewBinding binding;

    NotificationItemViewHolder(NotificationItemViewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.binding.setHolder(this);
    }

    @Override
    public void onBind(NotificationHistoryItem item) {
        binding.setItem(item);
        binding.vIsUnread.setVisibility(item.isRead() ? View.GONE : View.VISIBLE);
        binding.tvTitle.setTextAppearance(binding.getRoot().getContext(), item.isRead() ? R.style.AppText_Big : R.style.AppText_Big_Bold);
    }
}
