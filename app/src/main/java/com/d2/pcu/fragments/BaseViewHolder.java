package com.d2.pcu.fragments;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.fragments.notification.NotificationItemViewHolder;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    private NotificationItemViewHolder.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public BaseViewHolder<T> setOnItemClickListener(NotificationItemViewHolder.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void onBind(T item);

    public void onItemClick(View view) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

}