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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
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

import timber.log.Timber;

public class CalendarFragment extends Fragment {

    private static final String TAG = CalendarFragment.class.getSimpleName();

    private FragmentCalendarBinding binding;
    private CalendarViewModel viewModel;

    private OnLoadingStateChangedListener onLoadingStateChangedListener;
    private OnCalendarEventItemClickListener onCalendarEventItemClickListener;

    private DayEventsAdapter adapter;
    private Calendar lastDate;

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

        adapter = new DayEventsAdapter(calendarItem -> {
            if (onCalendarEventItemClickListener != null) {
                String serializedEvent = new Gson().toJson(calendarItem);

                onCalendarEventItemClickListener.onEventItemClick(serializedEvent);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(CalendarViewModel.class);
        viewModel.setOnLoadingStateChangedListener(onLoadingStateChangedListener);

        Timber.e("getAssembledItemsArray: %s\ngetEventDays: %s", viewModel.getAssembledItemsArray(), viewModel.getEventDays() );
        if (viewModel.getAssembledItemsArray() == null) {
            viewModel.enableLoading();
            assembleCalendarData();
        } else {
            binding.calendarCv.setEvents(viewModel.getEventDays());
            setCurrentDateInfo();
        }

        binding.calendarCv.setOnDayClickListener(eventDay -> setEvents(eventDay.getCalendar()));

        binding.calendarDayEventsRv.setAdapter(adapter);
        binding.calendarCv.setSaveEnabled(false);
        binding.calendarCv.setSaveFromParentEnabled(false);
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
//                events.add(new EventDay(calendar));
            }
        }

        viewModel.setEventDays(events);
    }

    private void setCurrentDateInfo() {
        if (lastDate != null) {
            try {
                binding.calendarCv.setDate(lastDate);
//                List<Calendar> single = new ArrayList<>();
//                single.add(lastDate);
//                binding.calendarCv.setSelectedDates(single);
                setEvents(lastDate);
            } catch (OutOfDateRangeException e) {
                Timber.e(e, "restore date: %s", e.getMessage());
                setEvents(binding.calendarCv.getFirstSelectedDate());
            }
        } else {
            setEvents(binding.calendarCv.getFirstSelectedDate());
        }
    }

    private void setEvents(Calendar source) {
this.lastDate = source;
        Calendar calendar = Calendar.getInstance();
        calendar.set(source.get(Calendar.YEAR), source.get(Calendar.MONTH), source.get(Calendar.DATE));

        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        DateUtils.setMidnight(calendar);
//        this.lastDate = calendar;
        List<CalendarItem> items = viewModel.getAssembledItemsArray().get(source.getTimeInMillis());

        adapter.setDayEvents(items);

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
