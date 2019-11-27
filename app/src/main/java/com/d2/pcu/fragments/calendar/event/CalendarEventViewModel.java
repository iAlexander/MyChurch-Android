package com.d2.pcu.fragments.calendar.event;

import android.view.View;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.d2.pcu.App;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.calendar.CalendarItem;
import com.d2.pcu.data.model.calendar.Event;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;

public class CalendarEventViewModel extends ViewModel {

    private Repository repository;

    private OnBackButtonClickListener onBackButtonClickListener;
    private OnLoadingStateChangedListener onLoadingStateChangedListener;

    private LiveData<Event> eventLiveData;

    private CalendarItem calendarItem;

    public CalendarEventViewModel() {
        repository = App.getInstance().getRepositoryInstance();

        eventLiveData = Transformations.switchMap(repository.getTransport().getEventChannel(), new Function<Event, LiveData<Event>>() {
            @Override
            public LiveData<Event> apply(Event input) {
                return new MutableLiveData<>(input);
            }
        });
    }

    void setOnBackButtonClickListener(OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
    }

    public void setOnLoadingStateChangedListener(OnLoadingStateChangedListener onLoadingStateChangedListener) {
        this.onLoadingStateChangedListener = onLoadingStateChangedListener;
    }

    void loadEventData() {
        if (calendarItem != null) {
            repository.getEventInfo(calendarItem.getId());
        }
    }

    LiveData<Event> getEventLiveData() {
        return eventLiveData;
    }

    void enableLoading() {
        if (onLoadingStateChangedListener != null) {
            onLoadingStateChangedListener.enableLoading(true);
        }
    }

    void disableLoading() {
        if (onLoadingStateChangedListener != null) {
            onLoadingStateChangedListener.enableLoading(false);
        }
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
