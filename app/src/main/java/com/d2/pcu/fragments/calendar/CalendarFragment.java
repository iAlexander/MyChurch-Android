package com.d2.pcu.fragments.calendar;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.CalendarContract;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applandeo.materialcalendarview.CalendarUtils;
import com.applandeo.materialcalendarview.EventDay;
import com.d2.pcu.R;
import com.d2.pcu.data.model.calendar.CalendarItem;
import com.d2.pcu.databinding.FragmentCalendarBinding;
import com.d2.pcu.listeners.OnLoadingEnableListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding binding;
    private CalendarViewModel viewModel;

    private OnLoadingEnableListener onLoadingEnableListener;

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

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SpannableString content = new SpannableString("dsadsa");
        content.setSpan(new UnderlineSpan(), 0 , content.length(), 0);

        viewModel = ViewModelProviders.of(this).get(CalendarViewModel.class);
        viewModel.setOnLoadingEnableListener(onLoadingEnableListener);
        viewModel.enableLoading();
        viewModel.loadCalendar();

        viewModel.getCalendarItemsLiveData().observe(getViewLifecycleOwner(), new Observer<List<CalendarItem>>() {
            @Override
            public void onChanged(List<CalendarItem> calendarItems) {
                List<EventDay> events = new ArrayList<>();
                Typeface typeface = Typeface.DEFAULT_BOLD;

                Calendar calendar = Calendar.getInstance();

                calendar.set(2019, 10, 21);

                events.add(new EventDay(calendar, R.drawable.ic_dot, Color.parseColor("#228B22")));
                events.add(new EventDay(calendar, R.drawable.ic_dot));

                binding.calendarCv.setEvents(events);

                viewModel.disableLoading();
            }
        });

    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onLoadingEnableListener = (OnLoadingEnableListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onLoadingEnableListener = null;
    }
}
