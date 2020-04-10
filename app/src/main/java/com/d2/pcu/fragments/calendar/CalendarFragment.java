package com.d2.pcu.fragments.calendar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.applandeo.materialcalendarview.utils.DateUtils;
import com.d2.pcu.R;
import com.d2.pcu.data.model.calendar.CalendarItem;
import com.d2.pcu.databinding.FragmentCalendarBinding;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;
import com.d2.pcu.utils.DateFormatter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class CalendarFragment extends Fragment {

    private static final String TAG = CalendarFragment.class.getSimpleName();

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

        viewModel = ViewModelProviders.of(getActivity()).get(CalendarViewModel.class);
        viewModel.setOnLoadingStateChangedListener(onLoadingStateChangedListener);

        if (viewModel.getAssembledItemsArray() == null) {
            viewModel.enableLoading();
            assembleCalendarData();
        } else {
            binding.calendarCv.setEvents(viewModel.getEventDays());
            setCurrentDateInfo();
        }

        binding.calendarCv.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                setEvents(eventDay.getCalendar());
            }
        });

        binding.calendarDayEventsRv.setAdapter(adapter);
    }

    private void assembleCalendarData() {
        viewModel.loadCalendar();

        viewModel.getCalendarItemsLiveData().observe(getViewLifecycleOwner(), new Observer<List<CalendarItem>>() {
            @Override
            public void onChanged(List<CalendarItem> calendarItems) {
                assembleCalendarEvents(calendarItems);

                binding.calendarCv.setEvents(viewModel.getEventDays());
                viewModel.assembleCalendarEventsArray();
                viewModel.disableLoading();
                setCurrentDateInfo();
            }
        });
    }

    private void assembleCalendarEvents(List<CalendarItem> calendarItems) {
        List<EventDay> events = new ArrayList<>();

        for (CalendarItem item : calendarItems) {
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            DateUtils.setMidnight(calendar);
            calendar.setTime(item.getDateNewStyle());

            if (item.getPriority() == 0) {
                events.add(new EventDay(calendar, R.drawable.dot_red));
            } else {
                events.add(new EventDay(calendar));
            }
        }

        viewModel.setEventDays(events);
    }

    private void setCurrentDateInfo() {
        setEvents(binding.calendarCv.getFirstSelectedDate());
    }

    private void setEvents(Calendar calendarSource) {
        Calendar source = calendarSource;

        Calendar calendar = Calendar.getInstance();
        calendar.set(source.get(Calendar.YEAR), source.get(Calendar.MONTH), source.get(Calendar.DATE));

        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        DateUtils.setMidnight(calendar);

        List<CalendarItem> items = viewModel.getAssembledItemsArray().get(calendar.getTimeInMillis());
        if (items != null) {
            adapter.setDayEvents(items);
        } else {
            // TODO: 2019-12-13 Refactoring
            adapter.setDayEvents(new ArrayList<>());
        }

        binding.calendarDateTitleTv.setText(DateFormatter.getDayAndMonth(calendar.getTime()));
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
