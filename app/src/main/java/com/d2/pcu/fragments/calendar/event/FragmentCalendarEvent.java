package com.d2.pcu.fragments.calendar.event;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.d2.pcu.R;
import com.d2.pcu.data.model.calendar.CalendarItem;
import com.d2.pcu.data.model.calendar.Event;
import com.d2.pcu.databinding.FragmentCalendarEventBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;
import com.google.gson.Gson;

public class FragmentCalendarEvent extends BaseFragment {

    private FragmentCalendarEventBinding binding;
    private CalendarEventViewModel viewModel;

    private OnBackButtonClickListener onBackButtonClickListener;
    private OnLoadingStateChangedListener onLoadingStateChangedListener;

    private CalendarItem calendarItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            calendarItem = new Gson().fromJson(FragmentCalendarEventArgs.fromBundle(getArguments()).getSerializedEvent(), CalendarItem.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar_event, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(CalendarEventViewModel.class);

        viewModel.enableLoading();

        viewModel.setOnBackButtonClickListener(onBackButtonClickListener);
        viewModel.setCalendarItem(calendarItem);

        viewModel.loadEventData();

        viewModel.getEventLiveData().observe(getViewLifecycleOwner(), new Observer<Event>() {
            @Override
            public void onChanged(Event event) {
                binding.setEvent(event);

                Drawable wrapped = DrawableCompat.wrap(getResources().getDrawable(R.drawable.paragraph));
                DrawableCompat.setTint(wrapped, Color.parseColor(calendarItem.getColor()));
                binding.calendarEventColorV.setBackground(wrapped);

                viewModel.disableLoading();
            }
        });

        binding.setModel(viewModel);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onBackButtonClickListener = (OnBackButtonClickListener) context;
        onLoadingStateChangedListener = (OnLoadingStateChangedListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onBackButtonClickListener = null;
        onLoadingStateChangedListener = null;
    }
}
