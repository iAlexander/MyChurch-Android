package com.d2.pcu.fragments.calendar.event;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.lifecycle.ViewModelProvider;

import com.d2.pcu.R;
import com.d2.pcu.data.model.calendar.CalendarItem;
import com.d2.pcu.databinding.FragmentCalendarEventBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.ui.TextToHtmlFormatter;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class FragmentCalendarEvent extends BaseFragment {

    private FragmentCalendarEventBinding binding;
    private CalendarEventViewModel viewModel;

    private CalendarItem calendarItem;

//    private PhotoAdapter adapter;

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
        binding = FragmentCalendarEventBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CalendarEventViewModel.class);

//        adapter = new PhotoAdapter();
//        binding.calendarEventPhotoRv.setAdapter(adapter);
//        binding.calendarEventPhotoRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        setViewModelListeners(viewModel);
        viewModel.setCalendarItem(calendarItem);

        if (viewModel.getCalendarItem() != null) {
            binding.setEvent(viewModel.getCalendarItem());

            Drawable wrapped = DrawableCompat.wrap(getResources().getDrawable(R.drawable.paragraph));
            try {
                DrawableCompat.setTint(wrapped, Color.parseColor(calendarItem.getColor()));
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                DrawableCompat.setTint(wrapped, Color.BLACK);
            }

            binding.calendarEventColorV.setBackground(wrapped);

            CalendarItem ci = viewModel.getCalendarItem();
            if (ci.getIconImage() != null) {
                if (!TextUtils.isEmpty(ci.getImageUrl())) {
                    Picasso.get().load(ci.getImageUrl()).into(binding.itemIv);
                }
//                adapter.setUrl(ci.getImageUrl());
            }

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

}
