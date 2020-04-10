package com.d2.pcu.data.responses.calendar;

import com.d2.pcu.data.model.calendar.CalendarItem;
import com.d2.pcu.data.responses.OnMasterResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CalendarResponse extends OnMasterResponse {

    @SerializedName("data")
    private CalendarDataWrapper calendarDataWrapper;

    public CalendarDataWrapper getCalendarDataWrapper() {
        return calendarDataWrapper;
    }

    public void setCalendarDataWrapper(CalendarDataWrapper calendarDataWrapper) {
        this.calendarDataWrapper = calendarDataWrapper;
    }
}
