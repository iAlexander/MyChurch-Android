package com.d2.pcu.fragments.cabinet.user_profile.subscription;

import com.d2.pcu.R;
import com.d2.pcu.data.model.profile.PaymentHistoryItem;
import com.d2.pcu.databinding.PaymentItemViewBinding;
import com.d2.pcu.fragments.BaseViewHolder;
import com.d2.pcu.utils.Constants;

public class PaymentsItemViewHolder extends BaseViewHolder<PaymentHistoryItem> {

    private PaymentItemViewBinding binding;

    PaymentsItemViewHolder(PaymentItemViewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @Override
    public void onBind(PaymentHistoryItem item) {
        binding.setItem(item);
        binding.ivStatus.setImageResource(Constants.PaymentStatus.REGULAR_ERROR.equals(item.getStatus()) ? R.drawable.ic_failed : R.drawable.ic_done);
        binding.tvAmount.setText(String.valueOf(item.getAmount()).concat(" грн"));
    }
}
