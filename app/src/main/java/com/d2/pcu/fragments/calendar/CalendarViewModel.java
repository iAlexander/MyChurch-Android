package com.d2.pcu.fragments.calendar;

import android.util.LongSparseArray;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.applandeo.materialcalendarview.EventDay;
import com.d2.pcu.App;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.calendar.CalendarItem;
import com.d2.pcu.fragments.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class CalendarViewModel extends BaseViewModel {

    private Repository repository;
    private LiveData<List<CalendarItem>> calendarItemsLiveData;

    private LongSparseArray<List<CalendarItem>> assembledItemsArray = null;
    private List<EventDay> eventDays;

    public CalendarViewModel() {
        repository = App.getInstance().getRepositoryInstance();

        calendarItemsLiveData = Transformations.switchMap(repository.getTransport().getCalendarChannel(), MutableLiveData::new);
    }

    void loadCalendar() {
        repository.getCalendar();
    }

    void assembleCalendarEventsArray() {
        List<CalendarItem> calendarItems = calendarItemsLiveData.getValue();
        assembledItemsArray = new LongSparseArray<>();
        List<CalendarItem> newItems;
        for (CalendarItem item : calendarItems) {
            if (assembledItemsArray.get(item.getDateNewStyle().getTime()) == null) {
                newItems = new ArrayList<>();
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
