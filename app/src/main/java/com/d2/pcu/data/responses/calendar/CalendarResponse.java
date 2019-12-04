package com.d2.pcu.data.responses.calendar;

import com.d2.pcu.data.model.calendar.CalendarItem;
import com.d2.pcu.data.responses.OnMasterResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CalendarResponse extends OnMasterResponse {

    @SerializedName("data")
    private List<CalendarItem> calendarItems;

    public List<CalendarItem> getCalendarItems() {
        return calendarItems;
    }

    public void setCalendarItems(List<CalendarItem> calendarItems) {
        this.calendarItems = calendarItems;
    }

    @Override
    public String toString() {
        return "CalendarResponse{" +
                "calendarItems=" + calendarItems +
                '}';
    }
}
