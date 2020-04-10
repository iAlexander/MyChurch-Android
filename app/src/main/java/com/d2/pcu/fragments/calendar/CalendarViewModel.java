package com.d2.pcu.fragments.calendar;

import android.util.LongSparseArray;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.applandeo.materialcalendarview.EventDay;
import com.d2.pcu.App;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.calendar.CalendarItem;
import com.d2.pcu.fragments.BaseViewModel;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;

import java.util.ArrayList;
import java.util.List;

public class CalendarViewModel extends BaseViewModel {

    private Repository repository;
    private LiveData<List<CalendarItem>> calendarItemsLiveData;

    private LongSparseArray<List<CalendarItem>> assembledItemsArray = null;
    private List<EventDay> eventDays;

    public CalendarViewModel() {
        repository = App.getInstance().getRepositoryInstance();

        calendarItemsLiveData = Transformations.switchMap(repository.getTransport().getCalendarChannel(), new Function<List<CalendarItem>, LiveData<List<CalendarItem>>>() {
            @Override
            public LiveData<List<CalendarItem>> apply(List<CalendarItem> input) {
                return new MutableLiveData<>(input);
            }
        });
    }

    void loadCalendar() {
        repository.getCalendar();
    }

    void assembleCalendarEventsArray() {
        List<CalendarItem> calendarItems = calendarItemsLiveData.getValue();

        assembledItemsArray = new LongSparseArray<>();

        for (CalendarItem item : calendarItems) {
            if (assembledItemsArray.get(item.getDateNewStyle().getTime()) == null) {
                List<CalendarItem> newItems = new ArrayList<>();
                newItems.add(item);
                assembledItemsArray.put(item.getDateNewStyle().getTime(), newItems);
            } else {
                assembledItemsArray.get(item.getDateNewStyle().getTime()).add(item);
            }
        }
    }

    LongSparseArray<List<CalendarItem>> getAssembledItemsArray() {
        return assembledItemsArray;
    }

    LiveData<List<CalendarItem>> getCalendarItemsLiveData() {
        return calendarItemsLiveData;
    }

    void setEventDays(List<EventDay> eventDays) {
        this.eventDays = eventDays;
    }

    List<EventDay> getEventDays() {
        return eventDays;
    }

    String getUTCTimeZone() {
        return "UTC";
    }
}
