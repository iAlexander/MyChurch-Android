package com.d2.pcu.fragments.calendar;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.applandeo.materialcalendarview.utils.DateUtils;
import com.d2.pcu.R;
import com.d2.pcu.data.model.calendar.CalendarItem;
import com.d2.pcu.databinding.FragmentCalendarBinding;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding binding;
    private CalendarViewModel viewModel;

    private OnLoadingStateChangedListener onLoadingStateChangedListener;
    private OnCalendarEventItemClickListener onCalendarEventItemClickListener;

    private DayEventsAdapter adapter;

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new DayEventsAdapter(new OnEventClickListener() {
            @Override
            public void onEventClick(CalendarItem calendarItem) {

                if (onCalendarEventItemClickListener != null) {
                    String serializedEvent = new Gson().toJson(calendarItem);

                    onCalendarEventItemClickListener.onEventItemClick(serializedEvent);
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(CalendarViewModel.class);
        viewModel.setOnLoadingStateChangedListener(onLoadingStateChangedListener);
        viewModel.enableLoading();

        if (viewModel.getAssembledCalendarMap() == null) {
            viewModel.loadCalendar();
        }

        viewModel.getCalendarItemsLiveData().observe(getViewLifecycleOwner(), new Observer<List<CalendarItem>>() {
            @Override
            public void onChanged(List<CalendarItem> calendarItems) {
                List<EventDay> events = new ArrayList<>();

                for (CalendarItem item : calendarItems) {
                    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                    calendar.setTime(item.getHolidayDate());

                    events.add(new EventDay(calendar, R.drawable.dot_red));
                }

                binding.calendarCv.setEvents(events);

                viewModel.assembleCalendarMap();

                viewModel.disableLoading();
            }
        });

        binding.calendarCv.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar calendar = eventDay.getCalendar();
                calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
                DateUtils.setMidnight(calendar);

                List<CalendarItem> items = viewModel.getAssembledCalendarMap().get(calendar.getTimeInMillis());
                if (items != null) {
                    adapter.setDayEvents(items);
                }
            }
        });

        binding.calendarDayEventsRv.setAdapter(adapter);

    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onLoadingStateChangedListener = (OnLoadingStateChangedListener) context;
        onCalendarEventItemClickListener = (OnCalendarEventItemClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onLoadingStateChangedListener = null;
        onCalendarEventItemClickListener = null;
    }
}
