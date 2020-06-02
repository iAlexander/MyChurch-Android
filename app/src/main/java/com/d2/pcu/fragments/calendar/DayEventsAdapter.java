package com.d2.pcu.fragments.calendar;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.R;
import com.d2.pcu.data.model.calendar.CalendarItem;
import com.d2.pcu.databinding.ViewCalendarEventItemBinding;
import com.google.android.gms.common.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DayEventsAdapter extends RecyclerView.Adapter<DayEventsViewHolder> {

    private List<CalendarItem> calendarItems = new ArrayList<>();
    private OnEventClickListener eventClickListener;

    DayEventsAdapter(OnEventClickListener onEventClickListener) {
        this.eventClickListener = onEventClickListener;
    }

    void setDayEvents(List<CalendarItem> events) {
        calendarItems.clear();
        if (!CollectionUtils.isEmpty(events)) {
            Collections.sort(events, (o1, o2) -> o2.getPriority() - o1.getPriority());
            calendarItems.addAll(events);
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DayEventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ViewCalendarEventItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.view_calendar_event_item, parent, false);
        return new DayEventsViewHolder(binding, eventClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DayEventsViewHolder holder, int position) {
        if (position != holder.getAdapterPosition()) return;
        if (calendarItems.size() <= position) return;
        holder.bind(calendarItems.get(position));
    }

    @Override
    public int getItemCount() {
        return calendarItems == null ? 0 : calendarItems.size();
    }
}
