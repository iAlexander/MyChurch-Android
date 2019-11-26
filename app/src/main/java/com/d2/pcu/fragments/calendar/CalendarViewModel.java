package com.d2.pcu.fragments.calendar;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.d2.pcu.App;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.calendar.CalendarItem;
import com.d2.pcu.listeners.OnLoadingEnableListener;

import java.util.List;

public class CalendarViewModel extends ViewModel {

    private Repository repository;
    private LiveData<List<CalendarItem>> calendarItemsLiveData;

    private OnLoadingEnableListener onLoadingEnableListener;

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

    void setOnLoadingEnableListener(OnLoadingEnableListener onLoadingEnableListener) {
        this.onLoadingEnableListener = onLoadingEnableListener;
    }

    void enableLoading() {
        if (onLoadingEnableListener != null) {
            onLoadingEnableListener.enableLoading(true);
        }
    }

    void disableLoading() {
        if (onLoadingEnableListener != null) {
            onLoadingEnableListener.enableLoading(false);
        }
    }

    LiveData<List<CalendarItem>> getCalendarItemsLiveData() {
        return calendarItemsLiveData;
    }
}
