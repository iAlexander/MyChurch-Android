package com.d2.pcu.data.responses.calendar;

import com.d2.pcu.data.responses.OnMasterResponse;
import com.google.gson.annotations.SerializedName;

public class CalendarUpdateResponse extends OnMasterResponse {

    @SerializedName("data")
    private CalendarLastUpdate calendarLastUpdate;

    public CalendarLastUpdate getCalendarLastUpdate() {
        return calendarLastUpdate;
    }

    public void setCalendarLastUpdate(CalendarLastUpdate calendarLastUpdate) {
        this.calendarLastUpdate = calendarLastUpdate;
    }
}
