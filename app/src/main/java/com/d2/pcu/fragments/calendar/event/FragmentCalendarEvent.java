package com.d2.pcu.fragments.calendar.event;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.d2.pcu.R;
import com.d2.pcu.data.model.calendar.CalendarItem;
import com.d2.pcu.data.model.calendar.Event;
import com.d2.pcu.databinding.FragmentCalendarEventBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.fragments.PhotoAdapter;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;
import com.d2.pcu.ui.TextToHtmlFormatter;
import com.d2.pcu.utils.livedata_utils.SingleEvent;
import com.google.gson.Gson;

public class FragmentCalendarEvent extends BaseFragment {

    private FragmentCalendarEventBinding binding;
    private CalendarEventViewModel viewModel;

    private OnBackButtonClickListener onBackButtonClickListener;

    private CalendarItem calendarItem;

    private PhotoAdapter adapter;

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

        adapter = new PhotoAdapter();
        binding.calendarEventPhotoRv.setAdapter(adapter);
        binding.calendarEventPhotoRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        viewModel.setOnBackButtonClickListener(onBackButtonClickListener);
        viewModel.setCalendarItem(calendarItem);

        if (viewModel.getCalendarItem() != null) {
            binding.setEvent(viewModel.getCalendarItem());

            Drawable wrapped = DrawableCompat.wrap(getResources().getDrawable(R.drawable.paragraph));
            try {
                DrawableCompat.setTint(wrapped, Color.parseColor(calendarItem.getColor()));
            } catch (NumberFormatException|StringIndexOutOfBoundsException e) {
                DrawableCompat.setTint(wrapped, Color.BLACK);
            }

            binding.calendarEventColorV.setBackground(wrapped);

//            adapter.setUrls(resultEvent.getImageUrls());

            viewModel.disableLoading();

            String htmlString = viewModel.getCalendarItem().getDescribe()
                    + "<br><br>"
                    + viewModel.getCalendarItem().getConceived();

            binding.calendarEventFullHolidayDescriptionTv.setText(
                    TextToHtmlFormatter.getFormattedHtmlText(htmlString),
                    TextView.BufferType.SPANNABLE
            );
        }

        binding.setModel(viewModel);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onBackButtonClickListener = (OnBackButtonClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onBackButtonClickListener = null;
    }
}
