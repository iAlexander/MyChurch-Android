package com.d2.pcu.fragments.cabinet.user_profile.subscription;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.data.model.profile.PaymentHistoryItem;
import com.d2.pcu.databinding.PaymentItemViewBinding;
import com.d2.pcu.fragments.BaseAdapter;
import com.d2.pcu.fragments.BaseViewHolder;

public class PaymentsAdapter extends BaseAdapter<PaymentHistoryItem, BaseViewHolder<PaymentHistoryItem>> {

    PaymentsAdapter() {

        setHasStableIds(true);
    }

    @NonNull
    @Override
    public BaseViewHolder<PaymentHistoryItem> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        PaymentItemViewBinding binding = PaymentItemViewBinding.inflate(inflater);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        binding.getRoot().setLayoutParams(lp);
        return new PaymentsItemViewHolder(binding);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }
}
