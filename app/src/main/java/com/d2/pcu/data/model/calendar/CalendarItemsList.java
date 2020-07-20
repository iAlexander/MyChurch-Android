package com.d2.pcu.data.model.calendar;

import com.d2.pcu.data.db.MasterDbModel;
import com.d2.pcu.data.model.pray.Pray;

import java.util.List;

public class CalendarItemsList extends MasterDbModel {

    private List<CalendarItem> calendarItems;

    public List<CalendarItem> getCalendarItems() {
        return calendarItems;
    }

    public void setCalendarItems(List<CalendarItem> calendarItems) {
        this.calendarItems = calendarItems;
    }
}