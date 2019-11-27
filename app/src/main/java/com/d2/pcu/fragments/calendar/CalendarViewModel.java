package com.d2.pcu.fragments.calendar;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.d2.pcu.App;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.calendar.CalendarItem;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CalendarViewModel extends ViewModel {

    private Repository repository;
    private LiveData<List<CalendarItem>> calendarItemsLiveData;

    private OnLoadingStateChangedListener onLoadingStateChangedListener;

    private HashMap<Long, List<CalendarItem>> assembledCalendarMap = null;

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

    void setOnLoadingStateChangedListener(OnLoadingStateChangedListener onLoadingStateChangedListener) {
        this.onLoadingStateChangedListener = onLoadingStateChangedListener;
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

    void assembleCalendarMap() {
        List<CalendarItem> calendarItems = calendarItemsLiveData.getValue();
        assembledCalendarMap = new HashMap<>();

        for (CalendarItem item : calendarItems) {
            if (assembledCalendarMap.get(item.getHolidayDate().getTime()) == null) {
                List<CalendarItem> newItems = new ArrayList<>();
                newItems.add(item);
                assembledCalendarMap.put(item.getHolidayDate().getTime(), newItems);
            } else {
                assembledCalendarMap.get(item.getHolidayDate().getTime()).add(item);
            }
        }
    }

    public HashMap<Long, List<CalendarItem>> getAssembledCalendarMap() {
        return assembledCalendarMap;
    }

    LiveData<List<CalendarItem>> getCalendarItemsLiveData() {
        return calendarItemsLiveData;
    }
}
