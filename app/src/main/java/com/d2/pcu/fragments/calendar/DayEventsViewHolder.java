package com.d2.pcu.fragments.calendar;

import android.graphics.Color;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.data.model.calendar.CalendarItem;
import com.d2.pcu.databinding.ViewCalendarEventItemBinding;

public class DayEventsViewHolder extends RecyclerView.ViewHolder {

    private ViewCalendarEventItemBinding binding;

    private OnEventClickListener onEventClickListener;

    private CalendarItem calendarItem;

    DayEventsViewHolder(ViewCalendarEventItemBinding binding, OnEventClickListener eventClickListener) {
        super(binding.getRoot());
        this.binding = binding;
        this.binding.setHolder(this);

        this.onEventClickListener = eventClickListener;
    }

    void bind(CalendarItem calendarItem) {
        this.calendarItem = calendarItem;

        try {
            binding.calendarEventIv.setColorFilter(Color.parseColor(calendarItem.getColor()));
            binding.calendarEventParagraphV.setBackgroundColor(Color.parseColor(calendarItem.getColor()));
        } catch (NumberFormatException e) {
            binding.calendarEventIv.setColorFilter(Color.BLACK);
            binding.calendarEventParagraphV.setBackgroundColor(Color.BLACK);
        }
        binding.calendarEventTitleTv.setText(calendarItem.getName());
    }

    public void onItemClick(View view) {
        if (onEventClickListener != null) {
            onEventClickListener.onEventClick(calendarItem);
        }
    }

}
