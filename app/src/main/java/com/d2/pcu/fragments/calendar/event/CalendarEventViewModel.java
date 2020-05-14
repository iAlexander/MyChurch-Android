package com.d2.pcu.fragments.calendar.event;


import com.d2.pcu.App;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.calendar.CalendarItem;
import com.d2.pcu.fragments.BaseViewModel;

public class CalendarEventViewModel extends BaseViewModel {

    private Repository repository;

    private CalendarItem calendarItem;

    public CalendarEventViewModel() {
        repository = App.getInstance().getRepositoryInstance();
    }

    CalendarItem getCalendarItem() {
        return calendarItem;
    }

    void setCalendarItem(CalendarItem calendarItem) {
        this.calendarItem = calendarItem;
    }

}
