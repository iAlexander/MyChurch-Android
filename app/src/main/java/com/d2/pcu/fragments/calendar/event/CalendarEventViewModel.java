package com.d2.pcu.fragments.calendar.event;


import android.view.View;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.d2.pcu.App;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.calendar.CalendarItem;
import com.d2.pcu.data.model.calendar.Event;
import com.d2.pcu.fragments.BaseViewModel;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.utils.livedata_utils.SingleEvent;

public class CalendarEventViewModel extends BaseViewModel {

    private Repository repository;

    private OnBackButtonClickListener onBackButtonClickListener;

    private CalendarItem calendarItem;

    public CalendarEventViewModel() {
        repository = App.getInstance().getRepositoryInstance();
    }

    void setOnBackButtonClickListener(OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
    }

    CalendarItem getCalendarItem() {
        return calendarItem;
    }

    void setCalendarItem(CalendarItem calendarItem) {
        this.calendarItem = calendarItem;
    }

    public void onBackPressed(View view) {
        if (onBackButtonClickListener != null) {
            onBackButtonClickListener.onBackButtonPressed();
        }
    }
}
